<template>
  <div class="col-full text-center">
    <h1>404</h1>
    <h2>Not Found</h2>
    <!-- <router-link :to="{ name: 'Home' }">Read some cool threads</router-link> -->
    <AppLink to="/">Go back to home</AppLink>
  </div>
</template>
<script setup>
import AppLink from '../components/AppLink.vue'
</script>
<style scoped>
h1 {
  font-size: 10rem;
  margin: 0;
}
h2 {
  font-size: 2rem;
  margin: 0;
}
</style>
