package com.vf.eventhubserver;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Controller
@RequestMapping("/api")
public class DocumentationController extends ResourceHttpRequestHandler {

  private static final String API_GUIDE_PATH = "build/docs/asciidoc/api-guide.html";

  @GetMapping
  public ResponseEntity<Resource> getDocumentation() {
    Resource resource = new FileSystemResource(API_GUIDE_PATH);
    if (resource.exists() && resource.isReadable()) {
      return ResponseEntity.ok().body(resource);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
