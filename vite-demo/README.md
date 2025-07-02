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
        ├── main.jsx             // The primary JavaScript entry point for your code
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
      * Open the `src/main.jsx` file in your code editor.
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
        
    * change `h1` innerHTML in `main.jsx` to use the environment variable:
    
        ```html
        <h1>${import.meta.env.VITE_APP_TITLE}</h1>
        ```
        
    * See the changes applied.
   
      ```bash
      10:31:55 [vite] .env.development changed, restarting server...
      10:31:55 [vite] server restarted.
      11:23:36 [vite] (client) page reload src/main.jsx
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
    *   Update `src/main.jsx` to import `style.scss` instead of `style.css`.
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
    *   Update an import in `src/main.jsx` to use the alias:
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

9.  **Static Asset Handling:**
    *   Place an image (e.g., `image-public.svg`) in the `public/` folder.
    *   Open `index.html` and add an `<img>` tag for this image using an absolute path:
        ```html
        <img src="/image-public.svg" alt="Image from public folder" />
        ```
    *   Place another image (e.g., `image-src.svg`) in the `src/` folder.
    *   Open `src/main.jsx` and import this image, then add it to the DOM:
        ```javascript
        import imageSrc from '@/image-src.svg'; // Or './image-src.png' if no alias

        // Add this line somewhere in your innerHTML or create a new img tag
        document.querySelector('#app').innerHTML += `
          <img src="${imageSrc}" alt="Image from src folder" />
        `;
        ```
    *   Restart the development server.
        ```bash
        npm run dev
        ```
    *   **Expected Output:** Both images should be displayed in the browser. The image from `public/` will have a direct path, while the image from `src/` will have a hashed filename (e.g., `assets/image-src-xxxx.png`) in the browser's developer tools, indicating it was processed by Vite.

10. **Dynamic Imports (Lazy Loading):**
    *   Create a new file `src/lazy-module.js` with the following content:
        ```javascript
        export function showLazyMessage() {
          const messageDiv = document.createElement('div');
          messageDiv.textContent = 'This message was loaded dynamically!';
          messageDiv.style.cssText = 'margin-top: 20px; padding: 10px; background-color: #e0ffe0; border: 1px solid #a0ffa0;';
          document.querySelector('#app').appendChild(messageDiv);
          console.log('Dynamic module loaded!');
        }
        ```
    *   Modify `src/main.jsx` to add a button that, on click, will import and execute the function from this module:
        ```javascript
        // ... (your existing code) ...

        // Add this button to your innerHTML
        <button id="load-lazy-module">Load dynamic module</button>

        // Add this JavaScript code after the counter setup
        document.getElementById('load-lazy-module').addEventListener('click', async () => {
          const { showLazyMessage } = await import('./lazy-module.js');
          showLazyMessage();
        });
        ```
    *   **Expected Output:**
        1.  Open your browser's developer tools (F12), go to the "Network" tab.
        2.  Load the page. You should not see `lazy-module.js` loaded initially.
        3.  Click the "Load dynamic module" button.
        4.  The message should appear on the page, and in the "Network" tab, you will see a new JavaScript file (with a hashed name like `lazy-module-xxxx.js`) that has been loaded. This demonstrates that Vite automatically code-splits and loads the module on demand.

11. **TypeScript Integration:**
    *   Install TypeScript as a development dependency:
        ```bash
        npm install -D typescript
        ```
    *   Generate a `tsconfig.json` file:
        ```bash
        npx tsc --init
        ```
    *   Rename `src/counter.js` to `src/counter.ts`.
    *   Open `src/counter.ts` and add basic TypeScript type annotations (e.g., `: HTMLButtonElement`, `: number`).
        ```typescript
        export function setupCounter(element: HTMLButtonElement) {
          let counter = 0
          const setCounter = (count: number) => {
            counter = count
            element.innerHTML = `count is ${counter}`
          }
          element.addEventListener('click', () => setCounter(counter + 1))
          setCounter(0)
        }
        ```
    *   Update the import path for `counter.ts` in `src/main.jsx`:
        ```javascript
        // Before
        import { setupCounter } from './counter.js'
        // After
        import { setupCounter } from './counter.ts'
        ```
    *   Restart the development server (mandatory for `tsconfig.json` changes).
        ```bash
        npm run dev
        ```
    *   **Expected Output:** The application should continue to function normally, and the counter should work. Vite automatically detects and compiles TypeScript files. If you introduce a type error, Vite will show a compilation error in your terminal and browser overlay.

12. **Custom Vite Plugin (HTML Injection):**
    *   **Description:** This plugin demonstrates a more practical use case: injecting dynamic content into your `index.html` file. We will create a plugin that adds a footer with the build timestamp.
    *   Create a new file named `vite-plugin-build-info.js` in the root of your project with the following content:
        ```javascript
        // vite-plugin-build-info.js
        export default function buildInfoPlugin() {
          return {
            name: 'vite-plugin-build-info',
            // Use the transformIndexHtml hook to modify the final HTML
            transformIndexHtml(html) {
              const buildTime = new Date().toLocaleString();
              const footer = `
                <footer style="text-align: center; padding: 10px; font-size: 12px; color: #888;">
                  Built on: ${buildTime}
                </footer>
              `;
              // Inject the footer before the closing body tag
              return html.replace('</body>', `${footer}</body>`);
            }
          };
        }
        ```
    *   Update `vite.config.js` to use this new plugin. Remove the old banner plugin and any related configuration (like `terserOptions` if you added it).
        ```javascript
        import { defineConfig } from 'vite'
        import { resolve } from 'path'
        import buildInfoPlugin from './vite-plugin-build-info.js'

        export default defineConfig({
          plugins: [
            buildInfoPlugin(),
          ],
          resolve: {
            alias: {
              '@': resolve(__dirname, './src'),
            },
          },
        })
        ```
    *   Run the production build and preview it:
        ```bash
        npm run build
        npm run preview
        ```
    *   **Expected Output:**
        1.  Open the preview URL (e.g., `http://localhost:4173/`) in your browser.
        2.  At the bottom of the page, you should see a new footer with the text "Built on: [current date and time]", demonstrating that your plugin successfully injected content into the HTML.

