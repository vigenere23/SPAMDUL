package ca.ulaval.glo4003.spamdul.infrastructure.ui.car.dto;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.car.CarRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.car.CarResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.CarAssembler;
import ca.ulaval.glo4003.spamdul.usecases.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.car.CarService;
import javax.ws.rs.core.Response;

public class CarResourceImpl implements CarResource {

  private CarService carService;
  private CarAssembler carAssembler;

  public CarResourceImpl(CarService carService, CarAssembler carAssembler) {

    this.carService = carService;
    this.carAssembler = carAssembler;
  }

  public Response createCar(CarRequest carRequest) {
    CarDto carDto = carAssembler.fromDto(carRequest);
    carService.createCar(carDto);

    return null;
  }
}
