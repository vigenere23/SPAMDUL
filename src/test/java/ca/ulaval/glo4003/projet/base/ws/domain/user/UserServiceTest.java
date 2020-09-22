package ca.ulaval.glo4003.projet.base.ws.domain.user;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

  private UserRepository userRepository;
  private UserAssembler userAssembler;
  private UserDto userDto;
  private UserService userService;


  @Before
  public void setUp() throws Exception {
    userAssembler = mock(UserAssembler.class);
    userRepository = mock(UserRepository.class);
    userDto = new UserDto();
    userService = new UserService(userAssembler, userRepository);
  }

  @Test
  public void whenAddingNewUser_shouldCallAssembler() {
    userService.addUser(userDto);

    verify(userAssembler, times(1)).create(userDto);
  }

  @Test
  public void whenAddingNewUser_shouldAddUserToRepository() {
    User user = new User();
    given(userAssembler.create(userDto)).willReturn(user);
    userService.addUser(userDto);

    verify(userRepository, times(1)).save(user);
  }
}