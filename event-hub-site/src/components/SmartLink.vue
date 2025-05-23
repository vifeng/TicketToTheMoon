<script lang="ts" setup>
import { RouterLink } from 'vue-router'

interface Props {
  to: string
  class?: string
}

const props = defineProps<Props>()

const isExternal = computed(() => /^(https?:)?\/\//.test(props.to))

const baseInternalClass = 'text-blue-600 hover:underline'
const baseExternalClass = 'text-green-600 hover:underline cursor-pointer'
</script>

<template>
  <component
    :is="isExternal ? 'a' : RouterLink"
    :to="!isExternal ? props.to : undefined"
    :href="isExternal ? props.to : undefined"
    :target="isExternal ? '_blank' : undefined"
    :rel="isExternal ? 'noopener noreferrer' : undefined"
    :class="[isExternal ? baseExternalClass : baseInternalClass, props.class]"
  >
  <template v-if="isExternal">
      <span class="icon-[lucide--globe] inline-block mr-1" data-testid="external-icon"></span>
    </template>
    <slot />
  </component>
</template>

