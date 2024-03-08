package com.vf.eventhubserver.domain.model;

import java.util.Comparator;
import java.util.TreeSet;

public class TRIsBookedComparator implements Comparator<TicketReservation> {
    TreeSet<TicketReservation> jeu = new TreeSet<TicketReservation>();

    @Override
    public int compare(TicketReservation o1, TicketReservation o2) {
        // compare si les deux id sont égales
        // si oui on compare les deux isBooked, sinon renvoie -1
        if (o1.getId().equals(o2.getId())) {
            // return Boolean.compare(o1.getIsBooked(), o2.getIsBooked());
            // sorted by true first false last
            return Boolean.compare(o2.getIsBooked(), o1.getIsBooked());
            // sorted by false first true last
        }
        return -1;
    }

}
