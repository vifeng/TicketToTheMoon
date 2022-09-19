<script setup>
  import HelloWorld from './components/HelloWorld.vue'
import Employees from './components/Employees.vue'
</script>

<script >
  export default {
      name: 'App'
  }
</script>

<template>
  <header>
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />
    <div class="">
      <HelloWorld msg="Fighting!" />
    </div>
  </header>

  <body>
    <main>
      <div id="app">
        <p>hello</p>
        <Employees />
        <p>bye</p>
      </div>
    </main>
  </body>
</template>

<style >
</style>
