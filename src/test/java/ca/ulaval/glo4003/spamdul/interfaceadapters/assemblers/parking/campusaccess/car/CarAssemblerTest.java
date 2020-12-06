package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.exceptions.InvalidCarTypeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.exceptions.InvalidCarYearArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.user.car.CarDto;
import org.junit.Before;
import org.junit.Test;

public class CarAssemblerTest {

  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE = "license plate";
  private final String A_YEAR_STRING = "2020";
  private final int A_YEAR = 2020;
  private final String A_CAR_TYPE_STRING = "gourmande";
  private final CarType A_CAR_TYPE = CarType.GOURMANDE;

  private CarRequest carRequest;
  private CarAssembler carAssembler;

  @Before
  public void setUp() throws Exception {
    carAssembler = new CarAssembler();
    carRequest = new CarRequest();
    carRequest.brand = A_CAR_BRAND;
    carRequest.model = A_CAR_MODEL;
    carRequest.year = A_YEAR_STRING;
    carRequest.licensePlate = A_LICENSE_PLATE;
    carRequest.type = A_CAR_TYPE_STRING;
  }

  @Test
  public void whenAssemblingFromRequest_shouldReturnDtoWithRightInfos() {
    CarDto carDto = carAssembler.fromRequest(carRequest);

    assertThat(carDto.brand).isEqualTo(A_CAR_BRAND);
    assertThat(carDto.model).isEqualTo(A_CAR_MODEL);
    assertThat(carDto.year).isEqualTo(A_YEAR);
    assertThat(carDto.licensePlate).isEqualTo(A_LICENSE_PLATE);
    assertThat(carDto.carType).isEqualTo(A_CAR_TYPE);
  }

  @Test(expected = InvalidCarYearArgumentException.class)
  public void givenANInvalidYear_whenAssemblingFromRequest_shouldThrowInvalidCarYearArgumentException() {
    carRequest.year = "invalid";

    carAssembler.fromRequest(carRequest);
  }

  @Test(expected = InvalidCarTypeArgumentException.class)
  public void givenAnInvalidCarType_whenAssemblingFromRequest_shouldThrowInvalidCarTypeArgumentException() {
    carRequest.type = "invalid";

    carAssembler.fromRequest(carRequest);
  }
}
