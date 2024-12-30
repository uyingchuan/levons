<script lang="ts" setup></script>

<template>
  <header>
    <nav class="navbar flex shadow-1 surface-card">
      <div class="layout-container !pt-0 flex justify-between">
        <div class="flex items-center" />
        <div class="flex items-center">
          <NuxtLink class="nav-link text-primary">
            发表文章
          </NuxtLink>
          <a class="nav-link text-base">{{ '登录' }}</a>
        </div>
      </div>
    </nav>
  </header>
</template>

<style scoped lang="scss">
.navbar {
  position: fixed;
  top: 0;
  width: 100%;
  height: 3.25rem;
  z-index: 10;
}

.navbar > .container {
  flex-grow: 1;
  margin: 0 auto;
  position: relative;
  width: 100%;
}

.navbar .nav-link {
  color: var(--text-color);
  padding: 5px 10px;
  margin-right: 1.25rem;
  position: relative;
  cursor: pointer;
  text-decoration: none;
}

.nav-link.router-link-active {
  color: var(--primary-color);
}
</style>
