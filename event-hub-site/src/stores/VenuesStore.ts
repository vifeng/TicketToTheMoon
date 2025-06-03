import { defineStore, acceptHMRUpdate } from 'pinia'
import { Configuration } from '@/api'
import { VenueControllerApi } from '@/api/apis'
import type { VenueDTO } from '@/api/models'

const config = new Configuration({
  basePath: import.meta.env.VITE_API_BASE_URL,
})
const api = new VenueControllerApi(config)

export const useVenuesStore = defineStore('VenuesStore', {
  state: () => {
    return {
      venues: [] as VenueDTO[],
      venue: null as VenueDTO | null,
    }
  },
  getters: {},
  actions: {
    async fetchVenues() {
      try {
        this.venues = await api.getAllVenues()
        console.log('Store: fetched venues', this.venues)
      } catch (err) {
        console.error('Store: failed to fetch venues', err)
      }
    },
    async fetchVenueById(id: number): Promise<VenueDTO | null> {
      try {
        const venue = await api.getVenueById({ id })
        this.venue = venue
        return venue
      } catch (error) {
        console.error(`Store: failed to fetch venue with id ${id}`, error)
        this.venue = null
        throw error
      }
    },
  },
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useVenuesStore, import.meta.hot))
}
