<template>
  <label :for="dependency.name">
    <div class="widget flex flex-row items-center justify-between">
      <div>
        {{ dependency.name }}
        <br />
        {{ entry }}
      </div>
      <div>
        <input
          :id="dependency.name"
          v-model="included"
          :name="dependency.name"
          type="checkbox"
          @change="change()"
        />
      </div>
    </div>
  </label>
</template>

<script>
import { ADD_DEPENDENCY, REMOVE_DEPENDENCY } from '@/store/mutations';

export default {
  props: {
    dependency: {
      type: Object,
      required: true,
    },
    preIncluded: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      included: this.preIncluded,
    };
  },
  computed: {
    entry() {
      return `${this.dependency.group}:${this.dependency.artifact}:${this.dependency.version}`;
    },
  },
  methods: {
    change() {
      if (this.included) {
        this.$store.commit(ADD_DEPENDENCY, this.dependency);
      } else {
        this.$store.commit(REMOVE_DEPENDENCY, this.dependency);
      }
    },
  },
};
</script>

<style scoped>
.widget {
  @apply rounded p-2 bg-white border border-gray-200 text-sm font-light text-gray-600
    m-2 dark:bg-gray-700 dark:border-gray-400 dark:text-gray-200;
}

input {
  @apply transition-colors duration-300 text-indigo-600 border-gray-400 focus:ring-0
    dark:bg-gray-700 dark:border-gray-400 dark:focus:ring-offset-transparent;
}

label {
  @apply cursor-pointer;
}
</style>
