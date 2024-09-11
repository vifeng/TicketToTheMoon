<template>
  <div class="Customer">
    <h1>Customers</h1>
    <p>
      Sort by id
      <v-btn prepend-icon="mdi-arrow-collapse-up" @click="sorting.sortIdByAscending"
        >Sort by id ascending</v-btn
      >
      <v-btn prepend-icon="mdi-arrow-collapse-down" @click="sorting.sortIdByDescending"
        >Sort by id descending</v-btn
      >
    </p>
    <p>
      Sort by Username
      <v-btn prepend-icon="mdi-arrow-collapse-up" @click="sorting.sortUsernameByAscending"
        >Sort by Username ascending</v-btn
      >
      <v-btn prepend-icon="mdi-arrow-collapse-down" @click="sorting.sortUsernameByDescending"
        >Sort by Username descending</v-btn
      >
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

<style scoped>
.v-btn {
  margin: 0 10px 10px 0;
  background-color: #3fb883;
  color: white;
}
</style>
