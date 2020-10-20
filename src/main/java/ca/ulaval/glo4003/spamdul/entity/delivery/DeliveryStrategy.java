package ca.ulaval.glo4003.spamdul.entity.delivery;

public interface DeliveryStrategy {

  void deliver(DeliveryOptions deliveryOptions, String content);
}
