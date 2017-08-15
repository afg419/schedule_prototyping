package com.rachio.homedepot.service.schedule;

import com.rachio.homedepot.service.schedule.conditionals.ScheduleConditional;
import com.rachio.homedepot.service.schedule.util.DateMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public abstract class Schedule<T extends PresentState> {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final DateMap<DayState<T>> daysInPeriod;

  protected void addSpecificCondition(LocalDate date, ScheduleConditional<T> conditional){
    if(daysInPeriod.containsKey(date)){
      daysInPeriod.get(date).getStateSensitiveConditionals().add(conditional);
    }
  }

  protected void addUniversalCondition(ScheduleConditional<T> conditional){
    daysInPeriod.values().forEach( dayState -> {
      dayState.getStateSensitiveConditionals().add(conditional);
    });
  }

  private DateMap<T> getAllStates(){
    return daysInPeriod.map(DayState::getPresentState);
  }



  public DateMap<WateringEvent> getWateringEvents(){
    DateMap<WateringEvent> toReturn = new DateMap<>();

    daysInPeriod.forEachInOrder( (date, dayState) -> {
      toReturn.put(date, dayState.getWateringEvent());
      dayState.happened();
    });

    return toReturn;
  }
}
