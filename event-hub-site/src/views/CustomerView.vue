<template>
  <div class="Customer">
    <h1>Employees</h1>
    <p>
      Sort by id
      <button @click="sorting.sortIdByAscending">Sort by id ascending</button>
      <button @click="sorting.sortIdByDescending">Sort by id descending</button>
    </p>
    <p>
      Sort by Username
      <button @click="sorting.sortUsernameByAscending">Sort by Username ascending</button>
      <button @click="sorting.sortUsernameByDescending">Sort by Username descending</button>
    </p>
    <div v-if="isLoading">Loading...</div>
    <div v-else class="customers__list">
      <div class="customer" v-for="customer in sortedCustomers" :key="customer.id">
        <p>id : {{ customer.id }}</p>
        <h2>fistname: {{ customer.firstName }}, lastname: {{ customer.lastName }}</h2>
        <p>username: {{ customer.username }}</p>
        <p>mail: {{ customer.email }}</p>
        <p>phoneNumer: {{ customer.phoneNumber }}</p>
        <pre>address:{{ customer.address }}</pre>
        <p>credit card number:{{ customer.creditCardNumber }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useSorting } from '@/composables/Sorting.js'
import { useCustomersStore } from '@/stores/CustomersStore.js'
import { onMounted, ref } from 'vue'

const isLoading = ref(true)
const customersStore = useCustomersStore()
customersStore.fetchCustomers()

const sorting = useSorting([])
onMounted(async () => {
  await customersStore.fetchCustomers()
  sorting.items.value = customersStore.customers
  isLoading.value = false
})

const sortedCustomers = sorting.items
</script>
