# Dockerfile

# --- Build Stage ---
# This stage is responsible for installing dependencies and potentially compiling assets.
# It uses a full Python development image to ensure all build tools are available.
FROM python:3.9 AS builder

# Set environment variables for non-interactive commands
ENV PYTHONUNBUFFERED 1

# Set the working directory for this stage
WORKDIR /app

# Copy only the requirements file first. This optimizes Docker's layer caching:
# if requirements.txt doesn't change, pip install step won't re-run on subsequent builds.
COPY requirements.txt ./

# Install Python dependencies.
# --no-cache-dir: Reduces image size by not storing pip's cache.
# --upgrade pip: Ensures pip itself is up-to-date.
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# --- Production Stage ---
# This stage uses a much smaller base image, only containing what's necessary to run the app.
# It copies artifacts (installed dependencies) from the 'builder' stage.
FROM python:3.9-slim-buster AS production

# Set environment variables for the application
ENV PYTHONUNBUFFERED 1
# Ensure common binaries are in PATH if stripped in slim versions
ENV PATH="/usr/bin:${PATH}"

# Set the working directory for the final application
WORKDIR /app

# Copy installed Python packages from the builder stage.
# This is the magic of multi-stage builds: we copy only the compiled/installed artifacts.
# /usr/local/lib/python3.9/site-packages is where pip installs packages in this base image.
COPY --from=builder /usr/local/lib/python3.9/site-packages /usr/local/lib/python3.9/site-packages

# Copy the application code.
COPY main.py .

# Expose the port on which the FastAPI application will listen.
EXPOSE 8000

# Define the command to run the application when the container starts.
# uvicorn is the ASGI server for FastAPI.
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]