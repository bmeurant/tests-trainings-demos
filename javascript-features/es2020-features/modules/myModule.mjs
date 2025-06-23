// es2020-features/modules/myModule.mjs
// A simple module to demonstrate dynamic import
console.log('[myModule.mjs] Module loaded!');

export const greeting = 'Hello from myModule!';

export function sayHello(name) {
    return `${greeting} Your name is ${name}!`;
}

export default {
    message: 'This is the default export.',
    version: '1.0'
};