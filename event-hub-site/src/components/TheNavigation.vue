<template>
  <div id="nav">
    <AppLink id="logo" to="/"
      ><img alt="logo" class="logo" src="../assets/logoEventHub.jpeg"
    /></AppLink>
    <AppLink to="/">Home</AppLink>
    <AppLink to="/about">About</AppLink>
    <AppLink to="/employees">Employees</AppLink>
    <AppLink to="/venues">Venues</AppLink>
    <AppLink to="/chatbot">AI chatbot</AppLink>
    <!-- <AppLink :to="{ name: 'protected' }">Dashboard</AppLink> -->
    <!-- this route will send you to the protected page if you login -->
  </div>
</template>

<script>
import AppLink from './AppLink.vue'
export default {
  components: { AppLink }
}
</script>
