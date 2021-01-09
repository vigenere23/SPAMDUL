package ca.ulaval.glo4003.spamdul.parking2.api;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.api.BillingUriBuilder;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
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
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessRightUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingPermitUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.ParkingUserUseCase;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingAccessDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.ParkingUserDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;
import ca.ulaval.glo4003.spamdul.shared.api.ApiExceptionWrapper;
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
  private final BillingUriBuilder billingUriBuilder;

  public ParkingResource(UserCreationAssembler userCreationAssembler,
                         PermitCreationAssembler permitCreationAssembler,
                         AccessRightCreationAssembler accessRightCreationAssembler,
                         ParkingAccessAssembler parkingAccessAssembler,
                         ParkingUserUseCase parkingUserUseCase,
                         ParkingPermitUseCase parkingPermitUseCase,
                         ParkingAccessRightUseCase parkingAccessRightUseCase,
                         ParkingAccessUseCase parkingAccessUseCase,
                         ParkingUserDtoAssembler parkingUserDtoAssembler,
                         BillingUriBuilder billingUriBuilder) {
    this.userCreationAssembler = userCreationAssembler;
    this.permitCreationAssembler = permitCreationAssembler;
    this.accessRightCreationAssembler = accessRightCreationAssembler;
    this.parkingAccessAssembler = parkingAccessAssembler;
    this.parkingUserUseCase = parkingUserUseCase;
    this.parkingPermitUseCase = parkingPermitUseCase;
    this.parkingAccessRightUseCase = parkingAccessRightUseCase;
    this.parkingAccessUseCase = parkingAccessUseCase;
    this.parkingUserDtoAssembler = parkingUserDtoAssembler;
    this.billingUriBuilder = billingUriBuilder;
  }

  @Path("user/{accountId}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(@PathParam("accountId") String accountIdString, UserCreationRequest request) {
    AccountId accountId = AccountId.valueOf(accountIdString);
    UserCreationDto dto = userCreationAssembler.fromRequest(request);
    ParkingUserDto parkingUserDto = parkingUserUseCase.createUser(accountId, dto);

    ParkingUserResponse response = parkingUserDtoAssembler.toResponse(parkingUserDto);
    return Response.status(201).entity(response).build();
  }

  @Path("user/{accountId}")
  @GET
  public Response getUser(@PathParam("accountId") String accountIdString) {
    AccountId accountId = AccountId.valueOf(accountIdString);
    ParkingUserDto parkingUserDto = parkingUserUseCase.getUser(accountId);

    ParkingUserResponse response = parkingUserDtoAssembler.toResponse(parkingUserDto);
    return Response.status(200).entity(response).build();
  }

  @Path("user/{accountId}/permit")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response orderPermit(@PathParam("accountId") String accountIdString, PermitCreationRequest request) {
    AccountId accountId = AccountId.valueOf(accountIdString);
    PermitCreationDto dto = permitCreationAssembler.fromRequest(request);
    InvoiceId invoiceId = parkingPermitUseCase.orderPermit(accountId, dto);

    return Response.created(billingUriBuilder.buildInvoice(invoiceId)).build();
  }

  @Path("car/{carId}/access-right")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response orderAccessRight(@PathParam("carId") String carId,
                                   AccessRightsCreationRequest request) {
    LicensePlate licensePlate = LicensePlate.valueOf(carId);
    List<AccessRightCreationDto> dtos = accessRightCreationAssembler.fromRequests(request.accessRights);
    InvoiceId invoiceId = parkingAccessRightUseCase.orderAccessRights(licensePlate, dtos);

    return Response.created(billingUriBuilder.buildInvoice(invoiceId)).build();
  }

  @Path("access")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void access(ParkingAccessRequest request) {
    ApiExceptionWrapper.wrap(() -> {
      ParkingAccessDto dto = parkingAccessAssembler.fromRequest(request);
      parkingAccessUseCase.accessParking(dto);
    });
  }

  @Path("infraction")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void giveInfraction(ParkingAccessRequest request) {
    ParkingAccessDto dto = parkingAccessAssembler.fromRequest(request);
    parkingAccessUseCase.giveInfraction(dto);
  }
}
