<template>
  <div class="mr-6">
    <h4>{{ name }}</h4>
    <div class="flex mt-2">
      <div v-for="value in options" :key="value" class="flex items-center">
        <input
          type="radio"
          :value="value"
          :id="value"
          :checked="selected === value"
          @input="$emit('input', $event.target.value)"
        />
        <label :for="value" v-if="renderMethod">{{
          renderMethod(value)
        }}</label>
        <label :for="value" v-else>{{ value }}</label>
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
    renderMethod: {
      type: Function,
      required: false,
    },
  },
};
</script>

<style lang="scss" scoped>
input {
  @apply focus:ring-transparent h-4 w-4 text-indigo-600 border-gray-300
    transition-colors duration-300;
}

label {
  @apply ml-2 mr-5 text-lg;
}
</style>
