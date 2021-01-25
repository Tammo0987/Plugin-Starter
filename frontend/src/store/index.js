import Vue from 'vue';
import Vuex from 'vuex';

import {
  ADD_AUTHOR,
  SET_DEPENDENCIES,
  ADD_DEPENDENCY,
  MUTATE_METADATA,
  MUTATE_PLUGIN,
  REMOVE_AUTHOR,
  REMOVE_DEPENDENCY,
  SET_PLUGIN,
} from './mutations';

Vue.use(Vuex);

const initialState = {
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
    version: '',
    language: 'JAVA',
    repositories: [],
    dependencies: [],
  },
  buildTools: ['MAVEN', 'GRADLE'],
  languages: ['JAVA', 'KOTLIN'],
  apis: ['SPIGOT', 'PAPER', 'SPONGE'],
};

export default new Vuex.Store({
  state: initialState,
  getters: {
    plugin: (state) => state.plugin,
    buildTools: (state) => state.buildTools,
    languages: (state) => state.languages,
    apis: (state) => state.apis,
  },
  mutations: {
    [SET_PLUGIN](state, plugin) {
      state.plugin = plugin;
    },
    [ADD_DEPENDENCY](state, dependency) {
      state.plugin.dependencies.push(dependency);
    },
    [REMOVE_DEPENDENCY](state, dependency) {
      state.plugin.dependencies = state.plugin.dependencies.filter((item) => item !== dependency);
    },
    [SET_DEPENDENCIES](state, dependencies) {
      state.plugin.dependencies = dependencies;
    },
    [ADD_AUTHOR](state, author) {
      state.plugin.metadata.authors.push(author);
    },
    [REMOVE_AUTHOR](state, author) {
      state.plugin.metadata.authors = state.plugin.metadata
        .authors.filter((item) => item !== author);
    },
    [MUTATE_METADATA](state, metadata) {
      state.plugin.metadata = { ...state.plugin.metadata, ...metadata };
    },
    [MUTATE_PLUGIN](state, plugin) {
      state.plugin = { ...state.plugin, ...plugin };
    },
  },
});
