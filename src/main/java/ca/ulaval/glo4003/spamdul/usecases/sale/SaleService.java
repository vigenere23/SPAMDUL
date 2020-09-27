package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.usecases.email.EmailService;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import ca.ulaval.glo4003.spamdul.usecases.post.PostalService;


public class SaleService {

  private PassService passService;
  private UserRepository userRepository;
  private final EmailService emailService;
  private final PostalService postalService;

  public SaleService(PassService passService,
                     UserRepository userRepository,
                     EmailService emailService,
                     PostalService postalService) {
    this.passService = passService;
    this.userRepository = userRepository;
    this.emailService = emailService;
    this.postalService = postalService;
  }

  public void createSale(SaleDto saleDto) {
    PassCode passCode = passService.createPass(saleDto.passDTO);

    // TODO: Ajouter la logique pour l'envoi selon delivery mode
    String message = String.format("Your pass code is: %s", passCode.toString());

    switch (saleDto.deliveryMode) {
      case EMAIL:
        emailService.send(saleDto.emailAddress, "Pass code", message);
        break;
      case POST:
        postalService.send(userRepository.findById(saleDto.passDTO.userId).getName(), saleDto.postalAddress, message);
        break;
    }
  }
}
