<template>
  <div class="flex flex-row flex-wrap flex-1 divide-x-2 max-h-full">
    <div class="flex-1 w-full max-h-full p-2">
      <h2>Metadata</h2>
      <div class="flex flex-col">
        <div class="flex flex-row my-2 flex-1 flex-wrap">
          <base-selection
            v-model="plugin.buildTool"
            :options="buildTools"
            :selected="plugin.buildTool"
            class="flex-1 w-0"
            name="Build Tool"
          />
          <base-selection
            v-model="plugin.language"
            :options="languages"
            :selected="plugin.language"
            class="flex-1 w-0"
            name="Language"
          />
        </div>
        <div class="flex flex-row my-2 flex-1 flex-wrap">
          <base-selection
            v-model="plugin.api"
            :options="apis"
            :selected="plugin.api"
            class="flex-1 w-0"
            name="API"
          />
          <div class="flex-1 w-0">
            <h3>API Version</h3>
            <base-drop-down
              v-model="plugin.version"
              :options="apiVersions[plugin.api]"
              :defaultSelection="plugin.version"
              class="w-60"
              label="API Version"
            />
          </div>
        </div>
        <div class="my-2">
          <h3 class="mt-4">Project Metadata</h3>
          <div class="flex flex-row">
            <div class="flex-1 w-0 mr-4">
              <base-input
                v-model="plugin.metadata.name"
                :value="plugin.metadata.name"
                label="Name"
              />

              <base-input
                v-model="plugin.metadata.group"
                :value="plugin.metadata.group"
                label="Group"
              />

              <base-input
                v-model="plugin.metadata.version"
                :value="plugin.metadata.version"
                label="Version"
              />

              <base-input
                v-model="plugin.metadata.description"
                label="Description"
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

import { loadVersions, setPluginParameterFromURL } from '@/service/backend.service';
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
      apiVersions: {},
      selectedDependencies: [],
    };
  },
  computed: {
    ...mapGetters(['plugin', 'buildTools', 'languages', 'apis']),
  },
  watch: {
    plugin: {
      handler(value) {
        this.$store.commit(SET_PLUGIN, value);
      },
      deep: true,
    },
  },
  beforeCreate() {
    setPluginParameterFromURL(this.$route.query);
  },
  async created() {
    this.apiVersions = await loadVersions();
  },
};
</script>
