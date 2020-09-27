package ca.ulaval.glo4003.spamdul.usecases.post;

import ca.ulaval.glo4003.spamdul.entity.post.PostalAddress;

public interface PostalService {

  void send(String recipientName, PostalAddress recipientAddress, String content);
}
