// src/App.jsx
import React, { useState, useEffect } from 'react'; // NEW: Import useEffect
import viteLogo from '/vite.svg';
import javascriptLogo from '@/javascript.svg';
import styles from './style.module.scss';
import imageSrc from '@/image-src.svg';
import { showLazyMessage } from './lazy-module.js';
import { v4 as uuidv4 } from 'uuid';

function App() {
    const [count, setCount] = useState(0);
    const [apiData, setApiData] = useState(null); // NEW: State for API data
    const [apiError, setApiError] = useState(null); // NEW: State for API error

    const handleCounterClick = () => {
        setCount((prevCount) => prevCount + 1);
    };

    const handleLoadLazyModule = async () => {
        showLazyMessage();
    };

    // useEffect to fetch data from the proxied API
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('/api/data'); // Request to our proxied endpoint
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setApiData(data.message);
            } catch (error) {
                console.error('Error fetching API data:', error);
                setApiError('Failed to fetch data from API.');
            }
        };

        fetchData();
    }, []); // Empty dependency array means this runs once on component mount

    return (
        <>
            <div>
                <a href="https://vitejs.dev" target="_blank" rel="noopener noreferrer">
                    <img src={viteLogo} className={styles.logo} alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank" rel="noopener noreferrer">
                    <img src={javascriptLogo} className={`${styles.logo} ${styles.react}`} alt="React logo" />
                </a>
            </div>
            <h1>{import.meta.env.VITE_APP_TITLE}</h1>

            <div className={styles.card}>
                <button id="counter" type="button" onClick={handleCounterClick}>
                    count is {count}
                </button>
                <p>
                    Edit <code>src/App.jsx</code> and save to test HMR
                </p>
            </div>

            <p className={styles.readTheDocs}>
                Click on the Vite logo to learn more
            </p>

            <img src={imageSrc} alt="Image from src folder" style={{ width: '100px', height: '100px', marginTop: '20px' }} />
            <button id="load-lazy-module" onClick={handleLoadLazyModule} style={{ marginLeft: '10px' }}>
                Load dynamic module
            </button>

            {/* Display a generated UUID */}
            <p style={{ marginTop: '10px', fontSize: '0.9em', color: '#666' }}>Generated UUID for demo: <strong>{uuidv4()}</strong></p>

            {/* Display API Data */}
            <div style={{ marginTop: '20px', padding: '10px', backgroundColor: '#fffbe0', border: '1px solid #ffed80' }}>
                <h3>API Data Demo</h3>
                {apiData ? (
                    <p>Data from Backend: <strong>{apiData}</strong></p>
                ) : apiError ? (
                    <p style={{ color: 'red' }}>Error: {apiError}</p>
                ) : (
                    <p>Loading API data...</p>
                )}
            </div>
        </>
    );
}

export default App;