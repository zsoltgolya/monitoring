package com.aldisued.iot.monitoring.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    validDeviation(deviation);

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
    validWindowsSize(data.size(), windowSize);

    return IntStream.rangeClosed(0, data.size() - windowSize)
      .mapToObj(i -> data.subList(i, i + windowSize).stream()
        .mapToDouble(Double::doubleValue)
        .average()
        .orElse(0.0))
      .toList();
  }

  private void validDeviation(Double deviation) {
    if (deviation < 0.0 || deviation > 1.0) {
      throw new IllegalArgumentException("Deviation must be between 0.0 and 1.0");
    }
  }

  private void validWindowsSize(int sizeOfData, int windowSize) {
    if (windowSize <= 0) {
      throw new IllegalArgumentException("windowSize must be greater than 0.");
    }

    if (windowSize > sizeOfData) {
      throw new IllegalArgumentException("windowSize cannot be greater than the number of data.");
    }
  }

}
