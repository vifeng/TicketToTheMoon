<template>
  <div id="nav">
    <AppLink id="logo" to="home"
      ><img alt="logo" class="logo" src="../assets/logoEventHub.jpeg"
    /></AppLink>
    <AppLink to="home">Home</AppLink>
    <AppLink to="employees">Employees</AppLink>
    <AppLink to="venues">Venues</AppLink>
    <AppLink to="customers">Customers</AppLink>
    <AppLink to="ticketReservation">ticketReservation</AppLink>
    <div class="dropdown" @mouseover="openDropdown" @mouseleave="closeDropdown">
      <AppLink to="about">About</AppLink>
      <div class="dropdown-content" v-if="isDropdownOpen">
        <AppLink to="apidoc">ApiDoc</AppLink>
        <AppLink to="chatbot">AI chatbot</AppLink>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue'
import { useMouseInElement } from '@vueuse/core'
import AppLink from './AppLink.vue'

const isDropdownOpen = ref(false)

function openDropdown() {
  isDropdownOpen.value = true
}

function closeDropdown() {
  isDropdownOpen.value = false
}
</script>

<style scoped>
#nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  margin-top: 0;
  padding: 8px;
  display: flex;
  align-items: center;
  border-bottom: 5px solid #4e6c71;
  z-index: 2;
  font-weight: bold;
  background: #f0f1f6;
}

#nav a {
  display: inline-block;
  margin-right: 15px;
  color: hsla(160, 100%, 37%, 1);
  transition: 0.2 ease color;
}

#nav a.AppLink-active,
#nav a:hover {
  opacity: 1;
  font-size: x-large;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a {
  color: hsla(160, 100%, 37%, 1);
  padding: 8px 12px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:last-child {
  padding: 8px 12px 25px 12px;
}
.dropdown-content a:hover {
  background-color: #f1f1f1;
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>
