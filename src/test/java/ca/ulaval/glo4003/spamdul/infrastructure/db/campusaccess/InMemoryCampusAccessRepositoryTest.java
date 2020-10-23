package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;

public class InMemoryCampusAccessRepositoryTest {

  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private final CampusAccess A_CAMPUS_ACCESS = new CampusAccess(A_CAMPUS_ACCESS_CODE,
                                                                new UserId(),
                                                                new CarId(),
                                                                null);

  private InMemoryCampusAccessRepository campusAccessRepository;

  @Before
  public void setUp() throws Exception {
    campusAccessRepository = new InMemoryCampusAccessRepository();
  }

  @Test
  public void whenSavingCampusAccess_campusAccessShouldBeSaved() {
    CampusAccessCode code = campusAccessRepository.save(A_CAMPUS_ACCESS);

    assertThat(campusAccessRepository.findById(code)).isEqualTo(A_CAMPUS_ACCESS);
  }

  @Test
  public void whenFindingById_shouldReturnCampusAccess() {
    campusAccessRepository.save(A_CAMPUS_ACCESS);

    CampusAccess campusAccess = campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE);

    assertThat(campusAccess).isEqualTo(A_CAMPUS_ACCESS);
  }

  @Test(expected = CampusAccessNotFoundException.class)
  public void givenNoCampusAccessCorrespondingToCampusAccessCode_whenFindingById_shouldThrowCampusAccessNotFoundExcetpion() {
    campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE);
  }
}