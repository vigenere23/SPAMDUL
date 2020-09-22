package ca.ulaval.glo4003.projet.base.ws.api.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserExceptionDto;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidBirthdayDateArgumentException;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidGenderArgumentException;
import javax.ws.rs.core.Response;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class UserExceptionAssemblerTest {

  private String A_MESSAGE;
  private UserExceptionAssembler assembler;

  @Before
  public void setUp() throws Exception {
    A_MESSAGE = "message";
    assembler = new UserExceptionAssembler();
  }

  @Test
  public void givenAnInvalidBirthdayDateArgumentException_whenAssembling_shouldProduceTheRightError() {
    InvalidBirthdayDateArgumentException exception = new InvalidBirthdayDateArgumentException(A_MESSAGE);

    Response response = assembler.toResponse(exception);

    UserExceptionDto dto = (UserExceptionDto) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_BIRTHDAY_DATE");
  }

  @Test
  public void givenAnInvalidGenderArgumentException_whenAssembling_shouldProduceTheRightError() {
    InvalidGenderArgumentException exception = new InvalidGenderArgumentException(A_MESSAGE);
    Response response = assembler.toResponse(exception);

    UserExceptionDto dto = (UserExceptionDto) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_GENDER");
  }

  @Test
  public void givenAnInvalidDayOfCampusAccessArgumentException_whenAssembling_shouldProduceTheRightError() {
    InvalidDayOfCampusAccessArgumentException exception = new InvalidDayOfCampusAccessArgumentException(A_MESSAGE);
    Response response = assembler.toResponse(exception);

    UserExceptionDto dto = (UserExceptionDto) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_DAY_OF_CAMPUS_ACCESS");
  }
}