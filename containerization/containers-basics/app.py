# app.py
from flask import Flask
from redis import Redis
import os

app = Flask(__name__)
# Get Redis host from environment variable (Docker Compose/Kubernetes will provide this)
# REDIS_HOST will be 'redis' when using Docker Compose, or 'redis-service' with Kubernetes.
# If not set (e.g., local run without Docker), defaults to 'localhost'.
redis_host = os.getenv('REDIS_HOST', 'localhost')
redis_port = int(os.getenv('REDIS_PORT', 6379))
redis_db = int(os.getenv('REDIS_DB', 0))

# Initialize Redis client, with basic error handling for connection
try:
    # socket_connect_timeout: Prevents the app from hanging indefinitely if Redis is unreachable.
    redis_client = Redis(host=redis_host, port=redis_port, db=redis_db, socket_connect_timeout=1)
    redis_client.ping() # Test connection to ensure Redis is alive
except Exception as e:
    print(f"Failed to connect to Redis at {redis_host}:{redis_port}. Error: {e}")
    redis_client = None # Set to None if connection fails to handle cases where Redis is optional or down

@app.route('/hello')
def hello():
    if redis_client:
        try:
            count = redis_client.incr('visits') # Increment 'visits' counter in Redis
            return f"Hello from containerized application! This page has been visited {count} times."
        except Exception as e:
            # If Redis connection was initially fine but failed during interaction
            return f"Error interacting with Redis: {e}. Is Redis running and accessible?", 500
    else:
        # Fallback message if Redis connection failed during initialization
        return "Hello from containerized application! (Redis not connected)", 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) # Run Flask app on all interfaces (0.0.0.0) on port 5000