package ca.ulaval.glo4003.spamdul.parking2.api;

import ca.ulaval.glo4003.spamdul.invoice.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.invoice.api.dtos.InvoiceResponse;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.PermitCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.AccessRightCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingAccessRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingUserResponse;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.PermitCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("parking")
public class ParkingResource {

  private final UserCreationAssembler userCreationAssembler;
  private final PermitCreationAssembler permitCreationAssembler;
  private final AccessRightCreationAssembler accessRightCreationAssembler;
  private final ParkingAccessAssembler parkingAccessAssembler;
  private final ParkingUseCase parkingUseCase;
  private final ParkingAccessUseCase parkingAccessUseCase;
  private final ParkingUserDtoAssembler parkingUserDtoAssembler;
  private final InvoiceDtoAssembler invoiceDtoAssembler;

  public ParkingResource(UserCreationAssembler userCreationAssembler,
                         PermitCreationAssembler permitCreationAssembler,
                         AccessRightCreationAssembler accessRightCreationAssembler,
                         ParkingAccessAssembler parkingAccessAssembler,
                         ParkingUseCase parkingUseCase,
                         ParkingAccessUseCase parkingAccessUseCase,
                         ParkingUserDtoAssembler parkingUserDtoAssembler,
                         InvoiceDtoAssembler invoiceDtoAssembler) {
    this.userCreationAssembler = userCreationAssembler;
    this.permitCreationAssembler = permitCreationAssembler;
    this.accessRightCreationAssembler = accessRightCreationAssembler;
    this.parkingAccessAssembler = parkingAccessAssembler;
    this.parkingUseCase = parkingUseCase;
    this.parkingAccessUseCase = parkingAccessUseCase;
    this.parkingUserDtoAssembler = parkingUserDtoAssembler;
    this.invoiceDtoAssembler = invoiceDtoAssembler;
  }

  @Path("user")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(UserCreationRequest request) {
    UserCreationDto dto = userCreationAssembler.fromRequest(request);
    ParkingUserDto parkingUserDto = parkingUseCase.createUser(dto);

    ParkingUserResponse response = parkingUserDtoAssembler.toResponse(parkingUserDto);
    return Response.status(201).entity(response).build();
  }

  @Path("user/{userId}")
  @GET
  public Response getUser(@PathParam("userId") String userId) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    ParkingUserDto parkingUserDto = parkingUseCase.getUser(parkingUserId);

    ParkingUserResponse response = parkingUserDtoAssembler.toResponse(parkingUserDto);
    return Response.status(200).entity(response).build();
  }

  @Path("user/{userId}/permit")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response orderPermit(@PathParam("userId") String userId, PermitCreationRequest request) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    PermitCreationDto dto = permitCreationAssembler.fromRequest(request);
    InvoiceDto invoiceDto = parkingUseCase.orderPermit(parkingUserId, dto);

    InvoiceResponse response = invoiceDtoAssembler.toResponse(invoiceDto);
    return Response.status(201).entity(response).build();
  }

  @Path("user/{userId}/car/{carId}/access-right")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response orderAccessRight(@PathParam("userId") String userId,
                                   @PathParam("carId") String carId,
                                   AccessRightCreationRequest request) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    LicensePlate licensePlate = LicensePlate.valueOf(carId);
    AccessRightCreationDto dto = accessRightCreationAssembler.fromRequest(request);
    InvoiceDto invoiceDto = parkingUseCase.orderAccessRight(parkingUserId, licensePlate, dto);

    InvoiceResponse response = invoiceDtoAssembler.toResponse(invoiceDto);
    return Response.status(201).entity(response).build();
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
