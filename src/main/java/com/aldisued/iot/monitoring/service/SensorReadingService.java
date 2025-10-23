package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.mapper.SensorReadingMapper;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SensorReadingService {

  private final SensorReadingRepository sensorReadingRepository;
  private final SensorRepository sensorRepository;
  private final SensorReadingMapper sensorReadingMapper;

  public SensorReadingService(SensorReadingRepository sensorReadingRepository,
      SensorRepository sensorRepository, SensorReadingMapper sensorReadingMapper) {
    this.sensorReadingRepository = sensorReadingRepository;
    this.sensorRepository = sensorRepository;
    this.sensorReadingMapper = sensorReadingMapper;
  }

  @Transactional
  public SensorReading saveSensorReading(SensorReadingDto sensorReadingDto) {
    UUID id = sensorReadingDto.sensorId();
    Sensor sensor = sensorRepository.findById(id)
      .orElseThrow(()-> new EntityNotFoundException("Sensor not found with id: " + id));

    var sensorReading = sensorReadingMapper.toEntity(sensorReadingDto);

    sensorReading.setSensor(sensor);

    return sensorReadingRepository.save(sensorReading);
  }

}
