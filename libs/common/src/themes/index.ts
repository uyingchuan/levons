import Nora from './nora';
import Aura from './aura';
import Lara from './lara';
import Material from './material';
import { IndexableObject } from '../interfaces';

export const presetThemes: PresetThemes = {
  Aura,
  Lara,
  Material,
  Nora,
};

export const colors = [
  'emerald',
  'green',
  'lime',
  'orange',
  'amber',
  'yellow',
  'teal',
  'cyan',
  'sky',
  'blue',
  'indigo',
  'violet',
  'purple',
  'fuchsia',
  'pink',
  'rose',
];

export type PresetThemes = {
  [key in presetKeys]: PresetTheme;
};

export type presetKeys = 'Aura' | 'Lara' | 'Material' | 'Nora';

export interface PresetTheme {
  primitive: IndexableObject;
  semantic: IndexableObject;
}
