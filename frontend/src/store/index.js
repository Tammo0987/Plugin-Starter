import Vue from 'vue';
import Vuex from 'vuex';

import {
  ADD_AUTHOR,
  ADD_DEPENDENCY,
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
    version: '1.8.8-R0.1-SNAPSHOT',
    language: 'JAVA',
    repositories: [],
    dependencies: [],
  },
};

export default new Vuex.Store({
  state: initialState,
  getters: {
    plugin: (state) => state.plugin,
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
    [ADD_AUTHOR](state, author) {
      state.plugin.metadata.authors.push(author);
    },
    [REMOVE_AUTHOR](state, author) {
      state.plugin.metadata.authors = state.plugin.metadata
        .authors.filter((item) => item !== author);
    },
  },
});
