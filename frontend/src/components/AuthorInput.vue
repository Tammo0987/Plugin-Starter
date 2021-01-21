<template>
  <div>
    <form @submit.prevent="addAuthor()">
      <base-input label="Authors" v-model="author" />
    </form>

    <div class="max-h-60 overflow-y-auto">
      <div
        class="widget mb-1 flex flex-row justify-between items-center"
        v-for="author in authors"
        :key="author"
      >
        <span>{{ author }}</span>

        <svg width="15px" height="15px" @click="removeAuthor(author)">
          <line x1="0" y1="0" x2="15" y2="15" stroke="gray" stroke-width="2" />
          <line x1="15" y1="0" x2="0" y2="15" stroke="gray" stroke-width="2" />
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
