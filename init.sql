-- Database initialization script for Docker
-- This script will be executed when PostgreSQL container starts
-- The database is already created by POSTGRES_DB environment variable

-- Set timezone
SET timezone = 'UTC';

-- You can add any initial SQL setup here
-- For example, creating additional users, extensions, or initial data

-- Log successful initialization
\echo 'Database initialized successfully for Email Sender application';