<template>
    <div class="employees">
        <h1>Employees</h1>
        <p>
            Trier par id
            <button @click="sorting.sortIdByAscending">Trier les id Ascendant</button>
            <button @click="sorting.sortIdByDescending">Trier les id Descendant</button>
        </p>
        <p>
            Trier par Username
            <button @click="sorting.sortUsernameByAscending">Trier les Username Ascendant</button>
            <button @click="sorting.sortUsernameByDescending">Trier les Username Descendant</button>
        </p>
        <div v-if="isLoading">Chargement en cours...</div>
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

<script setup>
import { useSorting } from '@/composables/Sorting.js';
import { useEmployeesStore } from '@/stores/EmployeesStore.js';
import { onMounted, ref } from 'vue';

const isLoading = ref(true)

const employeesStore = useEmployeesStore()
employeesStore.fetchEmployees()

const sorting = useSorting([])

onMounted(async () => {
    await employeesStore.fetchEmployees()
    sorting.items.value = employeesStore.employees // Mettez à jour les données de tri une fois que les employés sont chargés
    isLoading.value = false // Une fois que les données sont chargées, masquez l'indicateur de chargement
})

const sortedEmployees = sorting.items
</script>
