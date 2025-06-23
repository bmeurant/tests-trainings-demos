// es2021-features/features/weakRefsFinalizationRegistry.js
const assert = require('assert');

// To properly observe garbage collection and finalization,
// Node.js often requires specific flags or a deeper understanding of its GC.
// For this demo, we'll simulate the concept and use timers.
// In a real-world scenario, you'd need to run with --expose-gc
// and explicitly call global.gc() which is not recommended in production.

function runWeakRefsFinalizationRegistryDemo() {
    console.log('--- ES2021: WeakRefs and FinalizationRegistry ---');

    let objectToMonitor = {
        data: "I'm important but can be collected!"
    };

    console.log('\n--- 1. WeakRef Demonstration ---');
    const weakRef = new WeakRef(objectToMonitor);
    console.log('Created a WeakRef to objectToMonitor.');

    // Dereference the object (remove strong reference)
    objectToMonitor = null;
    console.log('Strong reference to objectToMonitor set to null.');

    // At this point, the object is eligible for garbage collection.
    // Accessing the WeakRef target:
    // Note: In real scenarios, `weakRef.deref()` might still return the object
    // immediately after nulling the strong ref, as GC is non-deterministic.
    // We'll use a timeout to give GC a chance (though not guaranteed).
    setTimeout(() => {
        const dereferencedObject = weakRef.deref();
        if (dereferencedObject) {
            console.log(`  WeakRef target is still available (deref()): ${dereferencedObject.data}`);
            assert.strictEqual(dereferencedObject.data, "I'm important but can be collected!", 'Assertion Failed: WeakRef data mismatch');
        } else {
            console.log('  WeakRef target has been garbage collected (deref() returned undefined).');
            // In a proper test with forced GC, this would be the expected path
            assert.strictEqual(dereferencedObject, undefined, 'Assertion Failed: WeakRef target expected to be undefined after GC.');
        }
        console.log('Assertion Passed: WeakRef mechanism demonstrated (subject to GC timing).');
    }, 50); // Small delay for demonstration

    // --- 2. FinalizationRegistry Demonstration ---
    console.log('\n--- 2. FinalizationRegistry Demonstration (Conceptual) ---');
    const registry = new FinalizationRegistry((heldValue) => {
        console.log(`  FinalizationRegistry callback fired for value: ${heldValue}`);
        // In a real app, you would clean up resources here
        // e.g., close a file handle associated with `key`
    });

    let resourceObject = {
        name: 'Temporary Resource',
        id: Math.random()
    };
    const resourceId = `resource-${resourceObject.id}`; // A value to pass to the finalizer
    registry.register(resourceObject, resourceId);
    console.log(`Registered resourceObject with FinalizationRegistry. Held value: ${resourceId}`);

    // Dereference the object (make it eligible for GC)
    resourceObject = null;
    console.log('Strong reference to resourceObject set to null.');

    // Now, the object is eligible for GC. When GC runs and collects it,
    // the FinalizationRegistry callback *might* be invoked.
    // This is non-deterministic and can happen anytime after GC.
    // We cannot assert the exact timing of the callback in standard Node.js.
    console.log('  (The FinalizationRegistry callback will fire asynchronously if/when the object is garbage collected).');
    console.log('  For this demo, we cannot programmatically assert the finalizer execution.');
    console.log('  You would see "FinalizationRegistry callback fired..." in the console if GC runs.');
}

// Since GC is non-deterministic, we'll run this demo and observe output,
// rather than relying on strict assertions for `deref()` becoming undefined or finalizer firing.
try {
    runWeakRefsFinalizationRegistryDemo();
    // Keep the process alive for a moment to allow potential GC callbacks
    // In a real app, this script would run for longer.
    setTimeout(() => {
        console.log('\n--- All WeakRefs and FinalizationRegistry demonstrations complete (conceptual). ---');
    }, 100);
} catch (error) {
    console.error('\n!!! ES2021 WeakRefs and FinalizationRegistry Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}