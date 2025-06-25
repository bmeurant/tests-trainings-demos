// es2024-features/features/resizableArrayBuffers.js
const assert = require('assert');

function runResizableArrayBufferDemo() {
    console.log('\n--- ES2024: Resizable and Growable ArrayBuffers ---');

    // Creating a resizable ArrayBuffer
    // The 'maxByteLength' option is crucial to specify the maximum size the buffer can grow to.
    // Without 'maxByteLength', it defaults to a fixed-size buffer.
    const initialByteLength = 16; // 16 bytes
    const maxByteLength = 32;     // Can grow up to 32 bytes

    const buffer = new ArrayBuffer(initialByteLength, { maxByteLength: maxByteLength });

    console.log(`Initial ArrayBuffer byteLength: ${buffer.byteLength}`);
    console.log(`Max ArrayBuffer byteLength: ${buffer.maxByteLength}`);
    assert.strictEqual(buffer.byteLength, initialByteLength, 'Assertion failed: Initial byteLength mismatch.');
    assert.strictEqual(buffer.maxByteLength, maxByteLength, 'Assertion failed: Max byteLength mismatch.');

    // Creating a Uint8Array view over the buffer
    // Views become "detached" if the underlying ArrayBuffer is resized to a smaller size
    // and the view's range is outside the new bounds.
    let view = new Uint8Array(buffer);
    view[0] = 123;
    console.log(`View element at index 0 (initial): ${view[0]}`);

    // Resizing the ArrayBuffer
    // The .resize() method changes the byteLength of the buffer.
    // It returns undefined if successful, throws RangeError if newLength > maxByteLength.
    const newByteLength = 24; // Resize to 24 bytes (between initial and max)
    buffer.resize(newByteLength);

    console.log(`Resized ArrayBuffer byteLength: ${buffer.byteLength}`);
    assert.strictEqual(buffer.byteLength, newByteLength, 'Assertion failed: Resized byteLength mismatch.');

    // Accessing elements after resize
    // Existing views are still valid if their range is within the new buffer size.
    // If resized to a smaller size, parts of existing views might become inaccessible.
    console.log(`View element at index 0 (after resize): ${view[0]}`); // Still 123
    assert.strictEqual(view[0], 123, 'Assertion failed: View element changed unexpectedly after resize.');

    // Try to grow beyond maxByteLength (should throw an error)
    try {
        buffer.resize(maxByteLength + 1);
        assert.fail('Assertion failed: Should throw RangeError when resizing beyond maxByteLength.');
    } catch (e) {
        console.log(`Caught expected error when resizing beyond maxByteLength: ${e.name}`);
        assert.strictEqual(e instanceof RangeError, true, 'Assertion failed: Error should be RangeError.');
    }

    // Create another view for different range after resize
    const anotherView = new Uint8Array(buffer, 8, 8); // A view of 8 bytes starting at offset 8
    console.log(`Another view created (byteOffset 8, byteLength 8). Its byteLength: ${anotherView.byteLength}`);
    assert.strictEqual(anotherView.byteLength, 8, 'Assertion failed: Another view byteLength mismatch.');

    console.log('Assertion passed: Resizable ArrayBuffer creation and resizing works.');
}

try {
    runResizableArrayBufferDemo();
} catch (error) {
    console.error('\n!!! ES2024 Resizable ArrayBuffer Demo FAILED !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}