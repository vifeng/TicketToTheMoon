<template>
  <div>
    <h1>Venues</h1>
    <div class="venues__list">
      <div class="venue" v-for="venue in venues" :key="venue.id">
        <h2>{{ venue.name }}</h2>
        <p>{{ venue.address }}</p>
        <p>{{ venue.employees }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useVenuesStore } from '@/stores/VenuesStore'

const venuesStore = useVenuesStore()
const { venues } = storeToRefs(venuesStore)

onMounted(async () => {
  try {
    await venuesStore.fetchVenues()
  } finally {
  }
})
</script>
