<template>
    <div class="employees">
        <h1>Employees</h1>
        <div class="employees__list">
            <div class="employee" v-for="employee in employeesStore.employees" :key="employee.id">
                <h2>{{ employee.username }}</h2>
                <p>{{ employee.password }}</p>
                <p>{{ employee.email }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useEmployeesStore } from '@/stores/EmployeesStore.js';

const employeesStore = useEmployeesStore()
employeesStore.fetchEmployees()
</script>
