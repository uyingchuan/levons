<template>
  <main>
    <layouts-header />
    <nuxt-page />
  </main>
</template>

<script lang="ts" setup>
const userStore = useUserStore();
</script>

<style scoped lang="scss">
@import 'default.scss';
</style>
