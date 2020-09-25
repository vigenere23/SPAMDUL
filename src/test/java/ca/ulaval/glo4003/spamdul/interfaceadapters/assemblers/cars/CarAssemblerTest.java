package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.car.CarRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.exceptions.InvalidCarYearArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.exceptions.InvalidUserIdArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.car.CarDto;
import org.junit.Before;
import org.junit.Test;

public class CarAssemblerTest {

  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE = "license plate";
  private final String A_USER_ID_STRING = "1";
  private final UserId A_USER_ID = UserId.valueOf(A_USER_ID_STRING);
  private final String A_YEAR_STRING = "2020";
  private final int A_YEAR = 2020;

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
    carRequest.userId = A_USER_ID_STRING;
  }

  @Test
  public void whenAssemblingFromRequest_shouldReturnDtoWithRightInfos() {
    CarDto carDto = carAssembler.fromDto(carRequest);

    assertThat(carDto.brand).isEqualTo(A_CAR_BRAND);
    assertThat(carDto.model).isEqualTo(A_CAR_MODEL);
    assertThat(carDto.year).isEqualTo(A_YEAR);
    assertThat(carDto.licensePlate).isEqualTo(A_LICENSE_PLATE);
    assertThat(carDto.userId).isEqualTo(A_USER_ID);
  }

  @Test(expected = InvalidCarYearArgumentException.class)
  public void givenANInvalidYear_whenAssemblingFromRequest_shouldThrowInvalidCarYearArgumentException() {
    carRequest.year = "invalid";

    carAssembler.fromDto(carRequest);
  }

  @Test(expected = InvalidUserIdArgumentException.class)
  public void givenANInvalidUserId_whenAssemblingFromRequest_shouldThrowInvalidCarYearArgumentException() {
    carRequest.userId = "invalid";

    carAssembler.fromDto(carRequest);
  }
}