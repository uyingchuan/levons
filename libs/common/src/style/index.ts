import { IndexableObject } from '../interfaces';

/**
 * 加载样式表
 * @param style 样式表
 * @param name 样式表名称
 * @param attr 接收名称的属性名
 */
export function loadStyle(
  style: string,
  name: string,
  attr = 'data-levons-style-id'
) {
  if (!document) return;

  const selector = document.querySelector(`style[${attr}="${name}"]`);
  const styleRef = selector || document.createElement('style');
  if (!styleRef.isConnected) {
    styleRef.setAttribute('type', 'text/css');
    styleRef.setAttribute(attr, name);
  }
  if (styleRef.textContent !== style) {
    styleRef.textContent = style;
  }
  document.head.appendChild(styleRef);
}

/**
 * 将深层样式表拍扁
 * @param obj 深层样式表
 * @param prefix 递归时传递父键
 */
export function flattenStylesObj(
  obj: IndexableObject,
  prefix = ''
): IndexableObject {
  let result: IndexableObject = {};

  for (const key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) {
      const value = obj[key];

      if (key === 'colorScheme') {
        result = { ...result, ...flattenStylesObj(value, prefix) };
        continue;
      }

      const formattedKey = prefix
        ? `${prefix}-${String(key)
            .replace(/([A-Z])/g, '-$1')
            .toLowerCase()}`
        : String(key).toLowerCase();
      if (typeof value === 'object' && value !== null) {
        result = { ...result, ...flattenStylesObj(value, formattedKey) };
      } else {
        result[formattedKey] = value;
      }
    }
  }

  return result;
}

/**
 * 转换样式表，只处理一层
 * @param obj 样式表配置
 * @param prefix 样式表前缀
 */
export function transformStyle(obj: IndexableObject, prefix = 'p'): string {
  let rootCssString = ':root { \n';
  let darkCssString = `.${prefix}-dark {`;
  for (const key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) {
      const value = obj[key];
      const resolvedValue =
        typeof value === 'string' ? resolveStyle(value, prefix) : value;

      if (key.includes('light')) {
        const newKey = key.split('light-')[1];
        rootCssString += `  --${prefix}-${newKey}: ${resolvedValue};\n`;
        continue;
      } else if (key.includes('dark')) {
        const newKey = key.split('dark-')[1];
        darkCssString += `  --${prefix}-${newKey}: ${resolvedValue};\n`;
        continue;
      }

      rootCssString += `  --${prefix}-${key}: ${resolvedValue};\n`;
    }
  }
  rootCssString += '}';
  darkCssString += '}';
  if (darkCssString === `.${prefix}-dark {}`) {
    return rootCssString;
  }
  return `${rootCssString}\n${darkCssString}`;
}

/**
 * 处理样式表变量
 */
function resolveStyle(value: string, prefix: string) {
  const regex = /\{([a-zA-Z0-9.]+)}/g;
  const match = regex.exec(value);

  if (match !== null) {
    const reference = match[1];
    value = value.replace(
      match[0],
      `var(--${prefix}-${reference.replace('.', '-')})`
    );
  }
  return value;
}

/**
 * 缩小样式表体积
 * @param style
 */
export function minifyStyles(style: string): string {
  return style
    .replace(/\/\*(?:(?!\*\/)[\s\S])*\*\/|[\r\n\t]+/g, '')
    .replace(/ {2,}/g, ' ')
    .replace(/ ([{:}]) /g, '$1')
    .replace(/([;,]) /g, '$1')
    .replace(/ !/g, '!')
    .replace(/: /g, ':');
}
