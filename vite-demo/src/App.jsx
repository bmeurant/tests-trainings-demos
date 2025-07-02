// src/App.jsx
import React, { useState } from 'react'; // Keep React and useState
import viteLogo from '/vite.svg';
import javascriptLogo from '@/javascript.svg';
import styles from './style.module.scss'; // Keep CSS Module import
import imageSrc from '@/image-src.svg'; // Keep image import

// Removed direct usage of setupCounter as React's useState handles the counter
// import { setupCounter } from './counter.ts'; // This is no longer directly needed in App.jsx for the button

import { showLazyMessage } from './lazy-module.js'; // For dynamic import demo
import { v4 as uuidv4 } from 'uuid'; // For the UUID demo

function App() {
    // State for the counter, replacing setupCounter's logic
    const [count, setCount] = useState(0);

    // Conditional feature flag (from previous step 5)
    const newFeatureEnabled = import.meta.env.VITE_ENABLE_NEW_FEATURE === 'true';

    // Function to increment the counter
    const handleCounterClick = () => {
        setCount((prevCount) => prevCount + 1);
    };

    // Function to load the dynamic module
    const handleLoadLazyModule = async () => {
        // showLazyMessage() is already imported at the top from lazy-module.js
        // If lazy-module.js was a React component, you'd use React.lazy here.
        // For a simple JS function, direct call is fine.
        showLazyMessage();
    };

    return (
        <> {/* This Fragment allows returning multiple elements */}
            <div>
                <a href="https://vitejs.dev" target="_blank" rel="noopener noreferrer">
                    <img src={viteLogo} className={styles.logo} alt="Vite logo" />
                </a>
                <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noopener noreferrer">
                    <img src={javascriptLogo} className={`${styles.logo} ${styles.vanilla}`} alt="JavaScript logo" />
                </a>
            </div>
            {/* Dynamic title from environment variables */}
            <h1>{import.meta.env.VITE_APP_TITLE}</h1>

            <div className={styles.card}>
                {/* Counter button re-implemented with React state */}
                <button id="counter" type="button" onClick={handleCounterClick}>
                    count is {count}
                </button>
            </div>

            <p className={styles.readTheDocs}>
                Click on the Vite logo to learn more
            </p>

            {/* Image from src folder */}
            <img src={imageSrc} alt="Image from src folder" style={{ width: '100px', height: '100px', marginTop: '20px' }} />

            {/* Dynamic module loading button */}
            <button id="load-lazy-module" onClick={handleLoadLazyModule} style={{ marginLeft: '10px' }}>
                Load dynamic module
            </button>

            {/* Display a generated UUID */}
            <p style={{ marginTop: '10px', fontSize: '0.9em', color: '#666' }}>Generated UUID for demo: <strong>{uuidv4()}</strong></p>
        </>
    );
}

export default App;