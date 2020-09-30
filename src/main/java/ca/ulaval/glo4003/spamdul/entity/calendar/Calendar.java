package ca.ulaval.glo4003.spamdul.entity.calendar;

import java.time.LocalDate;

public interface Calendar {

  LocalDate getCurrentTermStartDate();

  LocalDate getEndOfTermDateInNTerms(int numberOfTerms);

}
