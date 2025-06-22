| Topic                           | New Feature                                                                        | Test / Class Example                                               |
|---------------------------------|------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| Rest/Spread in Objects          | Use of `...rest` and `...spread` syntax with object literals                       | [`objectSpreadRest`](./features/objectSpreadRest.js)               |
| Promise.prototype.finally       | `finally()` method added to `Promise` for finalization logic regardless of outcome | [`promiseFinally`](./features/promiseFinally.js)                   |
| Asynchronous Iteration          | Support for `for await...of` to consume async iterables                            | [`asyncIteration`](./features/asyncIteration.js)                   |
| RegExp Named Capture Groups     | Named capturing in regex using `(?<name>...)` and back-referencing via `$<name>`   | [`regExpNamedCapture`](./features/regExpNamedCapture.js)           |
| RegExp Lookbehind Assertions    | Support for lookbehind assertions using `(?<=...)` and `(?<!...)`                  | [`regExpLookbehind`](./features/regExpLookbehind.js)               |
| RegExp `s` (dotAll) flag        | Enables dot (`.`) in regex to match newline characters (`\n`, `\r`, etc.)          | [`regExpDotAll`](./features/regExpDotAll.js)                       |
| RegExp Unicode Property Escapes | New `\p{...}` and `\P{...}` escapes for Unicode-aware regular expressions          | [`regExpUnicodeProperties`](./features/regExpUnicodeProperties.js) |

