package ca.ulaval.glo4003.spamdul.context.carboncredits;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.ui.carboncredits.CarbonCreditsResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ProdCarbonCreditsContextTest {

  private ProdCarbonCreditsContext context;
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
    context = new ProdCarbonCreditsContext(carbonCreditsTransactionService,
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
                                     CarbonCreditsResource.class);
  }
}
