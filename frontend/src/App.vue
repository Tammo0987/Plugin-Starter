<template>
  <div id="app">
    <div class="header">
      <h2>Plugin Starter</h2>
    </div>
    <router-view />
  </div>
</template>

<style lang="scss">
body {
  @apply bg-gray-50;
}

#app {
  font-family: "Quicksand", sans-serif;

  @apply container mx-auto h-full divide-y-2 divide-gray-300;
}

.header {
  @apply p-8 pl-5;
}
</style>
