export default defineNuxtPlugin((app) => {
  app.hook('app:created', () => {
    const appStore = useAppStore();
    appStore.initApp();
  });
});
