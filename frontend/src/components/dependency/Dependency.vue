<template>
  <div class="widget flex flex-row items-center justify-between">
    <div>
      {{ dependency.name }}
      <br />
      {{ entry }}
    </div>
    <div>
      <input
        type="checkbox"
        name="included"
        id="included"
        v-model="included"
        @change="change()"
      />
    </div>
  </div>
</template>

<script>
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
      const payload = { ...this.dependency, included: this.included };
      this.$emit('change', payload);
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
  @apply transition-colors duration-300 focus:ring-0;
}
</style>
