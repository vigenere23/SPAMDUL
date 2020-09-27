package ca.ulaval.glo4003.spamdul.usecases.post;

import ca.ulaval.glo4003.spamdul.entity.user.UserAddress;

public interface PostalService {

  void send(String recipientName, UserAddress recipientAddress, String content);
}
