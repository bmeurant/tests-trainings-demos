# simple_agent.py

import os
import vertexai
from vertexai.generative_models import GenerativeModel, Part

# --- Configuration ---
# Replace with your actual Google Cloud Project ID
PROJECT_ID = os.environ.get("GCP_PROJECT_ID")
REGION = os.environ.get("GCP_REGION")

# Initialize Vertex AI
# This assumes that 'gcloud auth application-default login' has been run
vertexai.init(project=PROJECT_ID, location=REGION)

# --- Agent Logic ---
def create_simple_agent():
    """
    Creates a simple generative AI model instance for the agent.
    """
    # Using a suitable model for general text generation
    model = GenerativeModel("gemini-2.5-pro")
    return model

def ask_agent(agent_model, question: str) -> str:
    """
    Asks the agent a question and gets a response.

    Args:
        agent_model: The initialized GenerativeModel instance.
        question: The question to ask.

    Returns:
        The agent's response.
    """
    print(f"Asking agent: {question}")
    try:
        # Generate content from the model
        resp = agent_model.generate_content(question)
        # Access the text from the response
        # The 'text' attribute directly gives the generated text
        # print(response) # Uncomment to see the full response object
        return resp.text
    except Exception as e:
        print(f"An error occurred: {e}")
        return "Sorry, I couldn't process your request."

# --- Main Execution ---
if __name__ == "__main__":
    # Check if the project ID is set
    if PROJECT_ID is None:
        print("Error: Google Cloud Project ID (GCP_PROJECT_ID) environment variable is not set.")
        print("Please set the GCP_PROJECT_ID environment variable.")
        exit(1)

    # Ensure your project ID is set, either directly or via environment variable
    if PROJECT_ID == "YOUR_PROJECT_ID":
        print("WARNING: Please set your Google Cloud Project ID in simple_agent.py or as an environment variable GCP_PROJECT_ID.")
        print("Example: export GCP_PROJECT_ID='your-project-id'")
        exit(1)

    print(f"Initializing agent for project: {PROJECT_ID} in region: {REGION}")
    agent = create_simple_agent()

    print("\nHello! I am a simple AI agent. Ask me anything (type 'quit' to exit).")
    while True:
        user_input = input("You: ")
        if user_input.lower() == 'quit':
            print("Goodbye!")
            break

        response = ask_agent(agent, user_input)
        print(f"Agent: {response}")