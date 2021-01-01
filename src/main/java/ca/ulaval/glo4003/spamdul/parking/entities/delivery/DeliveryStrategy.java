package ca.ulaval.glo4003.spamdul.parking.entities.delivery;

public interface DeliveryStrategy {

  void deliver(DeliveryOptions deliveryOptions, String content);
}
