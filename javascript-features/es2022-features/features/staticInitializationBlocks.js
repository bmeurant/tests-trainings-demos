// es2022-features/features/staticInitializationBlocks.js
const assert = require('assert');

function runStaticInitializationBlocksDemo() {
    console.log('--- ES2022: Static Class Initialization Blocks ---');

    class Logger {
        static #instanceCount = 0; // Private static field
        static #initialized = false; // Another private static field

        static logLevel; // Public static field

        // Static initialization block
        static {
            console.log('  [Logger Class]: Static initialization block executed.');
            // Complex logic to determine log level based on environment variables, etc.
            if (process.env.NODE_ENV === 'production') {
                this.logLevel = 'INFO';
            } else if (process.env.DEBUG_MODE === 'true') {
                this.logLevel = 'DEBUG';
            } else {
                this.logLevel = 'WARN';
            }
            this.#initialized = true; // Mark as initialized
            console.log(`  [Logger Class]: Log level set to ${this.logLevel}`);
        }

        constructor(name) {
            this.name = name;
            Logger.#instanceCount++;
            console.log(`  [Logger Class]: New Logger instance created: ${this.name}. Total instances: ${Logger.#instanceCount}`);
        }

        static getInitializedStatus() {
            return this.#initialized;
        }

        static getInstanceCount() {
            return this.#instanceCount;
        }

        log(message) {
            console.log(`[${this.name} - ${Logger.logLevel}] ${message}`);
        }
    }

    console.log('\n--- Checking initial static properties ---');
    assert.strictEqual(Logger.getInitializedStatus(), true, 'Assertion Failed: Static block should have initialized.');
    // Based on the default environment (likely not production or DEBUG_MODE=true)
    assert.strictEqual(Logger.logLevel, 'WARN', 'Assertion Failed: Log level not set correctly by static block.');
    console.log('Assertion Passed: Static properties initialized by static block.');

    console.log('\n--- Creating instances ---');
    const logger1 = new Logger('AppLogger');
    const logger2 = new Logger('DBLogger');

    assert.strictEqual(Logger.getInstanceCount(), 2, 'Assertion Failed: Instance count incorrect.');
    logger1.log('Application started.');
    logger2.log('Database connected.');
    console.log('Assertion Passed: Instances created and static properties are shared.');

    // Another static block can be added (they run in order)
    class AnotherClass {
        static #data = [];
        static MAX_SIZE;

        static {
            console.log('\n  [AnotherClass]: First static block executed.');
            this.#data.push('Item 1');
            this.MAX_SIZE = 5;
        }

        static {
            console.log('  [AnotherClass]: Second static block executed.');
            this.#data.push('Item 2');
        }

        static getData() {
            return [...this.#data]; // Return a copy
        }
    }

    console.log('\n--- Checking AnotherClass static properties ---');
    assert.deepStrictEqual(AnotherClass.getData(), ['Item 1', 'Item 2'], 'Assertion Failed: Data not accumulated by static blocks.');
    assert.strictEqual(AnotherClass.MAX_SIZE, 5, 'Assertion Failed: MAX_SIZE not set by static block.');
    console.log('Assertion Passed: Multiple static blocks execute in order and can modify static data.');

    console.log('\n--- All Static Class Initialization Blocks demonstrations complete. ---');
}

try {
    runStaticInitializationBlocksDemo();
} catch (error) {
    console.error('\n!!! ES2022 Static Class Initialization Blocks Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}