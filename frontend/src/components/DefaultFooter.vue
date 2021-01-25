<template>
  <footer>
    <base-button name="Copy Configuration URL" class="invisible"></base-button>
    <base-button name="Generate" @click="generatePlugin()" />
    <base-button
      name="Copy Configuration URL"
      @click="copyToClipboard('Test')"
    />
  </footer>
</template>

<script>
import DownloadService from '@/service/download.service';
import TemplateService from '@/service/template.service';
import { generate } from '@/service/backend.service';
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
    copyToClipboard() {
      const element = document.createElement('textarea');
      element.value = TemplateService.generateURL();
      document.body.appendChild(element);
      element.select();
      document.execCommand('copy');
      document.body.removeChild(element);
    },
  },
};
</script>

<style scoped>
footer {
  @apply flex justify-between;
}

button {
  @apply my-6;
}
</style>
