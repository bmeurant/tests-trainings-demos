| Topic                             | New Feature                                                                                    | Test / Class Example                                                       |
|-----------------------------------|------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| `Promise.any()`                   | Resolves with the first fulfilled promise, or rejects with `AggregateError` if all fail        | [`promiseAny`](features/promiseAny.js)                                     |
| `AggregateError`                  | Standard error class used by `Promise.any()` and custom batch operations                       | [`aggregateError`](features/aggregateError.js)                             |
| `String.prototype.replaceAll()`   | Replaces all matches of a substring or regex                                                   | [`stringReplaceAll`](features/stringReplaceAll.js)                         |
| Logical Assignment Operators      | New operators: `\|\|=`, `&&=`, `??=` for combining logic and assignment                        | [`logicalAssignmentOperators`](features/logicalAssignmentOperators.js)     |
| WeakRefs and FinalizationRegistry | Enables access to weakly held objects and cleanup after GC (advanced use cases)                | [`weakRefsFinalizationRegistry`](features/weakRefsFinalizationRegistry.js) |
| Numeric Separators                | Underscore `_` can be used to improve readability of large numeric literals (e.g. `1_000_000`) | [`numericSeparators`](features/numericSeparators.js)                       |


