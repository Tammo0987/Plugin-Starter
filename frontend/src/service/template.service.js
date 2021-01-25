import store from '@/store';

const generateURL = () => {
  const { plugin } = store.getters;

  const { metadata } = plugin;

  const authors = metadata.authors.join(',');
  const dependencies = plugin.dependencies.map((dependency) => dependency.name).join(',');

  const mappedPlugin = {
    name: metadata.name,
    group: metadata.group,
    version: metadata.version,
    description: metadata.description,
    buildTool: plugin.buildTool,
    language: plugin.language,
    api: plugin.api,
    apiVersion: plugin.version,
    authors,
    dependencies,
  };

  const parameters = Object.keys(mappedPlugin).map((key) => `${key}=${mappedPlugin[key]}`).join('&');

  return `${window.location.href.split('?')[0]}?${parameters}`;
};
export default {
  generateURL,
};
