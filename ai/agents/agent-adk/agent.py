# agent.py

from google.adk import Agent

# --- Agent Definition ---
class SimpleVertexAIAgent(Agent):
    """
    A simple AI agent demonstrating basic text generation using Vertex AI,
    integrated with the Google Agent Development Kit (ADK) UI.
    """
    def __init__(self, name: str, model_name: str = "gemini-pro", description: str = "", instruction: str = ""):
        super().__init__(
            name=name,
            model=model_name,
            description=description,
            instruction=instruction,
            tools=[],
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
    name="MyGeneralPurposeAI",
    model_name="gemini-2.5-pro",
    description="An AI assistant capable of answering a wide range of general knowledge questions.",
    instruction=(
        "You are a highly knowledgeable and friendly AI assistant. "
        "Always provide clear and accurate answers. "
        "Be concise but informative. If a question is beyond your current capabilities or knowledge, "
        "admit it gracefully."
    )
)

print(f"Agent '{root_agent.name}' instance defined.")

# --- Main Execution Block (for direct script execution) ---
if __name__ == "__main__":
    print("Agent script executed directly. To use with the UI, run: 'adk web agents'")
