# Generic RAG Agent

This directory contains a generic agent that uses a RAG (Retrieval-Augmented Generation) system built on **Google Cloud's Vertex AI RAG Engine**.

## Configuration

Before running the agent, you need to configure your Google Cloud environment.

1.  **Create a `.env` file** in this directory (`rag-engine-agent/.env`).
2.  **Populate the `.env` file** with your GCP project details.

### .env File Content

Copy the following content into your `.env` file and replace the placeholder values with your actual configuration:

```bash
# GOOGLE_GENAI_USE_VERTEXAI: Set to TRUE to use Vertex AI for RAG.
GOOGLE_GENAI_USE_VERTEXAI=TRUE
# GOOGLE_CLOUD_PROJECT: Your Google Cloud project ID.
# Example: "my-gcp-project-123"
GOOGLE_CLOUD_PROJECT="your-gcp-project-id"

# VERTEX_AI_REGION: The GCP region where your Vertex AI resources are located.
# Example: "us-central1"
VERTEX_AI_REGION="your-gcp-region"

# RAG_CORPUS_ID: The numeric ID of your Vertex AI RAG Corpus.
# You can find this ID in Vertex AI Rag Engine after creating your corpus.
# Example: "1234567890123456789"
RAG_CORPUS_ID="your-rag-corpus-id"
```

## Dependencies

This agent requires Python dependencies listed in the `requirements.txt` file.

You can install them using pip:

```bash
pip install -r requirements.txt
```

---

## How it Works

The `agent.py` script is configured to use the Vertex AI RAG Engine as its knowledge base.

1.  **Environment Variables**: The agent loads `GOOGLE_CLOUD_PROJECT`, `VERTEX_AI_REGION`, and `RAG_CORPUS_ID` from the `.env` file.
2.  **Vertex AI Initialization**: The Vertex AI SDK is initialized using your project and region.
3.  **RAG Tool**: The agent defines a tool that queries your specified RAG Corpus.
4.  **LLM Integration**: When the agent receives a user query, it uses the RAG tool to retrieve relevant information from your knowledge base.
5.  **Grounded Responses**: The retrieved information is then used by the Large Language Model (LLM) to generate a factually grounded and accurate response.

This setup ensures that the agent's responses are augmented with up-to-date and specific information from your custom knowledge base, reducing hallucinations and improving accuracy.