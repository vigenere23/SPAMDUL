package ca.ulaval.glo4003.spamdul.entity.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryBridge;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryBridgeFactory;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;

public class PassSender {

    private final UserRepository userRepository;
    private final PassDeliveryOptionsFactory passDeliveryOptionsFactory;
    private final DeliveryBridgeFactory deliveryBridgeFactory;

    public PassSender(UserRepository userRepository,
                      PassDeliveryOptionsFactory passDeliveryOptionsFactory,
                      DeliveryBridgeFactory deliveryBridgeFactory) {
        this.userRepository = userRepository;
        this.passDeliveryOptionsFactory = passDeliveryOptionsFactory;
        this.deliveryBridgeFactory = deliveryBridgeFactory;
    }

    public void sendPass(UserId userId, DeliveryDto deliveryDto, PassCode passCode) {
        DeliveryBridge deliveryBridge = deliveryBridgeFactory.create(deliveryDto.deliveryMode);
        String recipientName = userRepository.findById(userId).getName();
        String CONTENT = "Your pass code is: %s";
        String SUBJECT = "Your new pass code";
        String content = String.format(CONTENT, passCode.toString());
        DeliveryOptions deliveryOptions = passDeliveryOptionsFactory.create(deliveryDto, SUBJECT, recipientName);
        deliveryBridge.send(deliveryOptions, content);
    }
}
