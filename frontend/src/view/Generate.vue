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
            :selected="plugin.language"
            :options="languages"
            :renderMethod="capitalize"
            v-model="plugin.language"
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
          <div class="flex flex-col my-6">
            <h4>API Version</h4>
            <div class="flex flex-row mr-6">
              <base-drop-down
                label="API Version"
                :options="apiVersions[plugin.api]"
                v-model="plugin.version"
              />
            </div>
          </div>
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
        <dependencies @change="addDependency($event)" />
      </div>
    </div>
    <base-button name="Generate" @click="this.generate" class="mt-6" />
  </div>
</template>

<script>
import BaseButton from '@/components/BaseButton.vue';
import BaseSelection from '@/components/BaseSelection.vue';
import BaseInput from '@/components/BaseInput.vue';
import BaseDropDown from '@/components/BaseDropDown.vue';
import AuthorInput from '@/components/AuthorInput.vue';

import axios from 'axios';
import Dependencies from '@/components/dependency/Dependencies.vue';

export default {
  components: {
    BaseButton,
    BaseSelection,
    BaseDropDown,
    BaseInput,
    AuthorInput,
    Dependencies,
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
        language: 'JAVA',
        repositories: [],
        dependencies: [],
      },
      buildTools: ['MAVEN', 'GRADLE'],
      languages: ['JAVA', 'KOTLIN'],
      apis: ['SPIGOT', 'PAPER', 'SPONGE'],
      apiVersions: {},
      selectedDependencies: [],
    };
  },
  created() {
    this.loadVersions();
  },
  methods: {
    addDependency(dependency) {
      if (dependency.included) {
        this.selectedDependencies.push(dependency);
      } else {
        const index = this.selectedDependencies.findIndex((item) => item.name === dependency.name);
        if (index > -1) {
          this.selectedDependencies.splice(index, 1);
        }
      }
    },
    generate() {
      this.selectedDependencies.forEach((dependency) => {
        this.plugin.dependencies.push(dependency);
      });

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

          this.plugin.dependencies = [];
        });
    },
    async loadVersions() {
      const { data } = await axios.get('http://127.0.0.1/api/versions');
      this.apiVersions = data;
    },
    capitalize(value) {
      return `${value.charAt(0)}${value.slice(1).toLowerCase()}`.replace('_', ' ');
    },
  },
};
</script>
