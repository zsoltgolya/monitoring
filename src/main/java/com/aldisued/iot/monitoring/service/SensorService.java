package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.SensorAlreadyExistsException;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

  private final SensorRepository sensorRepository;

  public SensorService(SensorRepository sensorRepository) {
    this.sensorRepository = sensorRepository;
  }

  public Sensor saveSensor(SensorDto sensorDto) {
    if(sensorRepository.existsByName(sensorDto.name())){
      throw new SensorAlreadyExistsException("Sensor with name '" + sensorDto.name() + "' already exists");
    }

    return sensorRepository.save(new Sensor(
        sensorDto.name(),
        sensorDto.type()
    ));
  }
}
