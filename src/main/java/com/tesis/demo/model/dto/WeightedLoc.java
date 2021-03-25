package com.tesis.demo.model.dto;

import com.tesis.demo.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightedLoc {

    protected Point location;
    protected double weight;

}
