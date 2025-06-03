<script setup lang="ts">
import { useHead } from '@vueuse/head'

useHead({
  title: 'Event-Hub',
})
</script>

<template>
  <metainfo />

  <div class="flex min-h-screen flex-col">
    <TheNavigation />

    <main class="flex-1 bg-white p-4 text-black">
      <RouterView />
    </main>

    <TheFooter />
  </div>
</template>

<style lang="css"></style>
