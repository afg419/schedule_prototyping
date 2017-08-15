package com.rachio.homedepot.service.schedule;

import com.google.common.collect.Lists;
import com.rachio.homedepot.service.schedule.conditionals.DefaultNoWaterConditional;
import com.rachio.homedepot.service.schedule.conditionals.ScheduleConditional;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.time.LocalDate;
import java.util.Collection;

@Getter
public class DayState<T extends PresentState> {
  private final LocalDate date;
  private final T presentState;
  private final Collection<ScheduleConditional<T>> stateSensitiveConditionals = Lists.newArrayList(new DefaultNoWaterConditional<T>());

  public DayState(LocalDate date, T presentState){
    this.date = date;
    this.presentState = presentState;
  }

  public WateringEvent getWateringEvent(){
    ScheduleConditional<T> triggeringConditional = stateSensitiveConditionals.stream()
                                                            .filter(conditional -> conditional.shouldExecute(this))
                                                            .max((cond1, cond2) -> cond1.getPriority() - cond2.getPriority())
                                                            .get();
    return triggeringConditional.getWateringEvent(this);
  }

  public void happened(){
    presentState.happened();
  }
}