13. **CSS Modules (Scoped Styles):**
    *   **Description:** To prevent style conflicts in larger applications, we will use CSS Modules. This Vite-native feature makes CSS class names locally scoped by default.
    *   **1. Rename the stylesheet:** In the `src/` directory, rename `style.scss` to `style.module.scss`. This naming convention tells Vite to process it as a CSS Module.
        ```bash
        mv src/style.scss src/style.module.scss
        ```
    *   **2. Isolate global styles:** Open `src/style.module.scss`. Styles for general tags (like `body`, `h1`, `a`, etc.) must be explicitly marked as global to continue applying to the whole page. Wrap them with the `:global()` pseudo-class. Local classes like `.card` or `.logo` should remain unchanged.
        ```scss
        /* src/style.module.scss */

        /* BEFORE */
        :root { /* ... */ }
        body { /* ... */ }

        /* AFTER */
                :global(:root) { /* ... */ }
        :global(body) { /* ... */ }
        :global(h1) { /* ... */ }
        :global(#app) { /* ... */ }
        /* ...and so on for other global selectors like a, button. */
        ```
    *   **3. Update JavaScript to use the styles object:** Modify `src/main.jsx` to import the styles as an object and use it to apply classes.
        *   First, change the import at the top of the file:
            ```javascript
            // BEFORE
            import './style.scss';

            // AFTER
            import styles from './style.module.scss';
            ```
        *   Next, update the `innerHTML` string to use the `styles` object:
            ```javascript
            // BEFORE
            // <img src="..." class="logo vanilla" ... />
            // <div class="card"> ... </div>

            // AFTER
            // <img src="..." class="${styles.logo} ${styles.vanilla}" ... />
            // <div class="${styles.card}"> ... </div>
            ```
    *   **4. Verify the result:** Restart the development server (`npm run dev`).
    *   **Expected Output:** The application should look identical. However, if you inspect an element in your browser's developer tools (e.g., the `logo` image), you will see that its class name has been transformed into a unique string (e.g., `_logo_a1b2c_`), proving that the style is now locally scoped and safe from conflicts.

