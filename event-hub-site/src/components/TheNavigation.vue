<template>
  <nav class="flex flex-wrap items-center justify-between bg-slate-800 p-4 shadow-md">
    <!-- Logo -->
    <SmartLink id="logo" to="home" class="flex items-center">
      <img src="../assets/logoEventHub.jpeg" alt="logo" class="h-[4em]" />
    </SmartLink>

    <!-- Navigation Links -->
    <div class="flex flex-wrap items-center gap-4 font-semibold text-green-700">
      <SmartLink to="/">Home</SmartLink>
      <SmartLink to="employees">Employees</SmartLink>
      <SmartLink to="venues">Venues</SmartLink>
      <SmartLink to="customers">Customers</SmartLink>
      <SmartLink to="ticketReservation">Ticket Reservation</SmartLink>

      <!-- Dropdown -->
      <div class="dropdown" @mouseover="openDropdown" @mouseleave="closeDropdown">
        <SmartLink to="about" class="cursor-pointer">About</SmartLink>
        <div class="dropdown-content mt-1 rounded-md bg-white shadow-lg" v-if="isDropdownOpen">
          <SmartLink to="apidoc" class="block px-4 py-2 text-green-700 hover:bg-gray-100">
            ApiDoc
          </SmartLink>
          <SmartLink to="chatbot" class="block px-4 py-2 text-green-700 hover:bg-gray-100">
            AI Chatbot
          </SmartLink>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
const isDropdownOpen = ref(false)

function openDropdown() {
  isDropdownOpen.value = true
}

function closeDropdown() {
  isDropdownOpen.value = false
}
</script>

<style scoped>
.dropdown-content {
  min-width: 160px;
}
</style>
