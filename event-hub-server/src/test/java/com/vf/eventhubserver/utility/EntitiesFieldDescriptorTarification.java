package com.vf.eventhubserver.utility;

import com.vf.eventhubserver.tarification.CategoryTariff;
import com.vf.eventhubserver.tarification.Tarification;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptorTarification implements EntitiesFieldDescriptor {

  ConstraintDescriptions descTarification = new ConstraintDescriptions(Tarification.class);
  ConstraintDescriptions descCategoryTariff = new ConstraintDescriptions(CategoryTariff.class);

  public FieldDescriptor[] generateTarificationFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "basePrice", "Base price for the ticket to which discounts can be applied. ",
            "taxeRate", "The tax rate to be applied to the base price. ",
            "discountStudentRate", "The discount rate for students. ",
            "discountSeniorRate", "The discount rate for seniors. ",
            "discountChildRate", "The discount rate for children. ",
            "discountUnemployedRate", "The discount rate for unemployed. ");
    return generateFields(
        includeId,
        "tarification",
        descTarification,
        customDescriptions,
        new String[] {
          "basePrice",
          "taxeRate",
          "discountStudentRate",
          "discountSeniorRate",
          "discountChildRate",
          "discountUnemployedRate"
        });
  }

  public FieldDescriptor[] generateCategoryTariffFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "name", "The name of the category. Student, Senior, Child, Unemployed. ",
            "tarification", "The tarification for the category. ");
    return generateFields(
        includeId,
        "categoryTariff",
        descCategoryTariff,
        customDescriptions,
        new String[] {"name", "tarification"});
  }
}
