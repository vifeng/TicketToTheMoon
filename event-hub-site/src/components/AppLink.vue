<template>
  <!-- external link -->
  <a v-if="isExternal" target="_blank" rel="noopener" class="external-link" :href="to"><slot /></a>
  <!-- internal link -->
  <router-link v-else :to="internalTo" class="internal-link"><slot /></router-link>
</template>

<script>
import { RouterLink } from 'vue-router'

export default {
  props: {
    ...RouterLink.props,
    // 'to' can be a string for route names or an object for named routes with parameters
    to: {
      type: [String, Object],
      required: true
    }
  },
  computed: {
    isExternal() {
      // Check if 'to' is an external link
      return typeof this.to === 'string' && this.to.startsWith('http')
    },
    internalTo() {
      // If 'to' is a string, convert it to an object with 'name'
      // This handles simple named routes
      if (typeof this.to === 'string') {
        return { name: this.to }
      }
      // If 'to' is already an object (e.g., { name: 'profile', params: { username: 'erina' } }),
      // it's returned as is, which supports named routes with parameters
      return this.to
    }
  }
}
</script>
