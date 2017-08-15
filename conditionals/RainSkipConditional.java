package com.rachio.homedepot.service.schedule.conditionals;

import com.rachio.homedepot.service.schedule.DayState;
import com.rachio.homedepot.service.schedule.fixedSkips.WeatherIntelligenceState;

import java.util.Collection;

public class RainSkipConditional extends ScheduleConditional<WeatherIntelligenceState> {
  private final double precipThreshold;
  private final double wateringAmount;

  public RainSkipConditional(double precipThreshold, double wateringAmount){
    super(2);
    this.precipThreshold = precipThreshold;
    this.wateringAmount = wateringAmount;
  }

  public boolean shouldExecute(DayState<WeatherIntelligenceState> today){
    return today.getPresentState().getTodaysReport().getPrecipAmount() > precipThreshold;
  }

  public double wateringAmount(DayState<WeatherIntelligenceState> today){
    return wateringAmount;
  }
}
