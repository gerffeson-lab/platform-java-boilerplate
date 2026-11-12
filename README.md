# platform-java-boilerplate

Walking skeleton do golden path: um serviço Spring Boot mínimo que já nasce com contrato
OpenAPI, esteira de CI, imagem de container e metadados de catálogo.

> Repositório **template**. Não crie serviços clonando à mão: peça um novo serviço em
> `platform-self-service` e o repositório é criado automaticamente.

## Contrato do repositório

É o acordo entre o boilerplate (Arquitetura) e a esteira (Plataforma). A esteira assume que:

| Item | Onde / como |
|---|---|
| Contrato da API | `api/openapi.yaml` (OpenAPI 3, rotas versionadas `/v1/...`) |
| Regras de lint do contrato | Padrão da plataforma, versionado na esteira. Um `redocly.yaml` na raiz sobrescreve (exceção, não regra) |
| Build e testes | `mvn verify` (Java 21) gera um único jar em `target/` |
| Imagem | `Dockerfile` na raiz, copia `target/*.jar` |
| Porta | `8080` |
| Health checks | `/actuator/health/liveness` e `/actuator/health/readiness` |
| Logs | JSON (ECS) no stdout |
| Catálogo | `catalog-info.yaml` (pronto para um IDP futuro) |
| Documentação | `docs/` + `mkdocs.yml` |

## Desenvolvimento

1. Altere o contrato em `api/openapi.yaml`.
2. `mvn compile` gera as interfaces em `target/generated-sources`.
3. Implemente a interface gerada (veja `HelloController`).

```bash
mvn verify
mvn spring-boot:run
```

## Fluxo de branches (GitFlow)

`feature/*` → PR para `develop` → `release/*` → `main` + tag `vX.Y.Z`.
Escreva `AB#<id>` no PR para ligar ao work item do Azure Boards.
