| Topic                     | New Feature                                                                               | Test / Class Example                           |
|---------------------------|-------------------------------------------------------------------------------------------|------------------------------------------------|
| `Promise.any()`           | Resolves with the first fulfilled promise, or rejects with `AggregateError` if all fail   | [`promiseAny`](features/promiseAny.js)         |
| `AggregateError`          | Standard error class used by `Promise.any()` and custom batch operations                  | [`aggregateError`](features/aggregateError.js) |

