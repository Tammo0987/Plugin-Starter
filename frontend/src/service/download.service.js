const download = (data, name) => {
  const url = window.URL.createObjectURL(
    new Blob([data], { type: 'application/zip' }),
  );
  const link = document.createElement('a');

  link.href = url;
  link.setAttribute('download', name);

  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

export default {
  download,
};
