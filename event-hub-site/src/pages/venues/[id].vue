<template>
  <section v-if="venue">
    <h2>url : {{ id }}</h2>
    <h1>name : {{ venue.name }}</h1>
    <div class="venue-details">
      <!-- <div class="venue-details__image">
            <img :src="venue.image" :alt="venue.name" />
        </div> -->
      <div class="venue-details__info">
        <h2>{{ venue.name }}</h2>
        <p>{{ venue.address }}</p>
        <p>{{ venue.employees }}</p>
      </div>
    </div>
  </section>
  <div v-else>Loading...</div>
</template>

<script setup lang="ts">
import { useVenuesStore } from '@/stores/VenuesStore'

const venuesStore = useVenuesStore()
const { venue } = storeToRefs(venuesStore)
const route = useRoute() as { params: { id: string } }
const id = Number(route.params.id)

onMounted(async () => {
  try {
    await venuesStore.fetchVenueById(id)
  } finally {
  }
})
</script>
