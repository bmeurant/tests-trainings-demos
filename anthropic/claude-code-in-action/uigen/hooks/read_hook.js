async function main() {
    const chunks = [];
    for await (const chunk of process.stdin) {
        chunks.push(chunk);
    }

    const input = Buffer.concat(chunks).toString();
    const toolArgs = JSON.parse(input);

    // Extract the file path Claude is trying to read
    const readPath =
        toolArgs.tool_input?.file_path || toolArgs.tool_input?.path || "";

    // Check if Claude is trying to read the .env file
    if (readPath.includes('.env')) {
        console.error("You cannot read the .env file");
        process.exit(2);
    }
    
}

main().catch(err => {
    console.error("[ERROR] Hook failed:", err);
    process.exit(1);
});