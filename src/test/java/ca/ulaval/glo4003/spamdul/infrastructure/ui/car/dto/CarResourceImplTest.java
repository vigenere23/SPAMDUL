package ca.ulaval.glo4003.spamdul.infrastructure.ui.car.dto;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.car.CarRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.CarAssembler;
import ca.ulaval.glo4003.spamdul.usecases.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.car.CarService;
import org.junit.Before;
import org.junit.Test;

public class CarResourceImplTest {

  private CarService carService;
  private CarAssembler carAssembler;
  private CarResourceImpl carResource;
  private CarRequest carRequest;
  private CarDto carDto;

  @Before
  public void setUp() throws Exception {
    carService = mock(CarService.class);
    carAssembler = mock(CarAssembler.class);
    carRequest = new CarRequest();
    carDto = new CarDto();
    carResource = new CarResourceImpl(carService, carAssembler);
    given(carAssembler.fromDto(carRequest)).willReturn(carDto);
  }

  @Test
  public void whenCreatingNewCar_shouldCallAssemblerToMapCarInfos() {
    carResource.createCar(carRequest);

    verify(carAssembler, times(1)).fromDto(carRequest);
  }

  @Test
  public void whenCreatingNewCar_shouldCallCarServiceToCreateCar() {
    carResource.createCar(carRequest);

    verify(carService, times(1)).createCar(carDto);
  }
}