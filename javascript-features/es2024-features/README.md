| Topic                                | New Feature                                                                            | Test / Class Example                                       |
|--------------------------------------|----------------------------------------------------------------------------------------|------------------------------------------------------------|
| Stable `toSorted()`                  | Clarifies that `toSorted()` now performs stable sorting (behavior finalized in ES2024) | [`toSortedStable`](features/toSortedStable.js)             |
| Promise Utilities                    | `Promise.withResolvers()` returns pre-separated `resolve` and `reject` functions       | [`promiseWithResolvers`](features/promiseWithResolvers.js) |
| String Well-Formed Checks            | `String.prototype.isWellFormed()` and `.toWellFormed()` ensure Unicode correctness     | [`wellFormedUnicode`](features/wellFormedUnicode.js)       |
| `Object.groupBy()` & `Map.groupBy()` | Enables declarative grouping of values into objects or maps via callback functions     | [`objectMapGroupBy`](features/objectMapGroupBy.js)         |

