import { acceptHMRUpdate, defineStore } from 'pinia'

export const useEmployeesStore = defineStore('EmployeesStore', {
  state: () => {
    return {
      employees: []
    }
  },
  getters: {},
  actions: {
    async fetchEmployees() {
      const response = await fetch('http://localhost:8080/api/employees')
      const employees = await response.json()
      this.employees = employees
    }
  },
  mutations: {}
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useEmployeesStore, import.meta.hot))
}
