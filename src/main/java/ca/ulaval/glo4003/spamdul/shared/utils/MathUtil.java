package ca.ulaval.glo4003.spamdul.shared.utils;

public class MathUtil {

  public static double round(double number, int decimals) {
    double scale = Math.pow(10, decimals);
    return Math.round(number * scale) / scale;
  }
}
