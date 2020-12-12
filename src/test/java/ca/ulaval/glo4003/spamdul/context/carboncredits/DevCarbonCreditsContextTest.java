package ca.ulaval.glo4003.spamdul.context.carboncredits;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.ui.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DevCarbonCreditsContextTest {

  private DevCarbonCreditsContext context;
  private InstanceMap resources;

  @Mock
  private CarbonCreditsTransactionService carbonCreditsTransactionService;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private InitiativeCreator initiativeCreator;
  @Mock
  private AuthenticationRepository authenticationRepository;
  @Mock
  private AccessTokenCookieAssembler cookieAssembler;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevCarbonCreditsContext(carbonCreditsTransactionService,
                                          sustainabilityBankAccount,
                                          initiativeRepository,
                                          initiativeCreator,
                                          authenticationRepository,
                                          cookieAssembler);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     CarbonCreditsResource.class, CarbonCreditsResourceAdmin.class);
  }
}
