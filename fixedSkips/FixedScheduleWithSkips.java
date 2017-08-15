package com.rachio.homedepot.service.schedule.fixedSkips;

import com.google.common.collect.Lists;
import com.rachio.homedepot.service.schedule.DayState;
import com.rachio.homedepot.service.schedule.Schedule;
import com.rachio.homedepot.service.schedule.conditionals.RainSkipConditional;
import com.rachio.homedepot.service.schedule.util.DateMap;

import java.time.LocalDate;

public class FixedScheduleWithSkips extends Schedule<WeatherIntelligenceState> {
  private final double wateringAmount;

  public FixedScheduleWithSkips(LocalDate startDate, LocalDate endDate, double wateringAmount, double precipThreshold, DateMap<WeatherReport> reportedWeather, DateMap<DateMap<WeatherReport>> predictedWeatherReports){
    super(startDate,
          endDate,
          DateMap.initWithDefaults(
            startDate,
            endDate,
            date -> new DayState<>(
              date,
              new WeatherIntelligenceState(date, reportedWeather.get(date), predictedWeatherReports.get(date))
            )
          )
    );
    this.wateringAmount = wateringAmount;

    addUniversalCondition(new RainSkipConditional(precipThreshold, wateringAmount));
  }
}
