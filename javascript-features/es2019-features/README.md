| Topic                             | New Feature                                                                         | Test / Class Example                                       |
|-----------------------------------|-------------------------------------------------------------------------------------|------------------------------------------------------------|
| `Array.prototype.flat`            | Flattens nested arrays up to a specified depth                                      | [`arrayFlat`](features/arrayFlat.js)                       |
| `Array.prototype.flatMap`         | Maps and flattens arrays in a single operation                                      | [`arrayFlatMap`](features/arrayFlatMap.js)                 |
| `Object.fromEntries()`            | Creates an object from key-value pairs (inverse of `Object.entries()`)              | [`objectFromEntries`](features/objectFromEntries.js)       |
| `String.prototype.trimStart/End`  | New methods to trim whitespace from the start/end of a string                       | [`stringTrim`](features/stringTrim.js)                     |
| Optional `catch` binding          | `catch` block can now omit the error variable                                       | [`optionalCatchBinding`](features/optionalCatchBinding.js) |
| `Symbol.prototype.description`    | Direct access to a symbol’s description via a `.description` getter                 | [`symbolDescription`](features/symbolDescription.js)       |
| Well-formed JSON `Stringify`      | `JSON.stringify` now handles lone surrogates correctly to produce well-formed UTF-8 | [`jsonWellFormed`](features/jsonWellFormed.js)             |
 | Stable `Array.prototype.sort()`   | Sort order is now guaranteed to be stable (equal items retain relative order)       | [`arraySortStable`](features/arraySortStable.js)           |
