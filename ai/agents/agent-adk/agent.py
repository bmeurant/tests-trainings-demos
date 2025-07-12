# agent.py

from google.adk import Agent

# --- Simulated Knowledge Base ---
# In a real application, this would be a database, vector store, or external API.
knowledge_base = [
    {"id": 1, "topic": "Python", "content": "Python is a high-level, interpreted programming language known for its readability and versatility. It's widely used for web development, data analysis, AI, and automation."},
    {"id": 2, "topic": "Java", "content": "Java is a popular, class-based, object-oriented programming language designed to have as few implementation dependencies as possible. It's commonly used for enterprise-level applications, Android development, and big data."},
    {"id": 3, "topic": "Spring Framework", "content": "Spring Framework is an open-source application framework for the Java platform. It provides comprehensive infrastructure support for developing robust Java applications, notably for enterprise-level projects (Spring Boot, Spring MVC)."},
    {"id": 4, "topic": "REST API", "content": "A REST (Representational State Transfer) API is an architectural style for an API that uses HTTP requests to access and use data. It's stateless, meaning each request from client to server contains all the information needed to understand the request."},
    {"id": 5, "topic": "Google Cloud Platform (GCP)", "content": "Google Cloud Platform is a suite of cloud computing services that runs on the same infrastructure that Google uses internally for its end-user products, such as Google Search and YouTube. It offers services for computing, storage, networking, big data, machine learning, and IoT."},
    {"id": 6, "topic": "Gemini Model", "content": "Gemini is a family of multimodal large language models developed by Google AI. They are designed to understand and operate across different types of information, including text, code, audio, image, and video."},
    {"id": 7, "topic": "RAG (Retrieval Augmented Generation)", "content": "RAG is a technique that enhances the capabilities of large language models (LLMs) by allowing them to retrieve facts from an external knowledge base and use those facts to ground their responses. This helps to reduce hallucinations and provide more accurate, up-to-date information."},
    {"id": 8, "topic": "IntelliJ IDEA", "content": "IntelliJ IDEA is an integrated development environment (IDE) developed by JetBrains. It is primarily designed for Java, but also supports a wide range of other programming languages and frameworks, offering advanced code analysis and developer tools."},
    {"id": 9, "topic": "Ubuntu", "content": "Ubuntu is a popular open-source operating system based on Debian Linux. It's widely used for personal computers, servers, and cloud computing, known for its user-friendliness and strong community support."},
]

# --- Tool Definitions ---

def search_knowledge_base(query: str) -> str:
    """
    Searches the internal knowledge base for factual information on programming languages, frameworks,
    cloud platforms, and related technical concepts.
    Returns relevant content if found, otherwise indicates no information.

    Args:
        query: The search term or topic to look for in the knowledge base (e.g., 'Python', 'Spring Framework', 'GCP').
    """
    print(f"\n--- Tool Call: search_knowledge_base with query: '{query}' ---")
    results = []
    # Simple keyword search for demonstration purposes
    for item in knowledge_base:
        if query.lower() in item["topic"].lower() or query.lower() in item["content"].lower():
            results.append(item["content"])

    if results:
        # Join multiple results for context, or pick the most relevant one
        # For simplicity, we'll return all found content.
        # In a real RAG system, results would be ranked and truncated.
        response = "\n".join(results)
        print(f"--- Tool Result: Found {len(results)} results. ---")
        return response
    else:
        print("--- Tool Result: No relevant information found. ---")
        return "No relevant information found in the knowledge base."

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
    description="An AI assistant capable of answering a wide range of general knowledge questions.",
    instruction=(
        "You are a helpful and knowledgeable AI assistant. "
        "You have access to a `search_knowledge_base` tool to find specific factual information about **programming languages, frameworks (like Java, Python, Spring), cloud platforms (like Google Cloud Platform), and related technical concepts (like REST APIs, RAG, IDEs, operating systems).**. "
        "**Always try to use the `search_knowledge_base` tool if the user's question is about programming, cloud computing, or technical concepts that might be in a specialized knowledge base.** "
        "Formulate your search query concisely for the tool."
        "If the tool provides relevant information, use it to answer the user's question, citing the source implicitly by integrating the information smoothly. "
        "If the tool finds no relevant information for a specialized topic, state that you don't have specific information on that exact topic in your knowledge base. "
        "For general questions not requiring specialized facts (e.g., asking for a joke), answer based on your broad general knowledge without using the tool. "
        "Be concise but informative."
    ),
    tools=[search_knowledge_base]
)

print(f"Agent '{root_agent.name}' instance defined.")

# --- Main Execution Block (for direct script execution) ---
if __name__ == "__main__":
    print("Agent script executed directly. To use with the UI, run: 'adk web agents'")