// src/main.jsx
import './style.module.scss'; // Keep CSS Module import
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx'; // Import our React App component

const appRoot = document.querySelector('#app');

if (appRoot) {
    ReactDOM.createRoot(appRoot).render(
        <React.StrictMode>
            <App />
        </React.StrictMode>
    );
} else {
    console.error('Root element #app not found!');
}