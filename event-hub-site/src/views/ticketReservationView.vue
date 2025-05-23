<template>
  <div class="ticketReservation">
    <h1>Ticket Reservation</h1>
    <div class="ticketReservation__list">
      <div>Number of reservations: {{ ticketReservationsStore.ticketReservations.length }}</div>
      <div
        class="ticketReservation"
        v-for="ticketReservation in ticketReservationsStore.ticketReservations"
        :key="ticketReservation.id"
      >
        <pre>Seat details: {{ ticketReservation.ticketReservationKey.seatId }}</pre>
        <pre>
Session event details: {{ ticketReservation.ticketReservationKey.sessionEventId }}</pre
        >
        <hr />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useTicketReservationsStore } from '@/stores/ticketReservationsStore.js'
const ticketReservationsStore = useTicketReservationsStore()
ticketReservationsStore.fetchTicketReservations()
</script>
