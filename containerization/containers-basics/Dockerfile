# Dockerfile
# Use a lightweight official Python image as a base
FROM python:3.9-slim-buster

# Set the working directory inside the container
WORKDIR /app

# Copy the requirements file into the container
COPY requirements.txt .

# Install the Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the application code into the container
COPY app.py .

# Expose the port that the application will listen on
EXPOSE 5000

# Define the command to run the application when the container starts
CMD ["python", "app.py"]