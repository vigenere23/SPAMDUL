package ca.ulaval.glo4003.spamdul.entity.delivery;

public interface DeliveryBridge {
    void send(DeliveryOptions deliveryOptions, String content);
}
