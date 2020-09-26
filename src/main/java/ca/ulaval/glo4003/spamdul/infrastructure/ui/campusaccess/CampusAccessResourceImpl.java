package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;

public class CampusAccessResourceImpl implements CampusAccessResource {

  private final CampusAccessAssembler campusAccessAssembler;
  private final CampusAccessService campusAccessService;

  public CampusAccessResourceImpl(CampusAccessAssembler campusAccessAssembler,
                                  CampusAccessService campusAccessService) {
    this.campusAccessAssembler = campusAccessAssembler;
    this.campusAccessService = campusAccessService;
  }

  public CampusAccessResponse createCampusAccess(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);
    CampusAccess campusAccess = campusAccessService.createNewCampusAccess(campusAccessDto);

    //    return Response.status(Status.CREATED)
    //                   .entity(campusAccessAssembler.toResponse(campusAccess))
    ////                   .header("ContentType", MediaType.APPLICATION_JSON)
    //                   .build();
    System.out.println(campusAccessAssembler.toResponse(campusAccess));
    return campusAccessAssembler.toResponse(campusAccess);
  }
}
