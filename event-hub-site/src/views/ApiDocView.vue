<template>
  <div class="api-doc-view">
    <p>
      The API documentation that serves this website is available at
      <a href="http://localhost:8080/api" target="_blank" rel="noopener noreferrer">
        http://localhost:8080/api </a
      >. Here is a glance below..
    </p>
    <iframe
      src="http://localhost:8080/api"
      frameborder="0"
      style="width: 100%; height: 100vh"
    ></iframe>
  </div>
</template>

<script setup lang="ts"></script>
<style scoped>
.api-doc-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  width: 100%;
}
</style>
