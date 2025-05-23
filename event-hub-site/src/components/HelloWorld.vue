<!-- src/components/HelloWorld.vue -->
<template>
  <div>
    <p>{{ msg }}</p>
    <button @click="count++">Clicked {{ count }} times</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

defineProps(['msg'])
const count = ref(0)
</script>
