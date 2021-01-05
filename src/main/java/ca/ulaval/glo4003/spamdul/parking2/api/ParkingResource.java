package ca.ulaval.glo4003.spamdul.parking2.api;

import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.AccessRightCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingAccessRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.PermitCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("parking2")
public class ParkingResource {

  private final UserCreationAssembler userCreationAssembler;
  private final PermitCreationAssembler permitCreationAssembler;
  private final AccessRightCreationAssembler accessRightCreationAssembler;
  private final ParkingAccessAssembler parkingAccessAssembler;
  private final ParkingUseCase parkingUseCase;
  private final ParkingAccessUseCase parkingAccessUseCase;

  public ParkingResource(UserCreationAssembler userCreationAssembler,
                         PermitCreationAssembler permitCreationAssembler,
                         AccessRightCreationAssembler accessRightCreationAssembler,
                         ParkingAccessAssembler parkingAccessAssembler,
                         ParkingUseCase parkingUseCase,
                         ParkingAccessUseCase parkingAccessUseCase) {
    this.userCreationAssembler = userCreationAssembler;
    this.permitCreationAssembler = permitCreationAssembler;
    this.accessRightCreationAssembler = accessRightCreationAssembler;
    this.parkingAccessAssembler = parkingAccessAssembler;
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

  @Path("user/{userId}/permit")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addPermit(@PathParam("userId") String userId, PermitCreationRequest request) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    PermitCreationDto dto = permitCreationAssembler.fromRequest(request);
    parkingUseCase.addPermitToUser(parkingUserId, dto);
  }

  @Path("user/{userId}/car/{carId}/access-right")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addCarAccessRight(@PathParam("userId") String userId,
                                @PathParam("carId") String carId,
                                AccessRightCreationRequest request) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    LicensePlate licensePlate = LicensePlate.valueOf(carId);
    AccessRightCreationDto dto = accessRightCreationAssembler.fromRequest(request);
    parkingUseCase.addAccessRightToUser(parkingUserId, licensePlate, dto);
  }

  @Path("access")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void access(ParkingAccessRequest request) {
    ParkingAccessDto dto = parkingAccessAssembler.fromRequest(request);
    parkingAccessUseCase.accessParking(dto);
  }

  @Path("infraction")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void giveInfraction(ParkingAccessRequest request) {
    ParkingAccessDto dto = parkingAccessAssembler.fromRequest(request);
    parkingAccessUseCase.giveInfraction(dto);
  }
}
