<template>
  <div>
    <h1>Hello World</h1>
    <button class="btn">Test</button>
  </div>
</template>

<script>
export default {
  name: "Home"
};
</script>

<style lang="scss" scoped>
.btn {
  @apply py-2 px-4 font-semibold rounded-lg shadow-md text-white bg-indigo-500 hover:bg-indigo-700;
}
</style>
