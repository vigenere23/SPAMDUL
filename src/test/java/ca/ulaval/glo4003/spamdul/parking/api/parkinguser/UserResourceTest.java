package ca.ulaval.glo4003.spamdul.parking.api.parkinguser;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.api.parkinguser.dto.UserCreationResponse;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserCreationDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserUseCase;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

  public static final UserId USER_ID = UserId.valueOf("123");
  private UserResource userResource;
  private UserCreationRequest userCreationRequest;
  private UserCreationDto userCreationDto;

  @Mock
  private UserUseCase userUseCase;
  @Mock
  private UserAssembler userAssembler;

  @Before
  public void setUp() throws Exception {
    userResource = new UserResource(userAssembler, userUseCase);
    userCreationRequest = new UserCreationRequest();
    userCreationDto = new UserCreationDto();

    when(userAssembler.fromRequest(userCreationRequest)).thenReturn(userCreationDto);
  }

  @Test
  public void whenCreatingUser_shouldCallUserService() {
    when(userUseCase.createUser(userCreationDto)).thenReturn(USER_ID);

    userResource.createNewUser(userCreationRequest);

    verify(userUseCase).createUser(userCreationDto);
  }

  @Test
  public void whenCreatingUser_shouldReturnResponseWithRightInfo() {
    when(userUseCase.createUser(userCreationDto)).thenReturn(USER_ID);

    Response response = userResource.createNewUser(userCreationRequest);

    UserCreationResponse userCreationResponse = (UserCreationResponse) response.getEntity();
    assertThat(userCreationResponse.userId).isEqualTo(USER_ID.toString());
  }
}
