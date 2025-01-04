import { nxViteTsPaths } from '@nx/vite/plugins/nx-tsconfig-paths.plugin';
import { defineNuxtConfig } from 'nuxt/config';
import { resolve } from 'path';

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  workspaceDir: '../../',
  srcDir: 'src',
  devtools: { enabled: true },
  devServer: {
    port: 23412,
  },
  typescript: {
    typeCheck: true,
    tsConfig: {
      extends: '../tsconfig.app.json', // Nuxt copies this string as-is to the `./.nuxt/tsconfig.json`, therefore it needs to be relative to that directory
    },
  },
  imports: {
    autoImport: true,
  },
  app: {
    layoutTransition: { name: 'layout', mode: 'out-in' },
  },
  css: ['~/assets/css/styles.scss', 'libs/ng/src/styles/index.scss'],
  vite: {
    plugins: [nxViteTsPaths()],
    resolve: {
      alias: {
        '@utils': resolve(__dirname, 'src/utils'),
        '@levons/common': resolve(__dirname, '../../libs/common/src/index.ts'),
      },
    },
  },
  modules: [
    '@element-plus/nuxt',
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    'pinia-plugin-persistedstate/nuxt',
  ],
});
