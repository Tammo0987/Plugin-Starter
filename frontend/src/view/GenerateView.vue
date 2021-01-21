<template>
  <div class="flex flex-row flex-wrap flex-1 divide-x-2 max-h-full">
    <div class="flex-1 w-full max-h-full p-2">
      <h2>Metadata</h2>
      <div class="flex flex-col">
        <div class="flex flex-row my-2 flex-1 flex-wrap">
          <base-selection
            name="Build Tool"
            :selected="plugin.buildTool"
            :options="buildTools"
            v-model="plugin.buildTool"
            class="flex-1 w-0"
          />
          <base-selection
            name="Language"
            :selected="plugin.language"
            :options="languages"
            v-model="plugin.language"
            class="flex-1 w-0"
          />
        </div>
        <div class="flex flex-row my-2 flex-1 flex-wrap">
          <base-selection
            name="API"
            :selected="plugin.api"
            :options="apis"
            v-model="plugin.api"
            class="flex-1 w-0"
          />
          <div class="flex-1 w-0">
            <h3>API Version</h3>
            <base-drop-down
              label="API Version"
              :options="apiVersions[plugin.api]"
              v-model="plugin.version"
              class="w-60"
            />
          </div>
        </div>
        <div class="my-2">
          <h3 class="mt-4">Project Metadata</h3>
          <div class="flex flex-row">
            <div class="flex-1 w-0 mr-4">
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

            <author-input class="flex-1 w-0 mr-6" />
          </div>
        </div>
      </div>
    </div>
    <div class="flex-1 w-full max-h-full flex flex-col p-2">
      <h2>Dependencies</h2>
      <dependencies />
    </div>
  </div>
</template>

<script>
import AuthorInput from '@/components/AuthorInput.vue';
import BaseSelection from '@/components/input/BaseSelection.vue';
import BaseDropDown from '@/components/input/BaseDropDown.vue';
import BaseInput from '@/components/input/BaseInput.vue';
import Dependencies from '@/components/dependency/Dependencies.vue';

import { loadVersions } from '@/service/BackendService';
import { SET_PLUGIN } from '@/store/mutations';
import { mapGetters } from 'vuex';

export default {
  components: {
    AuthorInput,
    BaseSelection,
    BaseDropDown,
    BaseInput,
    Dependencies,
  },
  data() {
    return {
      buildTools: ['MAVEN', 'GRADLE'],
      languages: ['JAVA', 'KOTLIN'],
      apis: ['SPIGOT', 'PAPER', 'SPONGE'],
      apiVersions: {},
      selectedDependencies: [],
    };
  },
  computed: {
    ...mapGetters(['plugin']),
  },
  watch: {
    plugin: {
      handler(value) {
        this.$store.commit(SET_PLUGIN, value);
      },
      deep: true,
    },
  },
  async created() {
    this.apiVersions = await loadVersions();
  },
};
</script>
