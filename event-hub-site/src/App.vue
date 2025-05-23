<script setup lang="ts">
import { useHead } from '@vueuse/head'

useHead({
  title: 'Event-Hub',
})
</script>

<template>
    <metainfo></metainfo>

  <TheNavigation></TheNavigation>
  <div class="container">
    <div>
      <router-view />
    </div>
  </div>
  <TheFooter></TheFooter>
</template>

<style lang="css"></style>
