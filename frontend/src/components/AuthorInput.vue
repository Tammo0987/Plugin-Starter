<template>
  <div>
    <form @submit.prevent="addAuthor()">
      <base-input label="Author" v-model="author" />
    </form>
  </div>
</template>

<script>
import BaseInput from './BaseInput.vue';

export default {
  components: { BaseInput },
  data() {
    return {
      author: '',
      authors: [],
    };
  },
  methods: {
    addAuthor() {
      console.log(this.author);
    },
  },
};
</script>
