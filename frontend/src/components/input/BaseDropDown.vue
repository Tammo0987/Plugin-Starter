<template>
  <div>
    <select
      :id="label"
      :name="label"
      @input="$emit('input', $event.target.value)"
    >
      <option v-for="option in options" :key="option" :value="option">
        {{ option }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      required: true,
    },
    options: {
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data() {
    return {
      selected: this.options[0] || '',
    };
  },
  updated() {
    this.selected = this.options[0] || '';
    this.$emit('input', this.selected);
  },
};
</script>

<style lang="scss" scoped>
label {
  @apply text-base mb-1;
}

select {
  @apply rounded text-base border-gray-300 border-2 h-10 w-60 shadow-sm mt-2
    dark:bg-gray-700 dark:border-gray-400;
}
</style>
