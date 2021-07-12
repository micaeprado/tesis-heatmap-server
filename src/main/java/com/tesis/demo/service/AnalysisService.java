package com.tesis.demo.service;

import com.tesis.demo.enumeration.FunctionType;
import com.tesis.demo.model.Geodata;
import com.tesis.demo.model.Heatmap;
import com.tesis.demo.model.Map;
import com.tesis.demo.model.Point;
import com.tesis.demo.model.WeightedLoc;
import com.tesis.demo.model.dto.FieldFilterDto;
import com.tesis.demo.model.dto.HeatmapDto;
import com.tesis.demo.model.dto.MapDto;
import com.tesis.demo.model.dto.WeightedLocDto;
import com.tesis.demo.model.dto.ZoneFilterDto;
import com.tesis.demo.model.mapper.FieldFilterMapper;
import com.tesis.demo.model.mapper.HeatmapMapper;
import com.tesis.demo.model.mapper.ZoneFilterMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    protected final HeatmapService heatmapService;

    public List<WeightedLocDto> createMap(MapDto map) {
        Map savedMap = mapService.save(map);
        List<FieldFilterDto> savedFieldFilter = fieldFilterService.saveAll(FieldFilterMapper.fieldFiltersDTOsToFieldFilters(map.getFieldFilters()), savedMap);
        List<ZoneFilterDto> savedZoneFilter = zoneFilterService.saveAll(ZoneFilterMapper.zoneFiltersDTOsToList(map.getZoneFilters()), savedMap);
        List<Geodata> filteredElements = geodataService.getFilteredElements(map.getFileName(), savedFieldFilter, savedZoneFilter);
        HeatmapDto heatmap = createHeatmap(map.getName(), filteredElements, map.getFunctionName(), map.getFieldToCalculate(), map.getZoneFilters());
        return heatmap.getWeightedLocs();
    }

    private HeatmapDto createHeatmap(String name, List<Geodata> geodata, String function, String fieldValueFilterFunction, List<ZoneFilterDto> zoneFilters) {
        double result = getResult(function, geodata, fieldValueFilterFunction);
        List<WeightedLoc> weightedLocs = new ArrayList<>();
        geodata.forEach(element -> {
            Point point = Point.builder().lat(element.getLat()).lng(element.getLng()).build();
            double weight = Double.parseDouble(element.getFields().get(fieldValueFilterFunction)) / result;
            weightedLocs.add(WeightedLoc.builder().location(point).weight(weight).build());
        });

        Heatmap heatmap = Heatmap.builder()
                .name(name)
                .geodata(geodata)
                .weightedLocs(weightedLocs)
                .creationDate(LocalDateTime.now())
                .build();
        return HeatmapMapper.toDto(heatmapService.save(heatmap));
    }

    private double getResult(String idFunction, List<Geodata> geodata, String field) {
        if (FunctionType.FUNCTION_STANDARD_DEVIATION.getName().equals(idFunction)) {
            return getStandardDeviation(geodata, field);
        } else if (FunctionType.FUNCTION_ARITHMETIC_AVERAGE.getName().equals(idFunction)) {
            return getArithmeticAverage(geodata, field);
        } else if (FunctionType.FUNCTION_MINIMUM.getName().equals(idFunction)) {
            return getMinimum(geodata, field);
        } else if (FunctionType.FUNCTION_MAXIMUM.getName().equals(idFunction)){
            return getMaximum(geodata, field);
        }
        return 0;
    }

    private double getStandardDeviation(List<Geodata> geodata, String field) {
        return getDescriptiveStatistics(geodata, field).getStandardDeviation();
    }

    private double getArithmeticAverage(List<Geodata> geodata, String field) {
        return getDescriptiveStatistics(geodata, field).getMean();
    }

    private double getMinimum(List<Geodata> geodata, String field) {
        return getDescriptiveStatistics(geodata, field).getMin();
    }

    private double getMaximum(List<Geodata> geodata, String field) {
        return getDescriptiveStatistics(geodata, field).getMax();
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
        functions.add(FunctionType.FUNCTION_MINIMUM.getName());
        functions.add(FunctionType.FUNCTION_MAXIMUM.getName());
        return functions;
    }

}
