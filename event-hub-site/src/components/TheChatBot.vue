<template>
  <div class="container">
    <div class="video-container">
      <video width="600" controls class="video">
        <source :src="videoUrl" type="video/mp4" />
        Your browser does not support the video tag.
      </video>
    </div>
    <div class="text-container">
      <h2>AI Chatbot</h2>
      <h3>(on your right bottom end-side)</h3>
      <p>
        I created this chatbot using Dialogflow, a service on Google Cloud Platform, to help
        recruiters hire me (Virginie Fengarol). It's trained on my info : CV, skills, and hobbies.
        With its AI features, the chatbot understands questions about my background and dynamically
        generates detailed insights, facilitating recruiters' decision-making process efficiently
        and comprehensively. It also provides concise responses on my professional experience,
        education, skills, and hobbies. Feel free to ask any questions!
      </p>
      <p>
        It's still in development, so please use caution with the provided information and verify it
        against my CV for accuracy.
      </p>
      <p>
        <strong
          >This chatbot needs credentials, and might not work. In the meantime, have a look a the
          demo video.</strong
        >
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const GCP_PROJECT_ID = import.meta.env.VITE_GCP_PROJECT_ID
const CHATBOT_AGENT_ID = import.meta.env.VITE_CHATBOT_AGENT_ID
const videoUrl = ref('')

onMounted(() => {
  videoUrl.value = 'http://localhost:8080/api/chatbot'
  const script = document.createElement('script')
  script.src =
    'https://www.gstatic.com/dialogflow-console/fast/df-messenger/prod/v1/df-messenger.js'
  script.async = true
  script.onload = () => {
    // df-messenger.js has loaded, initialize the chatbot
    initializeChatBot()
  }
  document.body.appendChild(script)
})

function initializeChatBot() {
  const dfMessenger = document.createElement('df-messenger')
  dfMessenger.setAttribute('location', 'europe-west1')
  dfMessenger.setAttribute('project-id', GCP_PROJECT_ID)
  dfMessenger.setAttribute('agent-id', CHATBOT_AGENT_ID)
  dfMessenger.setAttribute('language-code', 'en')
  dfMessenger.setAttribute('max-query-length', '-1')

  
  const chatBubble = document.createElement('df-messenger-chat-bubble')
  chatBubble.setAttribute('chat-title', 'AI Chatbot for recruiter')

  dfMessenger.appendChild(chatBubble)

  const style = document.createElement('style')
  style.textContent = `
    df-messenger {
      z-index: 999;
      position: fixed;
      bottom: 16px;
      right: 16px;
    }
  `
  document.head.appendChild(style)

  document.body.appendChild(dfMessenger)
}
</script>

<style scoped>
.container {
  display: flex;
  align-items: flex-start; 
  gap: 20px; 
}

.video-container {
  flex: 1; 
}
.video {
  border: 4px solid #0957D0; /* Border around the video */
  border-radius: 8px; /* Rounded corners */
  box-shadow: -8px 8px 8px rgba(0, 0, 0, 0.1); /* Bottom-left shadow effect */
  width: 60%; /* Ensure video takes full width of container */
}
.text-container {
  flex: 1; 
}
</style>
