| Topic                            | New Feature                                                                           | Test / Class Example                                   |
|----------------------------------|---------------------------------------------------------------------------------------|--------------------------------------------------------|
| `Object.hasOwn()`                | Safer alternative to `hasOwnProperty` for checking own properties directly on objects | [`objectHasOwn`](features/objectHasOwn.js)             |
| `Error.prototype.cause`          | Allows wrapping lower-level errors inside higher-level ones with a `cause` property   | [`errorCause`](features/errorCause.js)                 |
| Class Fields                     | Native support for public and private class fields (e.g., `#privateField`)            | [`classFields`](features/classFields.js)               |
| Top-level `await`                | Use of `await` directly at the top level of ES modules                                | [`topLevelAwait`](features/topLevelAwait.mjs)          |
| RegExp Match Indices (`/d` flag) | `match.indices` provides start and end indices for regex capturing groups             | [`regexpMatchIndices`](features/regexpMatchIndices.js) |
