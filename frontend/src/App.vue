<template>
  <div id="app">
    <navbar/>
    <main>
      <router-view/>
    </main>
    <default-footer/>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import DefaultFooter from '@/components/DefaultFooter.vue';

export default {
  components: {
    Navbar,
    DefaultFooter,
  },
};
</script>

<style lang="css">
body {
  @apply bg-gray-50;
}

#app {
  font-family: "Quicksand", sans-serif;

  @apply container mx-auto flex flex-col h-screen divide-gray-300 divide-y-2;
}

main {
  @apply flex flex-1 pt-2 mb-2 overflow-hidden;
}
</style>
