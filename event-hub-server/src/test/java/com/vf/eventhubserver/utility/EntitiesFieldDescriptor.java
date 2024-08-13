package com.vf.eventhubserver.utility;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public interface EntitiesFieldDescriptor {

  default FieldDescriptor[] generateFields(
      boolean includeId,
      String entityType,
      ConstraintDescriptions descProvider,
      Map<String, String> customDescriptions,
      String[] fieldNames) {
    List<FieldDescriptor> fields = new ArrayList<>();
    if (includeId) {
      String idDescription =
          customDescriptions.getOrDefault("id", "").isEmpty()
              ? "The " + entityType + " ID. "
              : customDescriptions.get("id");
      fields.add(
          fieldWithPath("id")
              .description(
                  idDescription
                      + collectionToDelimitedString(
                          descProvider.descriptionsForProperty("id"), ". ")));
    }
    for (String fieldName : fieldNames) {
      String fieldDescription =
          customDescriptions.getOrDefault(fieldName, "").isEmpty()
              ? "The " + fieldName + " of the " + entityType + ". "
              : customDescriptions.get(fieldName);
      fields.add(
          fieldWithPath(fieldName)
              .description(
                  fieldDescription
                      + collectionToDelimitedString(
                          descProvider.descriptionsForProperty(fieldName), ". ")));
    }
    return fields.toArray(new FieldDescriptor[0]);
  }
}
