<template>
  <div class="employees">
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
    <div v-else class="employees__list">
      <div class="employee" v-for="employee in sortedEmployees" :key="employee.id">
        <p>id : {{ employee.id }}</p>
        <h2>{{ employee.username }}</h2>
        <p>{{ employee.password }}</p>
        <p>{{ employee.email }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useSorting } from '@/composables/Sorting.js'
import { useEmployeesStore } from '@/stores/EmployeesStore.js'
import { onMounted, ref } from 'vue'

const isLoading = ref(true)

const employeesStore = useEmployeesStore()
employeesStore.fetchEmployees()

const sorting = useSorting([])

onMounted(async () => {
  await employeesStore.fetchEmployees()
  sorting.items.value = employeesStore.employees
  isLoading.value = false
})

const sortedEmployees = sorting.items
</script>
