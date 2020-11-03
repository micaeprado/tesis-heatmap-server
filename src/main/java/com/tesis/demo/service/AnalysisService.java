package com.tesis.demo.service;

import com.tesis.demo.enumeration.FunctionType;
import com.tesis.demo.model.Geodata;
import com.tesis.demo.model.Layer;
import com.tesis.demo.model.Point;
import com.tesis.demo.model.WeightedLoc;
import com.tesis.demo.model.Zone;
import com.tesis.demo.model.dto.PointZoneDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    protected final ModelMapper modelMapper;
    protected final GeodataService geodataService;
    protected final PolygonService polygonService;

   /* public List<WeightedLoc> getElementsAnalyzed(Integer idFunction, String fileName) {
        List<Geodata> geodata = geodataService.getGeodataByFileName(fileName);
        double result = getResult(idFunction, geodata);
        List<WeightedLoc> weightedLocs = new ArrayList<>();
        for (Geodata element: geodata) {
           // double weight = getSeconds(element.getEndTime().getTime(), element.getStartTime().getTime()) / result;
            double weight = 1;
            Point point = new Point().lat(element.getLat()).lng(element.getLng());
            WeightedLoc weightedLoc = new WeightedLoc().location(point).weight(weight);
            weightedLocs.add(weightedLoc);

        }
        return weightedLocs;
    }*/

    public List<WeightedLoc> getMapElements(Layer layer) {
        List<Geodata> filteredElements = geodataService.getFilteredElements(layer.getFileName(), layer.getFieldFilters());
        return getElementsAnalyzed(filteredElements, layer.getFunctionName(), layer.getFieldToCalculate(), layer.getZone().getId());
    }

    public List<WeightedLoc> getElementsAnalyzed(List<Geodata> geodata, String function, String fieldValueFilterFunction, Long idZone) {
        Zone zone = polygonService.getZoneById(idZone);
        double result = getResult(function, geodata, fieldValueFilterFunction);
        List<WeightedLoc> weightedLocs = new ArrayList<>();
        for (Geodata element: geodata) {
            PointZoneDto point = new PointZoneDto().lat(element.getLat()).lng(element.getLng());
            Point point2 = new Point().lat(element.getLat()).lng(element.getLng());

            //if (polygonService.isPointInsideZone(point, zone)) {
            List<PointZoneDto> points = polygonService.getPointsDtoByZone(zone);

            if (ComparePoints.isInside(points, point)) {
                    Map<String, String> field = element.getFields();
                double weight = Double.parseDouble(field.get(fieldValueFilterFunction))/result;
                WeightedLoc weightedLoc = new WeightedLoc().location(point2).weight(weight);
                weightedLocs.add(weightedLoc);
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
