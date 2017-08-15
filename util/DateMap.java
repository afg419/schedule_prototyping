package com.rachio.homedepot.service.schedule.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DateMap<T> implements Map<LocalDate, T> {
  @Delegate
  private Map<LocalDate, T> contents = Maps.newHashMap();

  public boolean isFull(){
    List<LocalDate> dates = getDatesEarliestToLatest();
    boolean toReturn = true;
    for(int i = 1; i < dates.size(); i++){
      if(!dates.get(i-1).plusDays(1).equals(dates.get(i))){
        toReturn = false;
        break;
      }
    }
    return toReturn;
  }

  public void forEachInOrder(BiConsumer<? super LocalDate, ? super T> action){
    getDatesEarliestToLatest().forEach(
      date -> action.accept(date, contents.get(date))
    );
  }

  public static <T> DateMap<T> fromList(List<T> elements, Function<T, LocalDate> dateGrab){
    DateMap<T> mdm = new DateMap<>();
    elements.forEach( element -> mdm.put(dateGrab.apply(element), element));
    return mdm;
  }

  //Truncates datemap, *includes* start and end date if they exist.
  public DateMap<T> truncateBetween(LocalDate startDate, LocalDate endDate){
    Set<LocalDate> outOfBoundsDates = keySet().stream().filter(x ->
                                                                 x.isBefore(startDate) || x.isAfter(endDate)
    ).collect(Collectors.toSet());

    keySet().removeAll(outOfBoundsDates);
    return this;
  }

  public DateMap<T> fillMissingDatesWithDefaults(Function<LocalDate, T> dateOperator){
    LocalDate firstDate = firstDate();
    LocalDate lastDate = lastDate();

    DateMap<T> toReturn = new DateMap<>();
    toReturn.put(firstDate, contents.get(firstDate));
    toReturn.put(lastDate, contents.get(lastDate));

    LocalDate intermediaryDate = firstDate;
    while(intermediaryDate.isBefore(lastDate)){
      intermediaryDate = intermediaryDate.plusDays(1);
      T value = getOrDefault(intermediaryDate, dateOperator.apply(intermediaryDate));
      toReturn.put(intermediaryDate, value);
    }
    return toReturn;
  }

  public static <T> DateMap<T> merge(DateMap<T> firstMap, DateMap<T> secondMap, BiFunction<T,T,T> mergeValue){
    DateMap<T> toReturn = new DateMap<>(firstMap);
    secondMap.forEach( (date, value) -> {
      if(firstMap.containsKey(date)){
        toReturn.put(date, mergeValue.apply(value, firstMap.get(date)));
      } else {
        toReturn.put(date, value);
      }
    });
    return toReturn;
  }

  public DateMap<T> fillMissingDatesWithDefaults(LocalDate startDate, LocalDate lastDate, Function<LocalDate, T> dateOperator){
    DateMap<T> toReturn = new DateMap<>();

    LocalDate firstDate = startDate;
    if(!isEmpty()){
      firstDate = DateInstantUtil.whicheverIsFirst(firstDate(), startDate);
    }

    toReturn.put(firstDate, getOrDefault(firstDate, dateOperator.apply(firstDate)));
    toReturn.put(lastDate, getOrDefault(lastDate, dateOperator.apply(lastDate)));

    LocalDate intermediaryDate = firstDate;
    while(intermediaryDate.isBefore(lastDate)){
      intermediaryDate = intermediaryDate.plus(1, ChronoUnit.DAYS);
      T value = getOrDefault(intermediaryDate, dateOperator.apply(intermediaryDate));
      toReturn.put(intermediaryDate, value);
    }
    return toReturn;
  }

  public <R> DateMap<R> map(Function<T, R> valueOperator){
    return map((date, value) -> valueOperator.apply(value));
  }

  public <R> DateMap<R> map(BiFunction<LocalDate, T, R> keyValueOperator){
    DateMap<R> toReturn = new DateMap<>();
    forEach( (date, value) -> {
      toReturn.put(date, keyValueOperator.apply(date, value));
    });
    return toReturn;
  }

  public static <T> DateMap<T> initWithDefaults(LocalDate firstDate, LocalDate lastDate, Function<LocalDate, T> dateOperator){
    DateMap<T> toReturn = new DateMap<>();

    toReturn.put(firstDate, dateOperator.apply(firstDate));
    toReturn.put(lastDate, dateOperator.apply(lastDate));

    return toReturn.fillMissingDatesWithDefaults(firstDate, lastDate, dateOperator);
  }

  public List<LocalDate> getDatesEarliestToLatest(){
    List<LocalDate> dates = Lists.newArrayList(keySet());
    dates.sort((date1, date2) -> boolToInt(date1.isBefore(date2)));
    return dates;
  }

  private int boolToInt(boolean b){
    if(b){
      return -1;
    } else {
      return 1;
    }
  }

  public LocalDate firstDate(){
    return contents.keySet().stream().min(LocalDate::compareTo).orElseThrow(() -> new RuntimeException("This moisture date map has no elements. Can't return min date."));
  }

  public LocalDate lastDate(){
    return contents.keySet().stream().max(LocalDate::compareTo).orElseThrow(() -> new RuntimeException("This moisture date map has no elements. Can't return max date."));
  }

  public String toString(){
    String toReturn = "\t\t";
    for(LocalDate date: contents.keySet()){
      toReturn += date.toString() + ": " + contents.get(date).toString() + "\n\t\t";
    }
    return toReturn;
  }
}
