package com.aldisued.iot.monitoring.service;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (deviation < 0.0 || deviation > 1.0) {
      throw new IllegalArgumentException("Deviation must be between 0.0 and 1.0");
    }

    double average = values.stream()
      .mapToDouble(v -> v)
      .average()
      .orElse(0);

    double limitDown = average - deviation * average;
    double limitUp = average + deviation * average;

    return values
      .stream()
      .filter(value -> limitDown < value && value < limitUp)
      .toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    // TODO: Task 10
    return List.of();
  }

}
