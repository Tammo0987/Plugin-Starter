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
          type="checkbox"
          :name="dependency.name"
          :id="dependency.name"
          v-model="included"
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
  },
  data() {
    return {
      included: false,
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
    m-2;
}

input {
  @apply transition-colors duration-300 text-indigo-600 border-gray-400 focus:ring-0;
}

label {
  @apply cursor-pointer;
}
</style>
