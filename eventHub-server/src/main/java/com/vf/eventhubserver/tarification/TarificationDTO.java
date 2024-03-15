package com.vf.eventhubserver.tarification;

import com.vf.eventhubserver.show.EventDTO;

public record TarificationDTO(Long id, double basePrice, double taxeRate,
        double discountStudentRate, double discountSeniorRate, double discountChildRate,
        double discountUnemployedRate, EventDTO event) {
}
