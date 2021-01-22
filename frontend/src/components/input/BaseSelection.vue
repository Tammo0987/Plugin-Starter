<template>
  <div class="w-72">
    <h3>{{ name }}</h3>
    <div class="flex mt-2">
      <div v-for="value in options" :key="value" class="flex items-center">
        <input
          :id="value"
          :checked="selected === value"
          :value="value"
          type="radio"
          @input="$emit('input', $event.target.value)"
        />
        <label :for="value">{{ capitalize(value) }}</label>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      required: true,
    },
    selected: {
      type: String,
      required: true,
    },
    options: {
      type: Array,
      required: true,
    },
  },
  methods: {
    capitalize(value) {
      return `${value.charAt(0)}${value.slice(1).toLowerCase()}`.replace('_', ' ');
    },
  },
};
</script>

<style lang="scss" scoped>
input {
  @apply focus:ring-transparent h-4 w-4 text-indigo-600 border-gray-300
    transition-colors duration-300 dark:bg-gray-700 dark:border-gray-400
    dark:focus:ring-offset-transparent;
}

label {
  @apply ml-2 mr-5 text-lg;
}
</style>
