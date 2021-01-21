import axios from 'axios';

const baseUrl = process.env.BACKEND_API || 'http://127.0.0.1/api';

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

export {
  generate,
  loadVersions,
  loadDependencies,
};
