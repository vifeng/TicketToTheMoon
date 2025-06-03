import { acceptHMRUpdate, defineStore } from 'pinia'
import { Configuration } from '@/api'
import { CustomerControllerApi } from '@/api/apis'
import type { CustomerDTO } from '@/api/models'

const config = new Configuration({
  basePath: import.meta.env.VITE_API_BASE_URL,
})
const api = new CustomerControllerApi(config)

export const useCustomersStore = defineStore('CustomersStore', {
  state: () => {
    return {
      customers: [] as CustomerDTO[],
    }
  },
  getters: {},
  actions: {
    async fetchCustomers() {
      try {
        this.customers = await api.getAllCustomers()
      } catch (err) {
        console.error('Store: failed to fetch customers', err)
      }
    },
  },
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useCustomersStore, import.meta.hot))
}
