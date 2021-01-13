package ca.ulaval.glo4003.spamdul.shared.api;

import ca.ulaval.glo4003.spamdul.shared.utils.UriUtil;

public class ApiUrl {

  private final String hostName;
  private final int portNumber;
  private final String prefix;

  public ApiUrl(String hostName, int portNumber, String prefix) {
    this.hostName = hostName;
    this.portNumber = portNumber;
    this.prefix = prefix;
  }

  @Override
  public String toString() {
    return UriUtil.concatenate("http://", hostName + ':' + portNumber, prefix).toString();
  }

  public int getPortNumber() {
    return portNumber;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getHostName() {
    return hostName;
  }
}
