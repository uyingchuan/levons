/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./apps/**/*.{html,vue,js,ts}', './libs/**/*.{html,vue,js,ts}'],
  theme: {
    extend: {},
  },
  plugins: [require('tailwindcss-primeui')],
};
