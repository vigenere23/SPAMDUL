package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import org.junit.Before;
import org.junit.Test;

public class CarbonCreditsServiceTest {

  private CarbonCreditsService carbonCreditsService;

  @Before
  public void setUp() {
    carbonCreditsService = new CarbonCreditsService();
  }

  @Test
  public void givenInitialization_whenTriggeringTransfer_showTransferFunds() {
    whenTriggeringTransfer_showTransferFunds();
  }

  @Test
  public void givenFeatureActive_whenTriggeringTransfer_showTransferFunds() {
    whenTriggeringTransfer_showTransferFunds();
  }

  private void whenTriggeringTransfer_showTransferFunds() {
    // TODO
  }

  @Test(/*expected = RuntimeException.class*/)
  public void givenFeatureDeactivated_whenTriggeringTransfer_showThrowException() {
  }
}
