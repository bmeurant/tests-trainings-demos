# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

UIGen is an AI-powered React component generator built with Next.js 15, React 19, and TypeScript. It provides a chat-based interface where users can describe components and see them generated in real-time with live preview capabilities.

## Development Commands

```bash
# Setup (first time)
npm run setup                # Install deps, generate Prisma client, run migrations

# Development
npm run dev                  # Start Next.js dev server with Turbopack
npm run dev:daemon          # Start dev server in background, logs to logs.txt

# Testing & Quality
npm test                    # Run Vitest tests
npm run lint               # Run ESLint
npm run build              # Production build
npm start                  # Start production server

# Database
npm run db:reset           # Reset database and run migrations
npx prisma generate        # Generate Prisma client
npx prisma migrate dev     # Run database migrations
```

## Architecture

### Core Components
- **Virtual File System**: In-memory file system (`src/lib/file-system.ts`) that simulates disk operations without writing to actual filesystem
- **Chat Interface**: AI-powered component generation using Anthropic Claude via Vercel AI SDK
- **Live Preview**: Real-time component rendering using iframe sandboxing
- **Project Persistence**: SQLite database with Prisma ORM for authenticated users

### Key Contexts
- `FileSystemProvider`: Manages virtual file operations, tool calls, and file state
- `ChatProvider`: Handles chat messages, streaming responses, and project persistence

### AI Integration
- Uses Anthropic Claude for component generation via `/api/chat/route.ts`
- Two main tools available to AI:
  - `str_replace_editor`: Create/edit files with string replacement
  - `file_manager`: Rename/delete files and directories
- Supports both authenticated (with API key) and mock modes

### File Structure Patterns
- UI components use shadcn/ui conventions with Radix UI primitives
- React components follow functional component patterns with TypeScript
- Tests use Vitest with Testing Library
- Database models: User and Project with JSON fields for messages/data

### Authentication
- JWT-based auth with bcrypt password hashing
- Anonymous users can use the tool without persistence
- Authenticated users get project persistence and history

### Preview System
- Components are rendered in isolated iframe
- Supports JSX transformation via Babel standalone
- File changes trigger automatic preview updates

## Testing

Tests are located in `__tests__` directories within feature folders. Run individual test files:
```bash
npm test -- file-system.test.ts
```

## Database

Uses SQLite with Prisma. The Prisma client is generated to `src/generated/prisma/` to avoid conflicts. Schema includes User and Project models with JSON fields for flexible data storage.

The database schema is defined in the @prisma/schema.prisma file. Reference it anytime you need to understand the structure of data stored in the database.

### Development instructions

- Use comments sparingly. Only comment complex code.