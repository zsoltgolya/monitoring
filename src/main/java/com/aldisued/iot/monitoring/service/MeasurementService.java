package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

  private final SensorReadingRepository sensorReadingRepository;

  public MeasurementService(SensorReadingRepository sensorReadingRepository) {
    this.sensorReadingRepository = sensorReadingRepository;
  }

  public List<Double> getMeasurementValuesBySensorType(SensorType sensorType, LocalDateTime from, LocalDateTime to) {
    validPeriod(from, to);

    return sensorReadingRepository.findByTimestampBetweenOrderByTimestampAsc(from, to).stream()
      .filter(sr -> sr.getSensor().getType() == sensorType)
      .mapToDouble(SensorReading::getValue)
      .boxed()
      .toList();
  }

  public Optional<Double> getAverageTemperature(LocalDateTime from, LocalDateTime to) {
    validPeriod(from, to);

    return sensorReadingRepository.findByTimestampBetweenOrderByTimestampAsc(from, to).stream()
      .filter(sr -> SensorType.TEMPERATURE.equals(sr.getSensor().getType()))
      .mapToDouble(SensorReading::getValue)
      .average()
      .stream()
      .boxed()
      .findFirst();
  }

  private static void validPeriod(LocalDateTime from, LocalDateTime to) {
    if (from.isAfter(to)) {
      throw new IllegalArgumentException("'from' must be before 'to'");
    }
  }
}
