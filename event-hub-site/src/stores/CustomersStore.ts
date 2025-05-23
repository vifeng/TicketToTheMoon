import { acceptHMRUpdate, defineStore } from 'pinia'

export const useCustomersStore = defineStore('CustomersStore', {
  state: () => {
    return {
      customers: []
    }
  },
  getters: {},
  actions: {
    async fetchCustomers() {
      const response = await fetch('http://localhost:8080/api/customers')
      const customers = await response.json()
      this.customers = customers
    }
  },
  mutations: {}
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useCustomersStore, import.meta.hot))
}
