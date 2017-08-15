package com.rachio.homedepot.service.schedule.fixed;

import com.rachio.homedepot.service.schedule.DayState;
import com.rachio.homedepot.service.schedule.conditionals.DefaultWaterConditional;
import com.rachio.homedepot.service.schedule.Schedule;
import com.rachio.homedepot.service.schedule.util.DateMap;

import java.time.LocalDate;
import java.util.Collection;

public class FixedSchedule extends Schedule<BasicPresentState> {
  private final double wateringAmount;

  public FixedSchedule(LocalDate startDate, LocalDate endDate, double wateringAmount){
    super(startDate, endDate, DateMap.initWithDefaults(startDate, endDate, date -> new DayState<>(date, new BasicPresentState(date))));
    this.wateringAmount = wateringAmount;
  }

  private void addWateringDay(LocalDate date){
    addSpecificCondition(date, new DefaultWaterConditional<>(wateringAmount));
  }

  public void addWateringDaysByInterval(int waterEveryXDays){
    int counter = 0;
    for(LocalDate date: getDaysInPeriod().getDatesEarliestToLatest()){
      if(counter % waterEveryXDays == 0){
        addWateringDay(date);
      }
      counter ++;
    }
  }

  public void addWateringDays(Collection<LocalDate> datesToWater){
    datesToWater.forEach(this::addWateringDay);
  }
}
