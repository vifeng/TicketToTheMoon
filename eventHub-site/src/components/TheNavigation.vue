<template>
  <div id="nav">
    <AppLink id="logo" to="/"
      ><img alt="logo" class="logo" src="../assets/logoEventHub.jpeg"
    /></AppLink>
    <div>Event Hub</div>
    <div>&nbsp;&nbsp;&nbsp;&nbsp;</div>
    <AppLink to="/about">About</AppLink>
    <AppLink to="/employees">Employees</AppLink>
    <AppLink to="/venues">Venues</AppLink>
    <!-- <AppLink :to="{ name: 'protected' }">Dashboard</AppLink> -->
    <!-- this route will send you to the protected page if you login -->
    <AppLink to="https://vueschool.io">Vue School</AppLink>
  </div>
</template>

<script>
import AppLink from './AppLink.vue'
export default {
  components: { AppLink }
}
</script>
