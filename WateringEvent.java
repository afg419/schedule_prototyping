package com.rachio.homedepot.service.schedule;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
public class WateringEvent {
  private final double amount;
  private final LocalDate date;
}
