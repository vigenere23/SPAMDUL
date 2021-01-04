package ca.ulaval.glo4003.spamdul.parking2.api;

import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.CarCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessRequestAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.CarCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingAccessRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.CarCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("parking")
public class ParkingResource {

  private final UserCreationAssembler userCreationAssembler;
  private final CarCreationAssembler carCreationAssembler;
  private final ParkingAccessRequestAssembler parkingAccessRequestAssembler;
  private final ParkingUseCase parkingUseCase;
  private final ParkingAccessUseCase parkingAccessUseCase;

  public ParkingResource(UserCreationAssembler userCreationAssembler,
                         CarCreationAssembler carCreationAssembler,
                         ParkingAccessRequestAssembler parkingAccessRequestAssembler,
                         ParkingUseCase parkingUseCase,
                         ParkingAccessUseCase parkingAccessUseCase) {
    this.userCreationAssembler = userCreationAssembler;
    this.carCreationAssembler = carCreationAssembler;
    this.parkingAccessRequestAssembler = parkingAccessRequestAssembler;
    this.parkingUseCase = parkingUseCase;
    this.parkingAccessUseCase = parkingAccessUseCase;
  }

  @Path("user")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void createUser(UserCreationRequest request) {
    UserCreationDto dto = userCreationAssembler.fromRequest(request);
    parkingUseCase.createUser(dto);
  }

  @Path("user/{userId}/car")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addCar(@PathParam("userId") String userId, CarCreationRequest request) {
    CarCreationDto dto = carCreationAssembler.fromRequest(request);
    parkingUseCase.addCarToUser(dto);
  }

  @Path("user/{userId}/car/{carId}/access-right")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addCarAccessRight(@PathParam("userId") String userId, @PathParam("carId") String carId/*, ... request*/) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    LicensePlate licensePlate = LicensePlate.valueOf(carId);
    // TODO
  }

  @Path("user/{id}/bike/permit")
  @POST
  public void createBikePermit(@PathParam("id") String userId) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    parkingUseCase.createBikePermit(parkingUserId);
  }

  @Path("access")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void access(ParkingAccessRequest request) {
    ParkingAccessDto dto = parkingAccessRequestAssembler.fromRequest(request);
    parkingAccessUseCase.accessParking(dto);
  }

  @Path("access/bike")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void accessBike(ParkingAccessRequest request) {
    ParkingAccessDto dto = parkingAccessRequestAssembler.fromBikeRequest(request);
    parkingAccessUseCase.accessParking(dto);
  }

  @Path("infraction")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void giveInfraction(ParkingAccessRequest request) {
    ParkingAccessDto dto = parkingAccessRequestAssembler.fromRequest(request);
    parkingAccessUseCase.giveInfraction(dto);
  }
}
