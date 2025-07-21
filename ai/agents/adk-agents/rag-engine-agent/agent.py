# agent.py

import os

from google.adk.agents import Agent
from google.adk.tools.retrieval.vertex_ai_rag_retrieval import VertexAiRagRetrieval
from vertexai.preview import rag

from dotenv import load_dotenv
from .prompts import return_instructions_root

# Load environment variables from .env file
load_dotenv()

# --- RAG Engine Configuration ---
PROJECT_ID = os.getenv("GOOGLE_CLOUD_PROJECT")
REGION = os.getenv("VERTEX_AI_REGION")
RAG_CORPUS_ID = os.getenv("RAG_CORPUS_ID")
corpus_name = f"projects/{PROJECT_ID}/locations/{REGION}/ragCorpora/{RAG_CORPUS_ID}"

if not all([PROJECT_ID, REGION, RAG_CORPUS_ID]):
    raise ValueError("Environment variables GOOGLE_CLOUD_PROJECT, VERTEX_AI_REGION, and RAG_CORPUS_ID must be set.")

ask_dt_retrieval = VertexAiRagRetrieval(
    name="AskDTRAG",
    description="Use this tool to retrieve information about the Trusted Services Technical Direction",

    rag_resources=[
        rag.RagResource(
            rag_corpus=corpus_name
        )
    ],
    similarity_top_k=10,
    vector_distance_threshold=0.6,
)

# --- Agent Instantiation ---
print("\nDefining ADK Agent instance...")

root_agent = Agent(
    name="MyRAGAssistant",
    model="gemini-2.5-pro",
    description="AI assistant for the team. Can answer your questions about our missions, organization, and technical topics.",
    instruction=return_instructions_root(),
    tools=[ask_dt_retrieval]
)

print(f"Agent '{root_agent.name}' instance defined.")

# --- Main Execution Block (for direct script execution) ---
if __name__ == "__main__":
    print("Agent script executed directly. To use with the UI, run: 'adk web agents'")