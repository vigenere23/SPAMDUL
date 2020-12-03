package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.CampusAccessService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class CampusAccessResourceImpl implements CampusAccessResource {

  private final CampusAccessAssembler campusAccessAssembler;
  private final CampusAccessService campusAccessService;

  public CampusAccessResourceImpl(CampusAccessAssembler campusAccessAssembler,
                                  CampusAccessService campusAccessService) {
    this.campusAccessAssembler = campusAccessAssembler;
    this.campusAccessService = campusAccessService;
  }

  public Response createCampusAccess(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);
    CampusAccess campusAccess = campusAccessService.createCampusAccess(campusAccessDto);

    return Response.status(Status.CREATED)
                   .entity(campusAccessAssembler.toResponse(campusAccess))
                   .build();
  }

  public AccessingCampusResponse canAccessCampus(AccessingCampusRequest accessingCampusRequest) {
    AccessingCampusDto accessingCampusDto = campusAccessAssembler.fromRequest(accessingCampusRequest);
    boolean isAccessGranted = campusAccessService.grantAccessToCampus(accessingCampusDto);

    return campusAccessAssembler.toResponse(isAccessGranted);
  }
}
