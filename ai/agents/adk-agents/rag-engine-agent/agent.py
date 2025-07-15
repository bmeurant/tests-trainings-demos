# agent.py

import os
from dotenv import load_dotenv
import vertexai
from vertexai.preview import rag
from google.adk import Agent

# Load environment variables from .env file
load_dotenv(dotenv_path=os.path.join(os.path.dirname(__file__), '.env'))

# --- RAG Engine Configuration ---
PROJECT_ID = os.getenv("GOOGLE_CLOUD_PROJECT")
REGION = os.getenv("VERTEX_AI_REGION")
RAG_CORPUS_ID = os.getenv("RAG_CORPUS_ID")

if not all([PROJECT_ID, REGION, RAG_CORPUS_ID]):
    raise ValueError("Environment variables GOOGLE_CLOUD_PROJECT, VERTEX_AI_REGION, and RAG_CORPUS_ID must be set.")

# Initialize Vertex AI
vertexai.init(project=PROJECT_ID, location=REGION)

# --- Tool Definitions ---

def search_rag_corpus(query: str) -> str:
    """
    Searches the Vertex AI RAG Corpus for factual information on various topics.
    Returns relevant content if found, otherwise indicates no information.

    Args:
        query: The search term or topic to look for in the RAG Corpus.
    """
    print(f"\n--- Tool Call: search_rag_corpus with query: '{query}' ---")
    try:
        # Construct the full resource name for the RAG Corpus
        corpus_resource_name = f"projects/{PROJECT_ID}/locations/{REGION}/ragCorpora/{RAG_CORPUS_ID}"

        # Create the resource config
        rag_resource = rag.RagResource(rag_corpus=corpus_resource_name)

        # Configure retrieval parameters (using default values for now)
        retrieval_config = rag.RagRetrievalConfig(
            top_k=10,  # Default top_k
            filter=rag.utils.resources.Filter(vector_distance_threshold=0.5) # Default vector_distance_threshold
        )

        # Execute the query directly using the API
        rag_response = rag.retrieval_query(
            rag_resources=[rag_resource],
            text=query,
            rag_retrieval_config=retrieval_config
        )

        # Process the results
        results = []
        if hasattr(rag_response, "contexts"):
            contexts = rag_response.contexts
            if hasattr(contexts, "contexts"): # Handle nested contexts if they exist
                contexts = contexts.contexts
            
            for context in contexts:
                result_text = context.text if hasattr(context, "text") else ""
                results.append(result_text)

        if results:
            full_content = "\n\n".join(results)
            print(f"--- Tool Result: Found {len(results)} results. ---")
            return full_content
        else:
            print("--- Tool Result: No relevant information found in RAG Corpus. ---")
            return "No relevant information found in the knowledge base."
    except Exception as e:
        print(f"An error occurred during RAG Corpus search: {e}")
        return f"An error occurred while searching the knowledge base: {e}"

# --- Agent Definition ---
class SimpleVertexAIAgent(Agent):
    """
    A simple AI agent demonstrating basic text generation using Vertex AI,
    integrated with the Google Agent Development Kit (ADK) UI.
    """
    def __init__(self, name: str, model_name: str = "gemini-pro", description: str = "", instruction: str = "", tools: list = []):
        super().__init__(
            name=name,
            model=model_name,
            description=description,
            instruction=instruction,
            tools=tools,
        )
        print(f"Agent '{self.name}' core logic initialized with model: {model_name}.")

    def handle_message(self, message: str) -> str:
        print(f"\n--- User Message Received by ADK Agent ({self.name})---\n{message}")

        try:
            agent_response = self.llm.generate_content(message).text
            print(f"Model generated response: {agent_response}")
            return agent_response
        except Exception as e:
            print(f"An error occurred during content generation: {e}")
            return "Sorry, I encountered an issue. Please try again."


# --- Agent Instantiation ---
print("\nDefining ADK Agent instance...")

root_agent = SimpleVertexAIAgent(
    name="MyRAGAssistant",
    model_name="gemini-2.5-pro",
    description="Hello! I am a helpful AI assistant. I can answer your questions on a variety of topics. How can I help you?",
    instruction=(
        "You are a helpful AI assistant. "
        "Your primary role is to answer questions on a wide range of subjects. "
        "You have access to a `search_rag_corpus` tool to find specific factual information from a specialized knowledge base (Vertex AI RAG Engine). "
        "**Always try to use the `search_rag_corpus` tool if the user's question requires factual information.** "
        "Formulate your search query concisely for the tool. "
        "If the tool provides relevant information, use it to answer the user's question, integrating the information smoothly. "
        "If the tool finds no relevant information for a specialized topic, state that you don't have specific information on that exact topic in your knowledge base. "
        "For general questions not requiring specialized facts (e.g., asking for a joke), answer based on your broad general knowledge without using the tool. "
        "Be concise but informative."
    ),
    tools=[search_rag_corpus]
)

print(f"Agent '{root_agent.name}' instance defined.")

# --- Main Execution Block (for direct script execution) ---
if __name__ == "__main__":
    print("Agent script executed directly. To use with the UI, run: 'adk web agents'")