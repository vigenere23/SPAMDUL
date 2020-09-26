package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingAccessLogAgglomerator {
    // TODO finish
    public Map<LocalDate, List<ParkingAccessLog>> groupPerDay(List<ParkingAccessLog> logs) {
        Map<LocalDate, List<ParkingAccessLog>> logsPerDay = new HashMap<>();
        logs.forEach(log -> {
            List<ParkingAccessLog> logsForThisDay = logsPerDay.get(log.getAccessDate());
            if (logsForThisDay == null) {
                logsForThisDay = new ArrayList<>();
            }

            logsForThisDay.add(log);
        });

        return logsPerDay;
    }
}
