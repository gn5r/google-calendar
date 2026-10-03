#!/bin/bash

sudo chown -R vscode:vscode ~/.gemini
curl -fsSL --compressed https://antigravity.google/cli/install.sh | bash
npm install -g context-mode

if [ ! -e GEMINI.md ]; then
  cp "$(npm root -g)/context-mode/configs/antigravity/GEMINI.md" ./
fi

if [ -e .agents/mcp_config.json ]; then
  mkdir -p ~/.gemini/config

  if [ -e ~/.gemini/config/mcp_config.json ]; then
    cp ~/.gemini/config/mcp_config.json ~/.gemini/config/_mcp_config.json
  fi

  if [ ! -s ~/.gemini/config/mcp_config.json ]; then
    echo '{"mcpServers":{}}' > ~/.gemini/config/mcp_config.json
  fi

  jq -s '.[0] * .[1]' ~/.gemini/config/mcp_config.json .agents/mcp_config.json > ~/.gemini/config/mcp_config.json.tmp && \
    mv ~/.gemini/config/mcp_config.json.tmp ~/.gemini/config/mcp_config.json 
fi