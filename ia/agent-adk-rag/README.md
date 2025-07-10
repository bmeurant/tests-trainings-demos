# Setting Up the Development Environment

## 1. Python and Pip Installation (if not already installed)

On Ubuntu:

```bash
sudo apt update
sudo apt install python3 python3-pip
```

Check versions:

```bash
python3 --version
pip3 --version
```

Expected output:

```bash
Python 3.12.3
pip 24.0 from /usr/lib/python3/dist-packages/pip (python 3.12)
```

## 2. Google Cloud SDK Installation

The Google Cloud SDK allows us to interact with Google Cloud services from your terminal.

For Ubuntu:

```bash
sudo apt-get install apt-transport-https ca-certificates gnupg curl
echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] https://packages.cloud.google.com/apt cloud-sdk main" | sudo tee -a /etc/apt/sources.list.d/google-cloud-sdk.list
curl -fsSL https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo gpg --dearmor -o /usr/share/keyrings/cloud.google.gpg
sudo apt-get update
sudo apt-get install google-cloud-sdk
```

After installation, initialize the SDK:

```bash
gcloud init
```

Follow the prompts; it will ask to log in to our Google account and choose a project. Enter a project ID or create a 
new one.

## 4. Enabling Necessary APIs

We will need the Vertex AI API.

Check if the API is enabled:

```bash
gcloud services list --filter="name:aiplatform.googleapis.com AND state:ENABLED"
```

Or activate it:

```bash
gcloud services enable aiplatform.googleapis.com
```

Expected output:

```bash
NAME                       TITLE
aiplatform.googleapis.com  Vertex AI API
```

## 5. Authenticating the environment

For Python code to interact with Google Cloud, we need to authenticate. For local development, the simplest method is 
to use Application Default Credentials (ADC), which pick up the credentials configured with `gcloud init`.

Ensure you are authenticated:

```bash
gcloud auth application-default login
```

This will open a browser page to authenticate with our Google account. Once authenticated, our local environment will 
be able to use these credentials to call Google Cloud APIs.

## 6. Creating a Virtual Environment

It's always recommended to use a virtual environment to manage Python project's dependencies.

```bash
sudo apt install python3.12-venv
python3 -m venv .venv
source .venv/bin/activate
```

Expected output: (venv) appear before the prompt, indicating that the virtual environment is activated.

## 7. Installing Dependencies

We'll need the Google ADK and the Google Cloud Python SDK.

```bash
pip install google-generativeai google-cloud-aiplatform
```

**Note**: google-generativeai is for Gemini models, and google-cloud-aiplatform is the general SDK for Vertex AI.
The ADK (Agent Development Kit) isn't a separate Python library to install via pip; instead, it's a set of concepts and 
structures for building agents that rely on these Google Cloud libraries.

## 8. Write the agent code

## 9. Running the Agent

Before executing, ensure export **PROJECT_ID** and **GCP_REGION** as environment variables and they are used in the 
code.:

```bash
export GCP_PROJECT_ID="YOUR_PROJECT_ID" # Replace with the actual project ID
export GCP_REGION="us-central1" # or the preferred region for Vertex AI models
```

```python
PROJECT_ID = os.environ.get("GCP_PROJECT_ID")
REGION = os.environ.get("GCP_REGION")
```

Run the agent code:

```bash
python3 simple_agent.py
```