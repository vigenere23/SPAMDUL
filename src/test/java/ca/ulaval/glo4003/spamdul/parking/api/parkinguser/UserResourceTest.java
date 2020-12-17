package ca.ulaval.glo4003.spamdul.parking.api.parkinguser;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.parking.api.parkinguser.dto.UserResponse;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserDto;
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
  private UserRequest userRequest;
  private UserDto userDto;

  @Mock
  private UserUseCase userUseCase;
  @Mock
  private UserAssembler userAssembler;

  @Before
  public void setUp() throws Exception {
    userResource = new UserResource(userAssembler, userUseCase);
    userRequest = new UserRequest();
    userDto = new UserDto();

    when(userAssembler.fromRequest(userRequest)).thenReturn(userDto);
  }

  @Test
  public void whenCreatingUser_shouldCallUserService() {
    when(userUseCase.createUser(userDto)).thenReturn(USER_ID);

    userResource.createNewUser(userRequest);

    verify(userUseCase).createUser(userDto);
  }

  @Test
  public void whenCreatingUser_shouldReturnResponseWithRightInfo() {
    when(userUseCase.createUser(userDto)).thenReturn(USER_ID);

    Response response = userResource.createNewUser(userRequest);

    UserResponse userResponse = (UserResponse) response.getEntity();
    assertThat(userResponse.userId).isEqualTo(USER_ID.toString());
  }
}
