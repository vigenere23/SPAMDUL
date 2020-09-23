package ca.ulaval.glo4003.projet.base.ws.infrastructure.ui.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.projet.base.ws.entity.user.InvalidDayToAccessCampusException;
import ca.ulaval.glo4003.projet.base.ws.infrastructure.ui.user.dto.UserExceptionResponse;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.exceptions.InvalidBirthdayDateArgumentException;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.exceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.UserExceptionAssembler;
import javax.ws.rs.core.Response;
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

    UserExceptionResponse dto = (UserExceptionResponse) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_BIRTHDAY_DATE");
  }

  @Test
  public void givenAnInvalidGenderArgumentException_whenAssembling_shouldProduceTheRightError() {
    InvalidGenderArgumentException exception = new InvalidGenderArgumentException(A_MESSAGE);
    Response response = assembler.toResponse(exception);

    UserExceptionResponse dto = (UserExceptionResponse) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_GENDER");
  }

  @Test
  public void givenAnInvalidDayOfCampusAccessArgumentException_whenAssembling_shouldProduceTheRightError() {
    InvalidDayOfCampusAccessArgumentException exception = new InvalidDayOfCampusAccessArgumentException(A_MESSAGE);
    Response response = assembler.toResponse(exception);

    UserExceptionResponse dto = (UserExceptionResponse) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_DAY_OF_CAMPUS_ACCESS");
  }

  @Test
  public void givenAnInvalidDayOfCampusAccessException_whenAssembling_shouldProduceTheRightError() {
    InvalidDayToAccessCampusException exception = new InvalidDayToAccessCampusException(A_MESSAGE);
    Response response = assembler.toResponse(exception);

    UserExceptionResponse dto = (UserExceptionResponse) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(dto.error).isEqualTo("INVALID_DAY_OF_CAMPUS_ACCESS");
  }
}