package com.rachio.homedepot.service.schedule.util;

import java.time.*;
public class DateInstantUtil {
  public static Instant startOfLocalDay(LocalDate date, ZoneId timeZone){
    return date.atStartOfDay(timeZone).toInstant();
  }

  public static LocalDate localDateFromInstant(Instant instant, ZoneId timeZone){
    return LocalDateTime.ofInstant(instant, timeZone).toLocalDate();
  }

  public static ZonedDateTime zonedDateTimeFromInstant(Instant instant, ZoneId timeZone){
    return ZonedDateTime.ofInstant(instant, timeZone);
  }

  public static LocalTime localTimeFromInstant(Instant instant, ZoneId timeZone){
    return LocalDateTime.ofInstant(instant, timeZone).toLocalTime();
  }

  public static boolean isBetween(LocalDate middle, LocalDate start, LocalDate end){
    if(middle.equals(start)) return true;
    if(middle.equals(end)) return true;
    return (start.isBefore(middle) && end.isAfter(middle));
  }

  public static LocalDate atStartOfMonth(LocalDate date){
    return date.minusDays(date.getDayOfMonth() - 1);
  }

  public static LocalDate atStartOfMonthFromInstant(Instant instant, ZoneId timeZone){
    return atStartOfMonth(instant.atZone(timeZone).toLocalDate());
  }

  public static Instant whicheverIsFirst(Instant first, Instant second){
    if(first.isBefore(second)){
      return first;
    } else {
      return second;
    }
  }

  public static LocalDate whicheverIsFirst(LocalDate first, LocalDate second){
    if(first.isBefore(second)){
      return first;
    } else {
      return second;
    }
  }

  public static LocalDate dateInNewTimeZone(LocalDate date, ZoneId initialTimeZone, ZoneId desiredTimeZone){
    return localDateFromInstant(startOfLocalDay(date, initialTimeZone), desiredTimeZone);
  }
}
