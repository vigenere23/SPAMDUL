package ca.ulaval.glo4003.spamdul.infrastructure.ui.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.user.UserService;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

  public static final UserId USER_ID = new UserId();
  private UserResource userResource;
  private UserRequest userRequest;
  private UserDto userDto;

  @Mock
  private UserService userService;
  @Mock
  private UserAssembler userAssembler;

  @Before
  public void setUp() throws Exception {
    userResource = new UserResource(userAssembler, userService);
    userRequest = new UserRequest();
    userDto = new UserDto();

    when(userAssembler.fromRequest(userRequest)).thenReturn(userDto);
  }

  @Test
  public void whenCreatingUser_shouldCallUserService() {
    when(userService.createUser(userDto)).thenReturn(USER_ID);

    userResource.createNewUser(userRequest);

    verify(userService, times(1)).createUser(userDto);
  }

  @Test
  public void whenCreatingUser_shouldReturnResponseWithRightInfo() {
    when(userService.createUser(userDto)).thenReturn(USER_ID);

    Response response = userResource.createNewUser(userRequest);

    UserResponse userResponse = (UserResponse) response.getEntity();
    assertThat(userResponse.userId).isEqualTo(USER_ID.toString());
  }
}