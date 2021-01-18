<template>
  <div>
    <category
      v-for="category in categories"
      :key="category.name"
      :name="category.name"
      :dependencies="category.dependencies"
      @change="$emit('change', $event)"
    />
  </div>
</template>

<script>
import axios from 'axios';
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
      const { data } = await axios.get('http://127.0.0.1/api/dependencies');
      this.categories = data.categories;
    },
  },
};
</script>
