import { defineNuxtPlugin } from '#app';
import * as ElIcons from '@element-plus/icons-vue';

export default defineNuxtPlugin((nuxtApp) => {
  for (const [key, component] of Object.entries(ElIcons)) {
    nuxtApp.vueApp.component(key, component);
  }
});
