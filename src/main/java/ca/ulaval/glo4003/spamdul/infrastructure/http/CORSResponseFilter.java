package ca.ulaval.glo4003.spamdul.infrastructure.http;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * This class adds headers to the response context to enable the app to send http request to a different domain
 * than the one hosting the site itself.
 * <p>
 * Cross-origin HTTP requests: <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS">https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS</a>
 */
public class CORSResponseFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    MultivaluedMap<String, Object> headers = responseContext.getHeaders();

    headers.add("Access-Control-Allow-Origin", "*");
    headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
  }
}
