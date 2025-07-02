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
    
        ```bash
        npm run dev &
        ```

    *   Observe the near-instantaneous startup time.

        **Expected output:**
    
        ```bash
        VITE v7.0.0  ready in 132 ms

          ➜  Local:   http://localhost:5173/
          ➜  Network: use --host to expose
          ➜  press h + enter to show help
        ```
        
    *   Open the [provided URL](http://localhost:5173/) in your web browser to view the application

3.  **Hot Module Replacement (HMR):**
    * **HTML**
      * Open the `src/main.js` file in your code editor.
      * Locate the line that modifies the `<h1>` text (e.g., `document.querySelector('#app').innerHTML = ...`).
      * Change the text, for example, from "Vite + JavaScript" to "Vite + HMR in action!".
      * Save the file.
      * Witness instant updates in the browser without a full page reload.
      
    * **CSS**
      * Open the `style.css` file.
      * Change the h1 color to `red`: `color: red;`
      * Save the file.
      * Observe that the background color updates immediately in the browser.

    * **CSS**
      * Open the `counter.js` file.
      * Change the count message from `counter is` to `counter:`
      * Save the file.
      * Observe that the background color updates immediately in the browser.

4.  **Building for Production:**
    * Stop server with `Ctrl-C`.
    * Run the production build command.
    
      ```bash
      npm run build
      ```
      
      **Expected output:**

      ```bash
      > vite-demo@0.0.0 build
      > vite build
    
      vite v7.0.0 building for production...
      ✓ 7 modules transformed.
      dist/index.html                 0.45 kB │ gzip: 0.29 kB
      dist/assets/index-CrYBktwj.css  1.20 kB │ gzip: 0.62 kB
      dist/assets/index-C74yQL5S.js   2.59 kB │ gzip: 1.41 kB
      ✓ built in 115ms
      ```
      
    * Examine the optimized output in the `dist` directory.

     ```bash
     ls -R dist/
     ```
    
     **Expected output:**
    
     ```bash
     dist:
     assets  index.html  vite.svg
    
     dist/assets:
     index-C74yQL5S.js  index-CrYBktwj.css
     ```

     HTML, CSS, JavaScript files, and assets (SVG) are now minified and optimized. The JavaScript and CSS filenames include hashes (e.g., `-C74yQL5S.js`, `-CrYBktwj.css`) to enable efficient cache busting upon deployment.

5.  **Serving the Production Build:**
    *   Serve the production build locally to verify its functionality.
        
        ```bash
        npm run preview
        ```
        
        **Expected output:**
    
        ```bash
        > vite-demo@0.0.0 preview
        > vite preview
    
          ➜  Local:   http://localhost:4173/
          ➜  Network: use --host to expose
          ➜  press h + enter to show help
        ```
6. **Environment Variables:**
    * Stop the server with `Ctrl-C` and run `npm run dev` to restart the development server.
    * Create a `.env.development` file in the root of your project.
    
        ```bash
        echo "VITE_APP_TITLE=My Vite App in Dev" > .env.development
        ```
        
    * change `h1` innerHTML in `main.js` to use the environment variable:
    
        ```html
        <h1>${import.meta.env.VITE_APP_TITLE}</h1>
        ```
        
    * See the changes applied.
   
      ```bash
      10:31:55 [vite] .env.development changed, restarting server...
      10:31:55 [vite] server restarted.
      11:23:36 [vite] (client) page reload src/main.js
      ```

    * Stop the server with `Ctrl-C`
    * Create a `.env.production` file in the root of your project.

      ```bash
      echo "VITE_APP_TITLE=My Vite App in Prod" > .env.production
      ```

   * Run and launch the production.

     ```bash
     npm run build
     ```

7.  **CSS Preprocessors (Sass):**
    *   Install Sass as a development dependency.
        ```bash
        npm install -D sass
        ```
    *   Rename `src/style.css` to `src/style.scss`.
    *   Update `src/main.js` to import `style.scss` instead of `style.css`.
        ```javascript
        // Before
        import './style.css'
        // After
        import './style.scss'
        ```
    *   Add some Sass code to `src/style.scss` (e.g., variables, nesting).
        ```scss
        @use "sass:color";
        
        $primary-color: #42b883;

        body {
          font-family: sans-serif;
        }

        .card {
          button {
            background-color: $primary-color;
            &:hover {
              background-color: color.adjust($primary-color, 10%);
            }
          }
        }
        ```
    *   Restart the development server.
        ```bash
        npm run dev
        ```
    *   **Expected Output:** The application should reload, and the styles defined in Sass (e.g., button background color) should be applied, demonstrating Vite's native Sass compilation.

8.  **Path Aliases:**
    *   **Create `vite.config.js` (if it doesn't exist) and add `resolve` configuration (or ensure it matches the content above):**
        ```javascript
        // vite.config.js
        import { defineConfig } from 'vite'
        import { resolve } from 'path'

        export default defineConfig({
          plugins: [],
          resolve: {
            alias: {
              '@': resolve(__dirname, './src'),
            },
          },
        })
        ```
    *   Update an import in `src/main.js` to use the alias:
        ```javascript
        // Before
        import javascriptLogo from './javascript.svg'
        // After
        import javascriptLogo from '@/javascript.svg'
        ```
    *   Restart the development server (mandatory).
        ```bash
        npm run dev
        ```
    *   **Expected Output:** The application should load without errors, indicating that Vite successfully resolved the path using the alias.