package com.tesis.demo.model.mapper;

import com.tesis.demo.model.WeightedLoc;
import com.tesis.demo.model.dto.WeightedLocDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeightedLocMapper {

    public static WeightedLocDto toDto(WeightedLoc weightedLoc) {
        if (weightedLoc == null) {
            return null;
        }
        return  WeightedLocDto.builder()
                .location(PointMapper.toDto(weightedLoc.getLocation()))
                .weight(weightedLoc.getWeight())
                .build();
    }

    public static WeightedLoc toEntity(WeightedLocDto weightedLoc) {
        if (weightedLoc == null) {
            return null;
        }
        return  WeightedLoc.builder()
                .location(PointMapper.toEntity(weightedLoc.getLocation()))
                .weight(weightedLoc.getWeight())
                .build();
    }

    public static List<WeightedLoc> toEntityList(List<WeightedLocDto> weightedLocDtos){
        if (weightedLocDtos == null){
            return null;
        }
        return weightedLocDtos.stream().map(WeightedLocMapper::toEntity).collect(Collectors.toList());
    }

    public static List<WeightedLocDto> toDTOList(List<WeightedLoc> weightedLocs){
        if (weightedLocs == null){
            return null;
        }
        return weightedLocs.stream().map(WeightedLocMapper::toDto).collect(Collectors.toList());
    }
}
