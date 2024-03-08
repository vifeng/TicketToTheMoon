package com.vf.eventhubserver.domain.dto;

public record TarificationDTO(Long id, double basePrice, double taxeRate,
                double discountStudentRate, double discountSeniorRate, double discountChildRate,
                double discountUnemployedRate, EventDTO event) {

}
