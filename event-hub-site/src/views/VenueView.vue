<template>
  <div class="venues">
    <h1>Venues</h1>
    <div class="venues__list">
      <div class="venue" v-for="venue in venuesStore.venues" :key="venue.id">
        <h2>{{ venue.name }}</h2>
        <p>{{ venue.address }}</p>
        <p>{{ venue.employees }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useVenuesStore } from '@/stores/VenuesStore.js'

const venuesStore = useVenuesStore()
venuesStore.fetchVenues()
</script>
