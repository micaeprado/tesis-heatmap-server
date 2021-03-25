package com.tesis.demo.service;

import com.tesis.demo.enumeration.FunctionType;
import com.tesis.demo.model.Geodata;
import com.tesis.demo.model.Map;
import com.tesis.demo.model.Point;
import com.tesis.demo.model.dto.WeightedLoc;
import com.tesis.demo.model.dto.FieldFilterDto;
import com.tesis.demo.model.dto.MapDto;
import com.tesis.demo.model.dto.PointZoneDto;
import com.tesis.demo.model.dto.ZoneFilterDto;
import com.tesis.demo.model.mapper.FieldFilterMapper;
import com.tesis.demo.model.mapper.ZoneFilterMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    protected final ModelMapper modelMapper;
    protected final GeodataService geodataService;
    protected final PolygonService polygonService;
    protected final MapService mapService;
    protected final FieldFilterService fieldFilterService;
    protected final ZoneFilterService zoneFilterService;

    public List<WeightedLoc> createMap(MapDto map) {
        Map savedMap = mapService.save(map);
        List<FieldFilterDto> savedFieldFilter = fieldFilterService.saveAll(FieldFilterMapper.fieldFiltersDTOsToFieldFilters(map.getFieldFilters()), savedMap);
        List<ZoneFilterDto> savedZoneFilter = zoneFilterService.saveAll(ZoneFilterMapper.zoneFiltersDTOsToList(map.getZoneFilters()), savedMap);
        List<Geodata> filteredElements = geodataService.getFilteredElements(map.getFileName(), savedFieldFilter);
        return getElementsAnalyzed(filteredElements, map.getFunctionName(), map.getFieldToCalculate(), map.getZoneFilters());
    }

    public List<WeightedLoc> getElementsAnalyzed(List<Geodata> geodata, String function, String fieldValueFilterFunction, List<ZoneFilterDto> zoneFilters) {
        double result = getResult(function, geodata, fieldValueFilterFunction);
        List<WeightedLoc> weightedLocs = new ArrayList<>();
        for (Geodata element: geodata) {
            PointZoneDto point = new PointZoneDto().lat(element.getLat()).lng(element.getLng());
            Point point2 = new Point().lat(element.getLat()).lng(element.getLng());

            if (zoneFilters != null) {
                for (ZoneFilterDto zoneFilter : zoneFilters) {
                    if ((zoneFilter.getFilterInside() && ComparePoints.isInside(zoneFilter.getZone().getPoints(), point))
                            || (!zoneFilter.getFilterInside() && !ComparePoints.isInside(zoneFilter.getZone().getPoints(), point))) {
                        double weight = Double.parseDouble(element.getFields().get(fieldValueFilterFunction)) / result;
                        weightedLocs.add(WeightedLoc.builder().location(point2).weight(weight).build());
                    }
                }
            } else {
                double weight = Double.parseDouble(element.getFields().get(fieldValueFilterFunction)) / result;
                weightedLocs.add(WeightedLoc.builder().location(point2).weight(weight).build());
            }
        }

        return weightedLocs;
    }

    private double getResult(String idFunction, List<Geodata> geodata, String field) {
            if(FunctionType.FUNCTION_STANDARD_DEVIATION.getName().equals(idFunction)) {
                return getStandardDeviation(geodata, field);
            }
            else if(FunctionType.FUNCTION_ARITHMETIC_AVERAGE.getName().equals(idFunction)) {
                return getArithmeticAverage(geodata, field);
            }
        return 0;
    }


    private double getStandardDeviation(List<Geodata> geodata, String field) {
        return getDescriptiveStatistics(geodata, field).getStandardDeviation();
    }

    private double getArithmeticAverage(List<Geodata> geodata, String field) {
        return getDescriptiveStatistics(geodata, field).getMean();
    }

    private DescriptiveStatistics getDescriptiveStatistics(List<Geodata> geodata, String field) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

        for (Geodata element : geodata) {
            descriptiveStatistics.addValue(Double.parseDouble(element.getFields().get(field)));
        }

        return descriptiveStatistics;
    }

    public Set<String> getFunctions() {
        Set<String> functions = new HashSet<>();
        functions.add(FunctionType.FUNCTION_STANDARD_DEVIATION.getName());
        functions.add(FunctionType.FUNCTION_ARITHMETIC_AVERAGE.getName());
        return functions;
    }

}
