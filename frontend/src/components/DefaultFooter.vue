<template>
  <footer>
    <base-button name="Generate" @click="generatePlugin()" />
  </footer>
</template>

<script>
import DownloadService from '@/service/DownloadService';
import { generate } from '@/service/BackendService';
import { mapGetters } from 'vuex';

import BaseButton from '@/components/input/BaseButton.vue';

export default {
  components: {
    BaseButton,
  },
  computed: {
    ...mapGetters(['plugin']),
  },
  methods: {
    generatePlugin() {
      const zipName = `${this.plugin.metadata.name}.zip`;

      generate(this.plugin).then((response) => {
        DownloadService.download(response.data, zipName);
      });
    },
  },
};
</script>

<style scoped>
footer {
  @apply flex justify-center;
}

button {
  @apply my-6;
}
</style>