14. **Global CSS Preprocessor Configuration:**
    *   **Description:** In larger projects, it's common to have global Sass variables, mixins, or functions that you want to use across multiple `.scss` files without explicitly importing them in each file. Vite allows you to configure this globally.
    *   **1. Create a global Sass file:** Create a new file, for example, `src/styles/_global.scss`, to house your global variables and mixins. It's crucial to include `sass:color` here if you plan to use its functions (like `color.adjust`) globally.
        ```scss
        /* src/styles/_global.scss */
        @use "sass:color"; // Import sass:color here to make its functions available globally

        $global-text-color: #333;

        @mixin flex-center {
          display: flex;
          justify-content: center;
          align-items: center;
        }
        ```
    *   **2. Configure Vite to inject global styles:** Open `vite.config.js` and add a `css.preprocessorOptions` configuration. This tells Vite to automatically inject the content of `_global.scss` and `sass:color` into every Sass file.
        ```javascript
        // vite.config.js
        ...
          resolve: {
            alias: {
              '@': resolve(__dirname, './src'),
            },
          },
          css: { // This block is crucial for preprocessor options
            preprocessorOptions: {
              scss: {
                // Inject both sass:color and _global.scss.
                // @use "sass:color" must come first if its functions are used globally.
                additionalData: `@use "sass:color"; @use "${resolve(__dirname, 'src/styles/_global.scss')}" as *;`
              }
            }
          }
        ...
        ```
    *   **3. Update `src/style.module.scss`:** Remove the direct `@use "sass:color";` as it's now globally injected. Also, apply the global variable and remove the `flex-center` mixin from `body` as it caused layout issues.
        ```scss
        /* src/style.module.scss */
        /* BEFORE */
        @use "sass:color"; // This line will be removed
        $primary-color: #42b883;

        :global(body) {
          margin: 0;
          min-width: 320px;
          min-height: 100vh;
          font-family: sans-serif;
          color: $global-text-color; // Using a global variable
        }

        /* AFTER */
        $primary-color: #42b883; // Keep this local variable

        :global(body) {
          margin: 0;
          min-width: 320px;
          min-height: 100vh;
          font-family: sans-serif;
          color: $global-text-color; // Using a global variable
          @include flex-center; 
        }
        ```
    *   **4. Use global styles without explicit imports:** Now, you can use `$global-text-color` directly in any `.scss` file (e.g., `src/style.module.scss`) without needing an `@import` or `@use` statement. The `flex-center` mixin is available but was removed from `body` due to layout.
    *   **5. Verify the result:** Restart the development server (`npm run dev`).
    *   **Expected Output:** The application should load correctly, and the styles defined in `_global.scss` (e.g., the `body` text color) should be applied. The layout should also be correct as `flex-center` was removed from `body`.

