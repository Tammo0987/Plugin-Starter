<template>
  <div class="flex-1 overflow-y-auto">
    <category
      v-for="category in categories"
      :key="category.name"
      :dependencies="category.dependencies"
      :name="category.name"
    />
  </div>
</template>

<script>
import { loadDependencies } from '@/service/BackendService';
import Category from './Category.vue';

export default {
  components: {
    Category,
  },
  data() {
    return {
      categories: [],
    };
  },
  created() {
    this.loadCategories();
  },
  methods: {
    async loadCategories() {
      this.categories = await loadDependencies();
    },
  },
};
</script>

<style scoped>
.test {
  max-height: 95%;
}
</style>
