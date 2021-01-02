<template>
  <div class="container mx-auto flex flex-col items-center">
    <div class="grid grid-cols-2 divide-x-2 pt-4 w-full">
      <div class="flex flex-col m-3">
        <div class="grid grid-cols-2">
          <base-selection
            name="Build Tool"
            :selected="plugin.buildTool"
            :options="buildTools"
            :renderMethod="capitalize"
            v-model="plugin.buildTool"
          />
          <base-selection
            name="Language"
            :selected="language"
            :options="languages"
            :renderMethod="capitalize"
            v-model="language"
          />
        </div>

        <div class="grid grid-cols-2">
          <div class="my-6">
            <base-selection
              name="API"
              :selected="plugin.api"
              :options="apis"
              :renderMethod="capitalize"
              v-model="plugin.api"
            />
          </div>
          <div class="flex justify-center items-center">DROP DOWN</div>
        </div>

        <h4 class="mt-4">Project Metadata</h4>
        <div class="grid grid-cols-2">
          <div class="mr-6">
            <base-input
              label="Name"
              :value="plugin.metadata.name"
              v-model="plugin.metadata.name"
            />

            <base-input
              label="Group"
              :value="plugin.metadata.group"
              v-model="plugin.metadata.group"
            />

            <base-input
              label="Version"
              :value="plugin.metadata.version"
              v-model="plugin.metadata.version"
            />

            <base-input
              label="Description"
              v-model="plugin.metadata.description"
            />
          </div>
          <div class="mr-6">
            <author-input />
          </div>
        </div>
      </div>
      <div class="p-3">
        <h4>Dependencies</h4>
      </div>
    </div>
    <base-button name="Generate" @click="this.generate" class="mt-6" />
  </div>
</template>

<script>
import BaseButton from '@/components/BaseButton.vue';
import BaseSelection from '@/components/BaseSelection.vue';
import BaseInput from '@/components/BaseInput.vue';
import AuthorInput from '@/components/AuthorInput.vue';

import axios from 'axios';

export default {
  components: {
    BaseButton,
    BaseSelection,
    BaseInput,
    AuthorInput,
  },
  data() {
    return {
      plugin: {
        metadata: {
          name: 'demo',
          group: 'com.example',
          version: '1.0-SNAPSHOT',
          authors: [],
          description: 'Example Description',
        },
        buildTool: 'GRADLE',
        api: 'SPIGOT',
        version: '1.8.8-R0.1-SNAPSHOT',
        repositories: [],
        dependencies: [],
      },
      language: 'JAVA',
      buildTools: ['MAVEN', 'GRADLE'],
      languages: ['JAVA', 'KOTLIN'],
      apis: ['SPIGOT', 'PAPER_SPIGOT', 'SPONGE'],
    };
  },
  methods: {
    generate() {
      console.log(this.plugin.metadata.description);

      axios
        .post('http://127.0.0.1/api/generate', this.plugin, {
          responseType: 'blob',
        })
        .then((response) => {
          const url = window.URL.createObjectURL(
            new Blob([response.data], { type: 'application/zip' }),
          );
          const link = document.createElement('a');

          link.href = url;
          link.setAttribute('download', `${this.plugin.metadata.name}.zip`);

          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        });
    },
    // TODO implemented the correct function
    capitalize(value) {
      return `${value.charAt(0)}${value.slice(1).toLowerCase()}`.replace('_', ' ');
    },
  },
};
</script>
