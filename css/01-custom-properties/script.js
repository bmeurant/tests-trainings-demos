// Step 1.3: Manipulating CSS Variables with JavaScript
// This script shows how to dynamically change the value of a CSS variable, both globally and locally.

// Function to generate a random hex color
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

/* --- Global Variable Manipulation --- */
// Get the global button and the root HTML element
const changeGlobalColorBtn = document.getElementById('changeGlobalColorBtn');
const root = document.documentElement;

// Add a click listener to change the global --primary-color
changeGlobalColorBtn.addEventListener('click', () => {
    const newColor = getRandomColor();
    // Use setProperty on the root element to change the global variable
    root.style.setProperty('--primary-color', newColor);
    console.log(`Global color changed to: ${newColor}`);
});


/* --- Local Variable Manipulation --- */
// Get the local button and the element that holds the local variable
const changeLocalColorBtn = document.getElementById('changeLocalColorBtn');
const highlightZone = document.querySelector('.card-highlight');

// Add a click listener to change the local --primary-color within the highlight zone
changeLocalColorBtn.addEventListener('click', () => {
    const newColor = getRandomColor();
    // Use setProperty on the specific element to change its local variable
    highlightZone.style.setProperty('--primary-color', newColor);
    console.log(`Local color changed to: ${newColor}`);
});