package com.rachio.homedepot.service.schedule.conditionals;

import com.rachio.homedepot.service.schedule.DayState;
import com.rachio.homedepot.service.schedule.PresentState;
import com.rachio.homedepot.service.schedule.fixed.BasicPresentState;

import java.util.Collection;

public class DefaultWaterConditional<T  extends BasicPresentState> extends ScheduleConditional<T> {
  private final double wateringAmount;

  public DefaultWaterConditional(double wateringAmount){
    super(1);
    this.wateringAmount = wateringAmount;
  }

  public boolean shouldExecute(DayState<T> today){
    return true;
  }

  public double wateringAmount(DayState<T> today){
    return wateringAmount;
  }
}