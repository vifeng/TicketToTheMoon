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
      <HelloWorld msg="You did it! bravo" />
    </div>
  </header>

  <body>
    <main>
      <div id="app">
        <Employees />
      </div>
    </main>
  </body>
</template>

<style >
</style>
