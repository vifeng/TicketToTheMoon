package com.vf.eventhubserver;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface LocationResponseBuilder<T> {
  /**
   * Return a response with the location of the new resource. It's URL is assumed to be a child of
   * the URL just received.
   *
   * <p>Suppose we have just received an incoming URL of, say, <code>http://localhost:8080/accounts
   * </code> and <code>resourceId</code> is "12345". Then the URL of the new resource will be <code>
   * http://localhost:8080/accounts/12345</code>.
   *
   * @param resourceId Is of the new resource.
   * @return a ResponseEntity with a location header set to the current request URI with a child
   *     identifier appended
   */
  default ResponseEntity<Void> entityWithLocation(Object resourceId) {
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{childId}")
            .buildAndExpand(resourceId)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  default ResponseEntity<Void> entityWithCustomLocation(T resourceId, String resourcePath) {
    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(resourcePath)
            .buildAndExpand(resourceId)
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
