<template>
  <div>
    <h1>Customers</h1>
    <h2>List of Customers</h2>

    <div v-if="isLoading">Loading...</div>
    <div v-else-if="customers.length === 0">No customers found.</div>
    <div v-else class="customer-list">
      <div v-for="customer in customers" :key="customer.id" class="customer-card">
        <p><strong>ID:</strong> {{ customer.id }}</p>
        <p><strong>Name:</strong> {{ customer.firstName }} {{ customer.lastName }}</p>
        <p><strong>Username:</strong> {{ customer.username }}</p>
        <p><strong>Email:</strong> {{ customer.email }}</p>
        <p><strong>Phone Number:</strong> {{ customer.phoneNumber }}</p>
        <p><strong>Address:</strong></p>
        <pre class="address">{{ customer.address }}</pre>
        <p><strong>Credit Card:</strong> {{ customer.creditCardNumber }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useCustomersStore } from '@/stores/CustomersStore'
import { useHead } from '@vueuse/head'

useHead({
  title: 'Customers',
})
const isLoading = ref(false)
const customersStore = useCustomersStore()
const { customers } = storeToRefs(customersStore)

onMounted(async () => {
  try {
    isLoading.value = true
    await customersStore.fetchCustomers()
  } finally {
    isLoading.value = false
  }
})
</script>

<style scoped></style>
