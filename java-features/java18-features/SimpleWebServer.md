---

### Simple Web Server (JEP 408)

1.  **Start the Web Server:**
    * Make sure you're in your project's root directory (where the `web_root` folder is located).
    * Run the following command:
        ```bash
        jwebserver -d "$(pwd)/web_root" -p 8000
        ```
        * `-d web_root`: Specifies the document root directory to serve.
        * `-p 8000`: Specifies the port the server will listen on (default is 8000).

2.  **Access the Server:**
    * Open your web browser and navigate to **`http://localhost:8000/`**.
    * You should see the `index.html` page you created.
    * In the terminal, you'll see the HTTP request logs.

3.  **Stop the Server:**
    * Go back to the terminal and press **`Ctrl+C`**.

This server is a simple yet very useful addition for Java developers.