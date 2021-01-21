<template>
  <div>
    <label :for="label" class="block text-sm font-medium text-gray-700">{{
        label
      }}</label>
    <input
      :id="label"
      :name="label"
      :placeholder="label"
      :value="value"
      type="text"
      @input="$emit('input', $event.target.value)"
    />
  </div>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      required: true,
    },
    value: {
      type: String,
    },
  },
  data() {
    return {
      input: '',
    };
  },
};
</script>

<style lang="scss" scoped>
input {
  @apply mb-3 focus:border-indigo-500 border-2
  border-gray-300 block shadow-sm text-base rounded-md w-full;
}

label {
  @apply text-base mb-1;
}
</style>
