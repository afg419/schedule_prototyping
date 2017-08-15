package com.rachio.homedepot.service.schedule.conditionals;

import com.rachio.homedepot.service.schedule.DayState;
import com.rachio.homedepot.service.schedule.PresentState;
import com.rachio.homedepot.service.schedule.fixed.BasicPresentState;

import java.util.Collection;

public class DefaultNoWaterConditional<T  extends BasicPresentState> extends ScheduleConditional<T> {
  public DefaultNoWaterConditional(){
    super(0);
  }

  public boolean shouldExecute(DayState<T> today){
    return true;
  }

  public double wateringAmount(DayState<T> today){
    return 0;
  }
}
