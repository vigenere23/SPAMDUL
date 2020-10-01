package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.usagereport.UsageReport;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDayDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsageReportAssembler {

  public UsageReportDto toDto(UsageReport usageReport) {
    UsageReportDto dto = new UsageReportDto();
    dto.parkingZone = usageReport.getParkingZone();
    List<UsageReportDayDto> usageReportDayDtos = new ArrayList<>();

    for (Map.Entry<LocalDate, Integer> entry : usageReport.getUsageReport().entrySet()) {
      UsageReportDayDto dayDto = new UsageReportDayDto();
      dayDto.date = entry.getKey();
      dayDto.numberOfEntry = entry.getValue();
      usageReportDayDtos.add(dayDto);
    }
    dto.usageReport = usageReportDayDtos;
    dto.totalOfEntry = usageReport.getTotalOfEntry();

    return dto;
  }
}
