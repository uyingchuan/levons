import {
  flattenStylesObj,
  IndexableObject,
  loadStyle,
  minifyStyles,
  presetKeys,
  PresetTheme,
  PresetThemes,
  presetThemes,
  transformStyle,
} from '@levons/common';

export const useAppStore = defineStore('app', {
  state: (): AppState => ({
    preset: 'Aura',
    primary: 'green',
    surface: null,
    darkTheme: false,
    presetThemes: presetThemes,
  }),
  getters: {},
  actions: {
    /**
     * 启动应用时初始化
     */
    initApp() {
      if (import.meta.server) return;

      this.setDarkTheme(this.darkTheme);
      this.setPreset(this.preset);
      this.setPrimary(this.primary);
    },
    /**
     * 设置浅色或深色主题
     * @param darkTheme
     */
    setDarkTheme(darkTheme: boolean) {
      if (import.meta.server) return;

      if (darkTheme) {
        document.documentElement.classList.add('p-dark');
      } else {
        document.documentElement.classList.remove('p-dark');
      }
      this.darkTheme = darkTheme;
    },
    /**
     * 设置预设样式表
     * @param preset
     */
    setPreset(preset: string) {
      const theme: PresetTheme =
        this.presetThemes[preset] ?? this.presetThemes.Aura;
      const { primitive, semantic } = theme;
      const flattenPrimitive = flattenStylesObj(primitive);
      const flattenSemantic = flattenStylesObj(semantic);
      const primitiveStyles = transformStyle(flattenPrimitive);
      const semanticStyles = transformStyle(flattenSemantic);
      const minifyPrimitiveStyles = minifyStyles(primitiveStyles);
      const minifySemanticStyles = minifyStyles(semanticStyles);
      loadStyle(minifyPrimitiveStyles, 'primitive-variables');
      loadStyle(minifySemanticStyles, 'semantic-variables');
    },
    /**
     * 设置主题色
     * @param primary
     */
    setPrimary(primary: string) {
      const preset: PresetTheme = this.presetThemes[this.preset];
      const colors: IndexableObject = preset.primitive[primary];
      const flattenPrimary = {};
      for (const key in colors) {
        const newKey = `primary-${key}`;
        flattenPrimary[newKey] = colors[key];
      }
      const primaryStyles = transformStyle(flattenPrimary);
      const minifyPrimaryStyles = minifyStyles(primaryStyles);
      loadStyle(minifyPrimaryStyles, 'primary-variables');
    },
  },
  persist: {
    storage: piniaPluginPersistedstate.localStorage(),
  },
});

export interface AppState {
  preset: presetKeys;
  primary: string;
  surface: string | null;
  darkTheme: boolean;
  presetThemes: PresetThemes;
}
