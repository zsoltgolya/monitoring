package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.AlertNotFoundException;
import com.aldisued.iot.monitoring.mapper.AlertMapper;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AlertService {

  public static final String TOPIC_ALERTS = "alerts";
  private final AlertRepository alertRepository;
  private final SensorRepository sensorRepository;
  private final KafkaTemplate<String, AlertDto> kafkaTemplate;
  private final AlertMapper alertMapper;

  public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
                      KafkaTemplate<String, AlertDto> kafkaTemplate, AlertMapper alertMapper) {
    this.alertRepository = alertRepository;
    this.sensorRepository = sensorRepository;
    this.kafkaTemplate = kafkaTemplate;
    this.alertMapper = alertMapper;
  }

  @Transactional
  public Alert saveAlert(AlertDto alertDto) {
    UUID sensorId = alertDto.sensorId();
    Sensor sensor = sensorRepository.findById(sensorId)
      .orElseThrow(() -> new EntityNotFoundException("Sensor not found with id: " + sensorId));

    Alert alert = alertMapper.toEntity(alertDto);
    alert.setSensor(sensor);

    Alert savedAlert = alertRepository.save(alert);
    kafkaTemplate.send(TOPIC_ALERTS, alertDto);

    return savedAlert;
  }

  @Transactional(readOnly = true)
  public AlertDto findLastAlertBySensorId(UUID sensorId) {
    return alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId)
      .map(alertMapper::toDto)
      .orElseThrow(() -> new AlertNotFoundException("No alerts found for sensor: " + sensorId));
  }
}
