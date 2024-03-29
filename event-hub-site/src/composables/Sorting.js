// Importez la fonction `ref` de Vue
import { ref } from 'vue'

export function useSorting(initialItems) {
  const items = ref(initialItems)

  function sortIdByAscending() {
    items.value.sort((a, b) => a.id - b.id)
  }

  function sortIdByDescending() {
    items.value.sort((a, b) => b.id - a.id)
  }

  function sortUsernameByAscending() {
    items.value.sort((a, b) => a.username.localeCompare(b.username))
  }

  function sortUsernameByDescending() {
    items.value.sort((a, b) => b.username.localeCompare(a.username))
  }

  function sortNameByAscending() {
    items.value.sort((a, b) => a.name.localeCompare(b.name))
  }

  function sortNameByDescending() {
    items.value.sort((a, b) => b.name.localeCompare(a.name))
  }

  return {
    items,
    sortIdByAscending,
    sortIdByDescending,
    sortUsernameByAscending,
    sortUsernameByDescending,
    sortNameByAscending,
    sortNameByDescending
  }
}
