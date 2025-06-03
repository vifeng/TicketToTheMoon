<script setup lang="ts">
import { useHead } from '@vueuse/head'
useHead({
  title: 'HomePage',
  meta: [
    {
      name: 'description',
      content: 'Homepage of Event hub, a platform for event management',
    },
  ],
})
</script>

<template>
  <div>
    <h1>Home Page</h1>
  </div>
</template>
