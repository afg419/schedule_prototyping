package com.rachio.homedepot.service.schedule.fixedSkips;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class WeatherReport {
  private final LocalDate date;
  private final double precipAmount;
  private final double windSpeed;
  private final double temperature;
}
