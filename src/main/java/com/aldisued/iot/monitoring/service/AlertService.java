package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.exception.AlertNotFoundException;
import com.aldisued.iot.monitoring.mapper.AlertMapper;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AlertService {

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

  public Alert saveAlert(AlertDto alertDto) {
    // TODO: Task 6
    return null;
  }

  @Transactional(readOnly = true)
  public AlertDto findLastAlertBySensorId(UUID sensorId) {
    return alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId)
      .map(alertMapper::toDto)
      .orElseThrow(() -> new AlertNotFoundException("No alerts found for sensor: " + sensorId));
  }
}
