# TypeScript Tutorial

## Prerequisites

**Ubuntu** environment and **IntelliJ IDEA** as IDE.

### 1. Node.js and npm

**Verification:** Open your terminal and run:

```bash
node -v
npm -v
```

If you see version numbers, you're good to go. If not, install Node.js via nvm:

```bash
# Download and install nvm
curl -o- [https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh](https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh) | bash

# Load nvm into your current shell session
# You might need to close and reopen your terminal or source your shell config file (.bashrc, .zshrc, etc.)
source ~/.bashrc # Or ~/.zshrc if you use zsh

# Install the latest stable Node.js version
nvm install node

# Use the newly installed Node.js version
nvm use node
```

### 2. IntelliJ IDEA

Install IntelliJ IDEA

## Project Initialization and TypeScript Setup

### 1. Initialize `package.json`

In your terminal, navigate to your project directory and run:

```bash
npm init -y
```

The -y flag answers "yes" to all default questions, creating a basic package.json file.

### 2. Install TypeScript

```bash
npm install -D typescript
```

The -D (or --save-dev) flag indicates that typescript is a development dependency, meaning it's required for compiling the code but not for running the application in production.

### 3. Install Node Types

```bash
npm install -D @types/node
``` 

This installs the type definitions for Node.js, allowing TypeScript to understand Node.js-specific features and APIs.

### 4. Compiling & Building TypeScript

```bash
npx tsc
```

### 4. Run scripts

```bash
node dist/main.js
```

### 5. Configure watch mode (Optional)

To automatically compile TypeScript files when they change, you can run in another terminal (from `typescript-tutorial` directory):

```bash
npx tsc --watch
```