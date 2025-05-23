<template>
  <div id="nav">
    <SmartLink id="logo" to="home"
      ><img alt="logo" class="logo" src="../assets/logoEventHub.jpeg"
    /></SmartLink>
    <SmartLink to="home">Home</SmartLink>
    <SmartLink to="employees">Employees</SmartLink>
    <SmartLink to="venues">Venues</SmartLink>
    <SmartLink to="customers">Customers</SmartLink>
    <SmartLink to="ticketReservation">ticketReservation</SmartLink>
    <div class="dropdown" @mouseover="openDropdown" @mouseleave="closeDropdown">
      <SmartLink to="about">About</SmartLink>
      <div class="dropdown-content" v-if="isDropdownOpen">
        <SmartLink to="apidoc">ApiDoc</SmartLink>
        <SmartLink to="chatbot">AI chatbot</SmartLink>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { useMouseInElement } from '@vueuse/core'

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
