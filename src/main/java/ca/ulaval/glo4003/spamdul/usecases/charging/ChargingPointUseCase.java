package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.charging.EnoughCreditForChargingVerifier;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.usecases.charging.assembler.ChargingPointDtoAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.ChargingPointDto;
import java.util.ArrayList;
import java.util.List;

public class ChargingPointUseCase {

  private final ChargingPointRepository chargingPointRepository;
  private final EnoughCreditForChargingVerifier enoughCreditForChargingVerifier;
  private final ChargingPaymentService chargingPaymentService;
  private final ChargingPointDtoAssembler chargingPointDtoAssembler;

  public ChargingPointUseCase(ChargingPointRepository chargingPointRepository,
                              EnoughCreditForChargingVerifier enoughCreditForChargingVerifier,
                              ChargingPaymentService chargingPaymentService,
                              ChargingPointDtoAssembler chargingPointDtoAssembler) {
    this.chargingPointRepository = chargingPointRepository;
    this.enoughCreditForChargingVerifier = enoughCreditForChargingVerifier;
    this.chargingPaymentService = chargingPaymentService;
    this.chargingPointDtoAssembler = chargingPointDtoAssembler;
  }


  public List<ChargingPointDto> getAllChargingPoints() {
    List<ChargingPointDto> dtos = new ArrayList<>();

    for (ChargingPoint chargingPoint : chargingPointRepository.findAll()) {
      dtos.add(chargingPointDtoAssembler.toDto(chargingPoint));
    }

    return dtos;
  }

  public ChargingPointDto getChargingPoint(ChargingPointId chargingPointId) {
    return chargingPointDtoAssembler.toDto(chargingPointRepository.findBy(chargingPointId));
  }

  public ChargingPointDto activateChargingPoint(ChargingPointId chargingPointId, RechargULCardId rechargULCardId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.activate(enoughCreditForChargingVerifier, rechargULCardId);
    chargingPointRepository.update(chargingPoint);

    return chargingPointDtoAssembler.toDto(chargingPoint);
  }

  public ChargingPointDto startRecharging(ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.connect();
    chargingPointRepository.update(chargingPoint);

    return chargingPointDtoAssembler.toDto(chargingPoint);
  }

  public ChargingPointDto stopRecharging(ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.disconnect();
    chargingPointRepository.update(chargingPoint);

    return chargingPointDtoAssembler.toDto(chargingPoint);
  }

  public ChargingPointDto deactivateChargingPoint(ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.deactivateAndPay(chargingPaymentService);
    chargingPointRepository.update(chargingPoint);

    return chargingPointDtoAssembler.toDto(chargingPoint);
  }
}
