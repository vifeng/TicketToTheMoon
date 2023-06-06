import { defineStore, acceptHMRUpdate } from 'pinia'

export const useVenuesStore = defineStore('VenuesStore', {
  state: () => {
    return {
      venues: []
    }
  },
  getters: {},
  actions: {
    async fetchVenues() {
      const response = await fetch('http://localhost:8080/api/venues')
      const venues = await response.json()
      this.venues = venues
    }
  }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useVenuesStore, import.meta.hot))
}
