<template>
  <section class="venue">
    <h2>url : {{ $route.params.id }}</h2>
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
    </div>
  </section>
</template>

<script setup>
import { useVenuesStore } from '@/stores/VenuesStore.js'

const venuesStore = useVenuesStore()

computed: {
      venueId() {
        return parseInt(this.$route.params.id);
      },
      venue() {
        venuesStore.fetchVenuesById(this.venueId)
      }
    }
</script>
