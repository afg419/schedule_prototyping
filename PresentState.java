package com.rachio.homedepot.service.schedule;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public abstract class PresentState {
  private final LocalDate date;
  private boolean hasHappened = false;

  public void happened(){
    this.hasHappened = true;
  }
}
