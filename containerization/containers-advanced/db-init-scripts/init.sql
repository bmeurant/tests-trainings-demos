-- db-init-scripts/init.sql
-- This script runs automatically when the PostgreSQL container starts for the first time.
-- It ensures our 'tasks' table exists.

CREATE TABLE IF NOT EXISTS tasks (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE
    );

-- Optional: Insert some initial data
INSERT INTO tasks (title, description, completed) VALUES
                                                      ('Learn Docker Compose Advanced', 'Deep dive into volumes and networks', TRUE),
                                                      ('Master Multi-stage Builds', 'Reduce image size significantly', FALSE)
    ON CONFLICT (id) DO NOTHING; -- Avoid re-inserting if table recreated with data