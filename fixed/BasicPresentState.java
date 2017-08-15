package com.rachio.homedepot.service.schedule.fixed;

import com.rachio.homedepot.service.schedule.PresentState;

import java.time.LocalDate;

public class BasicPresentState extends PresentState {
  public BasicPresentState(LocalDate date){
    super(date);
  }
}
