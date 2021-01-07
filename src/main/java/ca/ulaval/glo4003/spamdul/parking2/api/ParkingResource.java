package ca.ulaval.glo4003.spamdul.parking2.api;

import ca.ulaval.glo4003.spamdul.invoice.api.InvoiceUriBuilder;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.AccessRightCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.ParkingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit.PermitCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingAccessRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.ParkingUserResponse;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.accessright.AccessRightsCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.permit.PermitCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessRightUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingPermitUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUserUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.permit.PermitCreationDto;
import java.util.List;
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
  private final ParkingUserUseCase parkingUserUseCase;
  private final ParkingPermitUseCase parkingPermitUseCase;
  private final ParkingAccessRightUseCase parkingAccessRightUseCase;
  private final ParkingAccessUseCase parkingAccessUseCase;
  private final ParkingUserDtoAssembler parkingUserDtoAssembler;
  private final InvoiceUriBuilder invoiceUriBuilder;

  public ParkingResource(UserCreationAssembler userCreationAssembler,
                         PermitCreationAssembler permitCreationAssembler,
                         AccessRightCreationAssembler accessRightCreationAssembler,
                         ParkingAccessAssembler parkingAccessAssembler,
                         ParkingUserUseCase parkingUserUseCase,
                         ParkingPermitUseCase parkingPermitUseCase,
                         ParkingAccessRightUseCase parkingAccessRightUseCase,
                         ParkingAccessUseCase parkingAccessUseCase,
                         ParkingUserDtoAssembler parkingUserDtoAssembler,
                         InvoiceUriBuilder invoiceUriBuilder) {
    this.userCreationAssembler = userCreationAssembler;
    this.permitCreationAssembler = permitCreationAssembler;
    this.accessRightCreationAssembler = accessRightCreationAssembler;
    this.parkingAccessAssembler = parkingAccessAssembler;
    this.parkingUserUseCase = parkingUserUseCase;
    this.parkingPermitUseCase = parkingPermitUseCase;
    this.parkingAccessRightUseCase = parkingAccessRightUseCase;
    this.parkingAccessUseCase = parkingAccessUseCase;
    this.parkingUserDtoAssembler = parkingUserDtoAssembler;
    this.invoiceUriBuilder = invoiceUriBuilder;
  }

  @Path("user")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(UserCreationRequest request) {
    UserCreationDto dto = userCreationAssembler.fromRequest(request);
    ParkingUserDto parkingUserDto = parkingUserUseCase.createUser(dto);

    ParkingUserResponse response = parkingUserDtoAssembler.toResponse(parkingUserDto);
    return Response.status(201).entity(response).build();
  }

  @Path("user/{userId}")
  @GET
  public Response getUser(@PathParam("userId") String userId) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    ParkingUserDto parkingUserDto = parkingUserUseCase.getUser(parkingUserId);

    ParkingUserResponse response = parkingUserDtoAssembler.toResponse(parkingUserDto);
    return Response.status(200).entity(response).build();
  }

  @Path("user/{userId}/permit")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response orderPermit(@PathParam("userId") String userId, PermitCreationRequest request) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    PermitCreationDto dto = permitCreationAssembler.fromRequest(request);
    InvoiceId invoiceId = parkingPermitUseCase.orderPermit(parkingUserId, dto);

    return Response.created(invoiceUriBuilder.buildGet(invoiceId)).build();
  }

  @Path("user/{userId}/car/{carId}/access-right")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response orderAccessRight(@PathParam("userId") String userId,
                                   @PathParam("carId") String carId,
                                   AccessRightsCreationRequest request) {
    ParkingUserId parkingUserId = ParkingUserId.valueOf(userId);
    LicensePlate licensePlate = LicensePlate.valueOf(carId);
    List<AccessRightCreationDto> dtos = accessRightCreationAssembler.fromRequests(request.accessRights);
    InvoiceId invoiceId = parkingAccessRightUseCase.orderAccessRights(parkingUserId, licensePlate, dtos);

    return Response.created(invoiceUriBuilder.buildGet(invoiceId)).build();
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
