package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ParkingAccessLogFilterTest {

    private ParkingAccessLogFilter parkingAccessLogFilter;
    private final ParkingAccessLog AN_ACCESS_LOG = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now());
    private final ParkingAccessLog AN_ACCESS_LOG_COPY = new ParkingAccessLog(new ParkingAccessLogId(), ParkingZone.ZONE_1, LocalDate.now());

    @Before
    public void setUp() {
        parkingAccessLogFilter = new ParkingAccessLogFilter();
    }

}
