<template>
  <div>
    <div class="widget">
      <h5 class="text-xl">{{ name }}</h5>
    </div>
    <div>
      <dependency
        v-for="dependency in dependencies"
        :key="dependency.name"
        :dependency="dependency"
        :preIncluded="dependencyIsPreIncluded(dependency)"
      />
    </div>
  </div>
</template>

<script>
import Dependency from './Dependency.vue';

export default {
  props: {
    name: {
      type: String,
      required: true,
    },
    dependencies: {
      required: false,
      default: () => [],
    },
  },
  components: {
    Dependency,
  },
  methods: {
    dependencyIsPreIncluded(dependency) {
      return this.$store.getters.plugin.dependencies.some(
        (element) => element.name === dependency.name,
      );
    },
  },
};
</script>

<style scoped>
.widget {
  @apply rounded p-2 bg-white border border-gray-200 text-sm font-light text-gray-600
    m-2 dark:bg-gray-800 dark:border-gray-400 dark:text-gray-200;
}
</style>
