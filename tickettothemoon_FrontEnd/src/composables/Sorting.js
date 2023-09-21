// Importez la fonction `ref` de Vue
import { ref } from 'vue'

export function useSorting() {
  // référence réactive pour stocker la liste d'éléments
  const items = ref([])

  function sortByAscending() {
    items.value.sort((a, b) => a - b)
  }

  function sortByDescending() {
    items.value.sort((a, b) => b - a)
  }

  // Retournez les fonctions et la liste pour les utiliser dans le composant
  return {
    items,
    sortByAscending,
    sortByDescending
  }
}
