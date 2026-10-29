# Parsa-Agent 🤖

Agente de IA pessoal desenvolvido sob medida, operando inicialmente via Terminal (CLI), com persistência em PostgreSQL e integração nativa com o Obsidian Vault para gestão de conhecimento em Markdown.

## 🛠️ Stack Tecnológica
* **Java 21**
* **Spring Boot 3.4+**
* **LangChain4j**
* **PostgreSQL 16** + Flyway (Migrações)
* **Obsidian Vault** (Armazenamento de Notas em Markdown)

---

## ⚙️ Pré-requisitos
* JDK 21 instalado na máquina.
* Docker e Docker Compose (para subir o PostgreSQL).
* IntelliJ IDEA (com plugin / suporte nativo a `.env`).

---

## 🚀 Como Executar o Projeto

1. **Subir o Banco de Dados:**
```bash
docker compose up -d
```

2. Configurar as Variáveis de Ambiente:
Crie um arquivo .env na raiz do projeto baseado no .env.example.

3. Executar a Aplicação:
Abra o projeto no IntelliJ IDEA e execute a classe ParsaAgentApplication.