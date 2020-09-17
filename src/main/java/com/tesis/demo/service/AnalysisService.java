package com.tesis.demo.service;

import com.tesis.demo.enumeration.FunctionType;
import com.tesis.demo.model.Geodata;
import com.tesis.demo.model.Point;
import com.tesis.demo.model.WeightedLoc;
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

    public List<WeightedLoc> getMapElements(String fileName, String fieldFilter, String fieldValueFilter,
                                            Integer function, String fieldValueFilterFunction) {
        List<Geodata> filteredElements = geodataService.getFilteredElements(fileName, fieldFilter, fieldValueFilter);
        return getElementsAnalyzed(filteredElements, function, fieldValueFilterFunction);
    }

    public List<WeightedLoc> getElementsAnalyzed(List<Geodata> geodata, Integer function, String fieldValueFilterFunction) {
        double result = getResult(function, geodata);
        List<WeightedLoc> weightedLocs = new ArrayList<>();
        for (Geodata element: geodata) {
            Map<String, String> field = element.getFields();
            double weight = Double.parseDouble(field.get(fieldValueFilterFunction))/result;
            Point point = new Point().lat(element.getLat()).lng(element.getLng());
            WeightedLoc weightedLoc = new WeightedLoc().location(point).weight(weight);
            weightedLocs.add(weightedLoc);
        }
        return weightedLocs;
    }

    private double getResult(Integer idFunction, List<Geodata> geodata) {
            if(idFunction == FunctionType.FUNCTION_STANDARD_DEVIATION.getId()) {
                return getStandardDeviation(geodata);
            }
            else if(idFunction.equals(FunctionType.FUNCTION_ARITHMETIC_AVERAGE.getId())) {
                return getArithmeticAverage(geodata);
            }
        return 0;
    }


    private double getStandardDeviation(List<Geodata> geodata) {
        return getDescriptiveStatistics(geodata).getStandardDeviation();
    }

    private double getArithmeticAverage(List<Geodata> geodata) {
        return getDescriptiveStatistics(geodata).getMean();
    }

    private DescriptiveStatistics getDescriptiveStatistics(List<Geodata> geodata) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

        for (Geodata element : geodata) {
            descriptiveStatistics.addValue(getSeconds(1, 1));
            //descriptiveStatistics.addValue(getSeconds(element.getEndTime().getTime(), element.getStartTime().getTime()));
        }

        return descriptiveStatistics;
    }

    private double getSeconds(double value1, double value2) {
        return (value1 - value2) / 1000;
    }

    public Set<String> getFunctions() {
        Set<String> functions = new HashSet<>();
        functions.add(FunctionType.FUNCTION_STANDARD_DEVIATION.getName());
        functions.add(FunctionType.FUNCTION_ARITHMETIC_AVERAGE.getName());
        return functions;
    }
}
