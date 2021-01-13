<template>
  <div class="w-full">
    <label :for="label">{{ label }}</label>

    <select
      :name="label"
      :id="label"
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
};
</script>

<style lang="scss" scoped>
label {
  @apply text-base mb-1;
}

select {
  @apply h-10 rounded text-base border-gray-300 border-2 w-full shadow-sm;
}
</style>
