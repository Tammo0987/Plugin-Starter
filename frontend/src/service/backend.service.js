import axios from 'axios';
import store from '@/store';

import { MUTATE_METADATA, MUTATE_PLUGIN, SET_DEPENDENCIES } from '@/store/mutations';

const baseUrl = process.env.VUE_APP_BACKEND_URL || 'https://backend.plugin-starter.com/api';

const client = axios.create({
  baseURL: baseUrl,
});

const generate = async (plugin) => client.post('/generate', plugin, {
  responseType: 'blob',
});

const loadVersions = async () => {
  const { data } = await client.get('/versions');
  return data;
};

const loadDependencies = async () => {
  const { data } = await client.get('/dependencies');
  return data.categories;
};

const checkSelectionParameter = (name, selection, query) => {
  if (query[name] && store.getters[selection].includes(query[name].toUpperCase())) {
    store.commit(MUTATE_PLUGIN, { [name]: query[name].toUpperCase() });
  }
};

const setPluginParameterFromURL = async (query) => {
  if (query.name || query.group || query.version || query.description) {
    store.commit(MUTATE_METADATA, query);
  }

  if (query.authors) {
    const metadata = { authors: query.authors.split(',') };
    store.commit(MUTATE_METADATA, metadata);
  }

  const parameters = {
    buildTool: 'buildTools',
    language: 'languages',
    api: 'apis',
  };

  Object.keys(parameters).forEach((key) => checkSelectionParameter(key, parameters[key], query));

  if (query.dependencies) {
    const dependencies = query.dependencies.split(',').map((name) => name.toLowerCase());
    const loadedCategories = await loadDependencies();

    const loadedDependencies = loadedCategories.flatMap((category) => category.dependencies)
      .filter((element) => element);

    const dependenciesToAdd = loadedDependencies
      .filter((dependency) => dependencies.includes(dependency.name.toLowerCase()));

    store.commit(SET_DEPENDENCIES, dependenciesToAdd);
  }

  if (query.apiVersion) {
    const allVersions = await loadVersions();
    const versions = allVersions[store.getters.plugin.api];

    if (versions.includes(query.apiVersion.toUpperCase())) {
      store.commit(MUTATE_PLUGIN, { version: query.apiVersion.toUpperCase() });
    }
  }
};

export {
  generate,
  loadVersions,
  loadDependencies,
  setPluginParameterFromURL,
};
