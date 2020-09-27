package ca.ulaval.glo4003.spamdul.infrastructure.ui.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.user.UserService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

public class UserResourceImplTest {

  private final User A_USER = new User(new UserId(),
                                       "Bob Ross",
                                       Gender.MALE,
                                       LocalDate.of(2004, 1, 1),
                                       DayOfWeek.MONDAY);

  private UserService userService;
  private UserAssembler userAssembler;
  private UserRequest userRequest;
  private UserResource resource;
  private UserDto userDto;

  @Before
  public void setUp() throws Exception {
    userService = mock(UserService.class);
    userAssembler = mock(UserAssembler.class);
    userRequest = new UserRequest();
    resource = new UserResourceImpl(userService, userAssembler);
    userDto = new UserDto();
    given(userAssembler.fromDto(userRequest)).willReturn(userDto);
    given(userService.createUser(userDto)).willReturn(A_USER);
  }

  @Test
  public void whenCreatingUser_shouldCallAssembler() {
    resource.addUser(userRequest);

    verify(userAssembler, times(1)).fromDto(userRequest);
  }

  @Test
  public void whenCreatingUser_shouldCallUserService() {
    resource.addUser(userRequest);

    verify(userService, times(1)).createUser(userDto);
  }

  @Test
  public void whenCreatingUser_shouldReturnUserIdInLocation() {
    Response response = resource.addUser(userRequest);

    String Location = (String) response.getHeaders().get("Location").get(0);
    assertThat(Location).isEqualTo(String.format("/users/%s",
                                                 A_USER.getId().toString()));
  }
}