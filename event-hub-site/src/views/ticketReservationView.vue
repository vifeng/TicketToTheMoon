<template>
    <div class="ticketReservation">
        <h1>Ticket Reservation</h1>
        <div class="ticketReservation__list">
            <div >Number of reservations: {{ ticketReservationsStore.ticketReservations.length }}</div>
        <div class="ticketReservation" v-for="ticketReservation in ticketReservationsStore.ticketReservations" :key="ticketReservation.id">
            <p>Seat details: <pre>{{ ticketReservation.ticketReservationKey.seatId }}</pre></p>
            <p>Session event details: <pre>{{ ticketReservation.ticketReservationKey.sessionEventId }}</pre></p>
            <hr>
        </div>
        </div>
    </div>
</template>

<script setup>
import { useTicketReservationsStore } from '@/stores/TicketReservationsStore.js';
const ticketReservationsStore = useTicketReservationsStore()
ticketReservationsStore.fetchTicketReservations()
</script>