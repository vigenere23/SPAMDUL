package ca.ulaval.glo4003.spamdul.assemblers.parking.bikeparking;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.ui.bikeparkingaccess.dto.BikeParkingAccessResponse;
import org.junit.Before;
import org.junit.Test;

public class BikeParkingAccessAssemblerTest {

  private BikeParkingAccessAssembler bikeParkingAccessAssembler;

  @Before
  public void setUp() throws Exception {
    bikeParkingAccessAssembler = new BikeParkingAccessAssembler();
  }

  @Test
  public void givenAccessGranted_whenAssemblingShouldSetAccessWithRightValue() {
    BikeParkingAccessResponse bikeParkingAccessResponse = bikeParkingAccessAssembler.toResponse(true);

    assertThat(bikeParkingAccessResponse.access).isEqualTo("GRANTED");
  }

  @Test
  public void givenAccessNotGranted_whenAssemblingShouldSetAccessWithRightValue() {
    BikeParkingAccessResponse bikeParkingAccessResponse = bikeParkingAccessAssembler.toResponse(false);

    assertThat(bikeParkingAccessResponse.access).isEqualTo("NOT_GRANTED");
  }
}
