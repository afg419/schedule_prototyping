package com.rachio.homedepot.service.schedule.fixedSkips;

import com.rachio.homedepot.service.schedule.PresentState;
import com.rachio.homedepot.service.schedule.fixed.BasicPresentState;
import com.rachio.homedepot.service.schedule.util.DateMap;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WeatherIntelligenceState extends BasicPresentState {
  DateMap<WeatherReport> predictions;
  WeatherReport todaysReport;

  public WeatherIntelligenceState(LocalDate date,  WeatherReport todaysReport, DateMap<WeatherReport> predictions){
    super(date);
    this.predictions = predictions;
    this.todaysReport = todaysReport;
  }
}
