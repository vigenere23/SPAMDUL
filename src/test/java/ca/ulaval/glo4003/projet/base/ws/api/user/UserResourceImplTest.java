package ca.ulaval.glo4003.projet.base.ws.api.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.projet.base.ws.domain.user.User;
import ca.ulaval.glo4003.projet.base.ws.domain.user.UserService;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

public class UserResourceImplTest {

  private UserService userService;
  private UserDto userDto;
  private UserResource resource;
  private User user;

  @Before
  public void setUp() throws Exception {
    userService = mock(UserService.class);
    userDto = new UserDto();
    resource = new UserResourceImpl(userService);
    user = new User();
    willReturn(user).given(userService).addUser(userDto);
  }

  @Test
  public void whenAddingUser_shouldCallUserService() {
    resource.addUser(userDto);

    verify(userService, times(1)).addUser(userDto);
  }

  @Test
  public void whenAddingUser_shouldReturnUserIdInLocation() {
    Response response = resource.addUser(userDto);

    String Location = (String) response.getHeaders().get("Location").get(0);
    assertThat(Location).isEqualTo(String.format("/users/%s",
                                                 user.getId().toString()));
  }
}