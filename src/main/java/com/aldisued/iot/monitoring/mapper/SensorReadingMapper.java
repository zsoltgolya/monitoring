package com.aldisued.iot.monitoring.mapper;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.entity.SensorReading;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorReadingMapper {
  SensorReading toEntity(SensorReadingDto dto);
}
