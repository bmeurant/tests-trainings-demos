// vite-plugin-build-info.js
export default function buildInfoPlugin() {
  return {
    name: 'vite-plugin-build-info',
    // This hook is specifically for transforming the main index.html file
    transformIndexHtml(html) {
      const buildTime = new Date().toLocaleString();
      const footer = `
        <footer style="text-align: center; padding: 10px; font-size: 12px; color: #888;">
          Built on: ${buildTime}
        </footer>
      `;

      // Inject the footer right before the closing body tag
      return html.replace('</body>', `${footer}</body>`);
    }
  };
}
