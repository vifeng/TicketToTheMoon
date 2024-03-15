package com.vf.eventhubserver.order;

import java.util.Comparator;
import java.util.TreeSet;

public class TRIsBookedComparator implements Comparator<TicketReservation> {
    TreeSet<TicketReservation> jeu = new TreeSet<>();

    @Override
    public int compare(TicketReservation o1, TicketReservation o2) {
        if (o1.getId().equals(o2.getId())) {
            return Boolean.compare(o2.getIsBooked(), o1.getIsBooked());
        }
        return -1;
    }

}
