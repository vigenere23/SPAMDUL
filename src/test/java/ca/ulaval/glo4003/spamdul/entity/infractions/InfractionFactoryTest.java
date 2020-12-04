package ca.ulaval.glo4003.spamdul.entity.infractions;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionFactoryTest {

  public static final String A_VALID_PASS_CODE_STRING = "568";
  public static final String AN_INFRACTION_DESCRIPTION = "dfhbjfebsik";
  public static final double AN_AMOUNT_DOUBLE = 0.599;
  public static final Amount AN_AMOUNT = Amount.valueOf(AN_AMOUNT_DOUBLE);
  public static final InfractionId AN_INFRACTION_ID = InfractionId.valueOf("123");

  @Mock
  private InfractionIdFactory infractionIdFactory;

  private InfractionInfos infractionInfos;
  private InfractionFactory infractionFactory;

  @Before
  public void setUp() {
    infractionInfos = new InfractionInfos();
    infractionFactory = new InfractionFactory(infractionIdFactory);
  }

  @Test
  public void givenInfractionInfos_whenCreatingPass_shouldCreateInfractionWithRightInfo() {
    when(infractionIdFactory.create()).thenReturn(AN_INFRACTION_ID);
    infractionInfos.code = A_VALID_PASS_CODE_STRING;
    infractionInfos.infraction = AN_INFRACTION_DESCRIPTION;
    infractionInfos.montant = AN_AMOUNT_DOUBLE;

    Infraction infraction = infractionFactory.create(infractionInfos);

    assertThat(infraction.getAmount()).isEqualTo(AN_AMOUNT);
    assertThat(infraction.getCode()).isEqualTo(InfractionCode.valueOf(A_VALID_PASS_CODE_STRING));
    assertThat(infraction.getInfractionDescription()).isEqualTo(AN_INFRACTION_DESCRIPTION);
    assertThat(infraction.getInfractionId()).isNotNull();
  }
}
