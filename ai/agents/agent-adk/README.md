# Agent ADK

## Set up Environment & Install ADK

1. Set up Environment & Install ADK

```bash
# Create
python -m venv .venv
source .venv/bin/activate
```

2. Install ADK:

```bash
pip install google-adk
```

## Environment config

1. Create a .env file in the same folder:

```bash
GOOGLE_GENAI_USE_VERTEXAI=TRUE
GOOGLE_CLOUD_PROJECT=YOUR_PROJECT_ID
GOOGLE_CLOUD_LOCATION=LOCATION
```

## Run the Agent

1. Move to parent project

```bash
cd ..
```

2. Run ADK web

```bash
adk web
```
