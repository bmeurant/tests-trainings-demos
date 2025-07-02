export default function mockApiPlugin() {
  return {
    name: 'mock-api',
    configureServer(server) {
      server.middlewares.use('/api/data', (req, res) => {
        console.log('Intercepting /api/data request via custom plugin...');
        const mockData = {message: 'Hello from mocked API (via custom plugin)!'};
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(mockData));
      });
    },
  };
}