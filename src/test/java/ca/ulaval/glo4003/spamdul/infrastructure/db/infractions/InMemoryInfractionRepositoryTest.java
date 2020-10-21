package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class InMemoryInfractionRepositoryTest {
  private static final String A_INFRACTION_CODE_STRING = "test";
  private static final InfractionCode A_INFRACTION_CODE = InfractionCode.valueOf(A_INFRACTION_CODE_STRING);
  private static final double A_INFRACTION_AMOUNT = 5;
  private static final String A_INFRACTION_DESCRIPTION = "test description";
  private static final InfractionId A_INFRACTION_ID = new InfractionId();
  private static final InfractionId A_NON_EXISTING_INFRACTION_ID = new InfractionId();
  private static final Infraction A_INFRACTION = new Infraction(A_INFRACTION_ID, A_INFRACTION_DESCRIPTION, A_INFRACTION_CODE, A_INFRACTION_AMOUNT);

  private InfractionRepository infractionRepository;

  @Before
  public void setUp(){
    infractionRepository = new InMemoryInfractionRepository();
  }

  @Test
  public void givenASavedInfraction_whenFindingByInfractionId_thenShouldReturnRightInfraction(){
    infractionRepository.save(A_INFRACTION);

    assertThat(infractionRepository.findBy(A_INFRACTION_ID)).isEqualTo(A_INFRACTION);
  }

  @Test(expected = InfractionNotFoundException.class)
  public void givenSavedInfraction_whenFindingANonExistingInfractionId_thenShouldThrowInfractionNotFoundException(){
    infractionRepository.save(A_INFRACTION);

    infractionRepository.findBy(A_NON_EXISTING_INFRACTION_ID);
  }
}
