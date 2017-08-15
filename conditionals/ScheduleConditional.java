package com.rachio.homedepot.service.schedule.conditionals;

import com.rachio.homedepot.service.schedule.DayState;
import com.rachio.homedepot.service.schedule.PresentState;
import com.rachio.homedepot.service.schedule.WateringEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public abstract class ScheduleConditional<S extends PresentState> {
  private final int priority;

  public abstract boolean shouldExecute(DayState<S> today);
  public abstract double wateringAmount(DayState<S> today);

  public WateringEvent getWateringEvent(DayState<S> today){
    return new WateringEvent(wateringAmount(today), today.getDate());
  }
}
