<template>
  <div>
    <form @submit.prevent="addAuthor()">
      <base-input v-model="author" label="Authors"/>
    </form>

    <div class="max-h-60 overflow-y-auto">
      <div
        v-for="author in authors"
        :key="author"
        class="widget mb-1 flex flex-row justify-between items-center"
      >
        <span>{{ author }}</span>

        <svg height="15px" width="15px" @click="removeAuthor(author)">
          <line stroke="gray" stroke-width="2" x1="0" x2="15" y1="0" y2="15"/>
          <line stroke="gray" stroke-width="2" x1="15" x2="0" y1="0" y2="15"/>
        </svg>
      </div>
    </div>
  </div>
</template>

<script>
import { ADD_AUTHOR, REMOVE_AUTHOR } from '@/store/mutations';
import BaseInput from './input/BaseInput.vue';

export default {
  components: { BaseInput },
  data() {
    return {
      author: '',
    };
  },
  computed: {
    authors: {
      get() {
        return this.$store.getters.plugin.metadata.authors;
      },
    },
  },
  methods: {
    addAuthor() {
      if (!this.authors.includes(this.author) && this.author.trim() !== '') {
        this.$store.commit(ADD_AUTHOR, this.author);
      }

      this.author = '';
    },
    removeAuthor(author) {
      this.$store.commit(REMOVE_AUTHOR, author);
    },
  },
};
</script>

<style lang="scss" scoped>
.widget {
  @apply rounded p-2 bg-white border border-gray-200 text-sm uppercase font-light text-gray-500;
}

svg:hover {
  cursor: pointer;
}
</style>
