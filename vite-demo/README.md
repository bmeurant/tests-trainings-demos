# Vite Tutorial

This tutorial will guide you through the basics of Vite, demonstrating its key features like fast development server, Hot Module Replacement (HMR), and optimized production builds.

## Tutorial Program

1.  **Project Setup & Dependencies:**
    *   Create a new Vite project.
    
        ```bash
        npm create vite@latest vite-demo -- --template vanilla
        ```
        
        **Expected output:**
    
        ```bash
        Need to install the following packages:
        create-vite
        Ok to proceed? (y)
        √ Project name: » vite-demo
        √ Select a framework: » Vanilla
        √ Select a variant: » JavaScript
        
        Scaffolding project in /home/your-user/vite-demo-project...
        
        Done. Now run:
        
        cd vite-demo-project
        npm install
        npm run dev
        ```

    *   Install project dependencies.
    
        ```bash
        cd vite-demo
        npm install
        ```
        
        **Expected output:**
    
        ```bash
        added 44 packages, and audited 58 packages in 2s

        5 packages are looking for funding
          run `npm fund` for details
        
        found 0 vulnerabilities
        ```

    *   Understand the project structure.

        ```
        vite-demo/
        ├── public/             // Directory for static assets served directly
        │   └── vite.svg        // Vite's SVG logo, a static asset
        ├── index.html          // Main entry point of the application
        ├── main.js             // The primary JavaScript entry point for your code
        ├── style.css           // The main stylesheet for the application
        ├── javascript.svg      // An SVG icon used within the generated example code
        ├── package.json        // Project metadata and NPM scripts configuration
        ├── vite.config.js      // Vite-specific configuration file for customization
        └── README.md           // Project's readme file for documentation
        ```

2.  **Fast Development Server:**
    *   Start the Vite development server.
    *   Observe the near-instantaneous startup time.

3.  **Hot Module Replacement (HMR):**
    *   Make changes to source code (e.g., `main.js`, `style.css`, `index.html`).
    *   Witness instant updates in the browser without a full page reload.

4.  **Building for Production:**
    *   Run the production build command.
    *   Examine the optimized output in the `dist` directory.

5.  **Serving the Production Build:**
    *   Serve the production build locally to verify its functionality.

## How to Follow Along

I will guide you step-by-step. After each explanation, I will prompt you to perform an action or observe a result. Please confirm when you are ready for the next step.
