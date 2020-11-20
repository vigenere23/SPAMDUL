package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyChargingException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyExistsException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotDisconnectedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.InvalidChargingPointIdException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class ChargingPointExceptionMapper implements ExceptionMapper<ChargingPointException> {

  @Override
  public Response toResponse(ChargingPointException e) {
    ExceptionResponse response = new ExceptionResponse();
    response.description = e.getMessage();
    Status status = Status.BAD_REQUEST;

    if (e instanceof ChargingPointAlreadyActivatedException) {
      response.error = "CHARGING_POINT_ALREADY_ACTIVE";
    } else if (e instanceof ChargingPointAlreadyChargingException) {
      response.error = "CHARGING_POINT_ALREADY_CHARGING";
    } else if (e instanceof ChargingPointAlreadyExistsException) {
      response.error = "CHARGING_POINT_ALREADY_EXISTS";
    } else if (e instanceof ChargingPointNotActivatedException) {
      response.error = "CHARGING_POINT_NOT_ACTIVATED";
    } else if (e instanceof ChargingPointNotChargingException) {
      response.error = "CHARGING_POINT_NOT_CHARGING";
    } else if (e instanceof ChargingPointNotDisconnectedException) {
      response.error = "CHARGING_POINT_NOT_DISCONNECTED";
    } else if (e instanceof ChargingPointNotFoundException) {
      response.error = "CHARGING_POINT_NOT_FOUND";
      status = Status.NOT_FOUND;
    } else if (e instanceof InvalidChargingPointIdException) {
      response.error = "INVALID_CHARGING_POINT_ID";
    } else {
      response.error = "INVALID_CHARGING_POINT_OPERATION";
    }

    return Response.status(status).type(MediaType.APPLICATION_JSON).entity(response).build();
  }
}
