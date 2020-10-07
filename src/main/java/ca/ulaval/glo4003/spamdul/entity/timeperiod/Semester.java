package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.util.Objects;

public class Semester {
    private final char season;
    private final int year;

    public Semester(char season, int year) {
        this.season = season;
        this.year = year;
    }

    public Semester addSemester(int numberOfSemester) {
        int remainder = numberOfSemester % 3;
        int plusYear = numberOfSemester / 3;
        if (remainder == 0) {
            return new Semester(season, year + plusYear);
        }
        return nextSemester().addSemester(numberOfSemester - 1);
    }

    private Semester nextSemester() {
        if (season == 'A') {
            return new Semester('H', year+1);
        } else if (season == 'H') {
            return new Semester('E', year);
        } else {
            return new Semester('A', year);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return season == semester.season &&
                year == semester.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, year);
    }
}
