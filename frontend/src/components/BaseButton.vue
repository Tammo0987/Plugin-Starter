<template>
  <button @click="this.click">{{ name }}</button>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      required: true,
    },
  },
  methods: {
    click(event) {
      this.$emit('click', event);
    },
  },
};
</script>

<style lang="scss" scoped>
button {
  @apply whitespace-nowrap inline-flex items-center justify-center
    px-8 py-2
    border border-transparent rounded-md shadow-sm
    text-xl font-medium
    text-white bg-indigo-600 hover:bg-indigo-700;
}
</style>
