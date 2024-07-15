import { acceptHMRUpdate, defineStore } from 'pinia'

export const useTicketReservationsStore = defineStore('TicketReservationsStore', {
  state: () => {
    return {
      ticketReservations: []
    }
  },
  getters: {},
  actions: {
    async fetchTicketReservations() {
      const response = await fetch('http://localhost:8080/api/ticketReservation')
      const ticketReservations = await response.json()
      this.ticketReservations = ticketReservations
    }
  },
  mutations: {}
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useTicketReservationsStore, import.meta.hot))
}
