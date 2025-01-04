<script setup lang="ts">
import { useNuxtApp } from 'nuxt/app';

const nuxtApp = useNuxtApp();
const isFullLoading = ref(true);
nuxtApp.hook('page:start', () => {
  isFullLoading.value = true;
});
nuxtApp.hook('page:finish', () => {
  isFullLoading.value = false;
});
</script>

<template>
  <FullLoading v-if="isFullLoading" />
  <NuxtLayout>
    <NuxtLoadingIndicator />
    <NuxtPage />
  </NuxtLayout>
</template>

<style scoped lang="scss">
.layout-enter-active,
.layout-leave-active {
  transition: all 0.4s;
}

.layout-enter-from,
.layout-leave-to {
  filter: grayscale(1);
}
</style>