15. **Optimizing Build Output (Advanced):**
    * **Description:** This section delves deeper into optimizing your production build. We will explore manual chunking to control code splitting and integrate a bundle analyzer to visualize the size of your JavaScript modules and identify areas for further optimization.

    * **1. Install Bundle Analyzer:**
        * First, install `rollup-plugin-visualizer`, a powerful tool to visualize your bundle:
            ```bash
            npm install -D rollup-plugin-visualizer
            ```
        
    * **2. Add and use a vendor lib:**
        * Install uuid library to use as a vendor lib:
            ```bash
            npm install uuid
            ```
        * Open `src/main.jsx` and import the `uuid` library to ensure it is included in the build:
            ```javascript
            import imageSrc from '@/image-src.svg'
            import { v4 as uuidv4 } from 'uuid';
            ...
            <button id="load-lazy-module">Load dynamic module</button>
            <p style="margin-top: 10px; font-size: 0.9em; color: #666;">Generated UUID for demo: <strong>${uuidv4()}</strong></p>
            ...
            ```

    * **3. Configure Vite for Manual Chunking and Bundle Analysis:**
        * Open `vite.config.js` and add/modify the `build` configuration to include `rollupOptions` for manual chunking and integrate the `rollup-plugin-visualizer`. This will allow you to explicitly define how certain modules are grouped into separate output files.
           ```javascript
           ...
           import buildInfoPlugin from './vite-plugin-build-info.js'
           import { visualizer } from 'rollup-plugin-visualizer' // Import the visualizer plugin
           ...
             buildInfoPlugin(),
             // Add the visualizer plugin
             visualizer({
               filename: './stats.html', // Output file for the visualization
               open: false, // Do not open automatically, we'll open it manually
               gzipSize: true, // Show gzip size
               brotliSize: true, // Show brotli size
             }),
           ...
            build: {
              rollupOptions: {
                output: {
                // Manual chunking to separate specific modules into their own bundles
                manualChunks: {
                // Creates a 'vendor' chunk for commonly used libraries
                // We target node_modules to separate all third-party dependencies.
                vendor: ['react', 'react-dom', 'vue', 'vue-router', 'lodash', 'moment'], // Example: add common libraries used
                // Creates a 'lazy-module' chunk for our dynamically imported module
                // The lazy-module.js is already split by dynamic import, but this shows manual control
                'lazy-module': ['./src/lazy-module.js'],
                },
              },
            }
           ```

    * **4. Run and Analyze the Production Build:**
        * Stop the development server (`Ctrl-C`).
        * Run the production build command:
            ```bash
            npm run build
            ```
        * **Expected Output:
            ```bash
            vite v7.0.0 building for production...
            ✓ 31 modules transformed.
            dist/index.html                      0.75 kB │ gzip: 0.45 kB
            dist/assets/index-DOluJ5tD.css       1.52 kB │ gzip: 0.73 kB
            dist/assets/lazy-module-Tt88o4u_.js  0.32 kB │ gzip: 0.26 kB
            dist/assets/vendor-BKrj-4V8.js       0.89 kB │ gzip: 0.48 kB
            dist/assets/index-U8oNmSVS.js        4.65 kB │ gzip: 2.50 kB
            ✓ built in 712ms
            ```          

        * ** After the build, a new file named `stats.html` will be generated in your project root. This HTML file, when opened in a browser, will display an interactive treemap of your bundle, showing the size of each module. You should also observe new chunk files generated in `dist/assets/` due to manual chunking.

    * **5. Verify Chunking in `dist` Directory:**
        * List the contents of your `dist/assets` directory to confirm the new chunk files:
            ```bash
            ls -R dist/
            ```
        * **Expected Output:** You should see output similar to this, with `vendor` and `lazy-module` chunks:
            ```
            dist:
            assets  index.html  stats.html  vite.svg

            dist/assets:
            index-ABCDEFG.js     index-HIJKLMN.css  lazy-module-OPQRST.js  vendor-UVWXYZ.js
            ```
            (Note: Actual hashes will vary)

16. **Integration with a Framework (e.g., React):**
    * **Description:** Vite offers first-class support for various frontend frameworks through dedicated plugins. This section demonstrates how effortlessly Vite integrates with React, enabling you to build powerful applications with familiar tooling and Vite's rapid development experience, including Hot Module Replacement (HMR) for components.

        * **1. Install React and the Vite React Plugin:**
            * Stop your development server (`Ctrl-C`).
            * Install React, React DOM, and the official Vite React plugin as development dependencies:
                ```bash
                npm install react react-dom
                npm install -D @vitejs/plugin-react
                ```

        * **2. Configure Vite to Use the React Plugin:**
            * Open `vite.config.js` and import the `react` plugin, then add it to your `plugins` array.
                ```javascript
                import react from '@vitejs/plugin-react'

                export default defineConfig({
                  plugins: [
                    react(),
                    buildInfoPlugin(),
                    ...
                  ]
                })
                ```

        * **3. Create a Simple React Component:**
            * Create a new file `src/App.jsx` to house a basic React component (cf. [App.jsx](./src/App.jsx)).

        * **4. Rename `main.js` to `main.jsx` to Render the React App:**
            * Modify `src/main.jsx` to import and render your `App.jsx` component using React's client-side rendering API. cf. [main.jsx](./src/main.jsx).

        * **5. Observe React Integration in Development:**
            * Start the development server:
                ```bash
                npm run dev
                ```
            * **Expected Output:** Your browser should now display the content rendered by your React component.
            * **Test HMR:** Make a small text change inside `src/App.jsx` and save the file. Observe that the changes are instantly reflected in the browser without a full page reload, demonstrating Vite's HMR working with React components.