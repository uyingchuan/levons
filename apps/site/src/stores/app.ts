import { useLocalStorage } from '@vueuse/core';
import { defineStore } from 'pinia';
import { StorageKey } from '@utils/constants/storage';

export const useAppStore = defineStore('app', {
  state: (): AppState => ({
    theme: 'light',
  }),
  getters: {},
  actions: {
    initApp() {
      const storageTheme = useLocalStorage(StorageKey.theme, 'light');

      this.setTheme(storageTheme.value as Theme);
    },
    setTheme(theme: 'light' | 'dark') {
      if (import.meta.server) return;

      document.body.setAttribute('app-theme-style', this.theme);
      this.theme = theme;
    },
  },
});

type Theme = 'light' | 'dark';

export interface AppState {
  theme: Theme;
}
