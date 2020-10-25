package ca.ulaval.glo4003.spamdul.entity.infractions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class InfractionFactoryTest {

  public static final String A_VALID_PASS_CODE_STRING = "568";
  public static final String AN_INFRACTION_DESCRIPTION = "dfhbjfebsik";
  public static final double AN_AMOUNT = 0.599;

  private InfractionFactory infractionFactory = new InfractionFactory();
  private InfractionInfos infractionInfos = new InfractionInfos();

  @Test
  public void givenInfractionInfos_whenCreatingPass_shouldCreateInfractionWithRightInfo() {
    infractionInfos.code = A_VALID_PASS_CODE_STRING;
    infractionInfos.infraction = AN_INFRACTION_DESCRIPTION;
    infractionInfos.montant = AN_AMOUNT;

    Infraction infraction = infractionFactory.create(infractionInfos);

    assertThat(infraction.getAmount()).isEqualTo(AN_AMOUNT);
    assertThat(infraction.getCode()).isEqualTo(InfractionCode.valueOf(A_VALID_PASS_CODE_STRING));
    assertThat(infraction.getInfractionDescription()).isEqualTo(AN_INFRACTION_DESCRIPTION);
    assertThat(infraction.getInfractionId()).isNotNull();
  }
}
