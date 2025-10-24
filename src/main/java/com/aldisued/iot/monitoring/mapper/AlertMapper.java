package com.aldisued.iot.monitoring.mapper;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper {
  AlertDto toDto(Alert alert);
}
