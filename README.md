# Serviço Java / Spring Boot

Criado a partir do golden path Java/Spring: um serviço Spring Boot mínimo que já nasce com
contrato OpenAPI, esteira de CI e imagem de container.

O template não carrega a identidade do serviço. O time dono está no `CODEOWNERS` (gravado na
criação) e o nome em runtime (logs, traces, Kubernetes) vem do deploy, a partir do nome do
repositório. Sigla, produto e time ficam nas custom properties do repositório.

> No repositório template (`platform-java-boilerplate`): não crie serviços clonando à mão.
> Peça um novo serviço em `platform-self-service`.

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
| Deploy | `deploy/values.yaml` (+ `values-<dev\|hml\|prd>.yaml`), contrato do chart `app` da plataforma |
| Documentação | `docs/` + `mkdocs.yml` |

## Desenvolvimento

1. Altere o contrato em `api/openapi.yaml`.
2. `mvn compile` gera as interfaces em `target/generated-sources`.
3. Implemente a interface gerada (veja `HelloController`).

```bash
mvn verify
mvn spring-boot:run
```

## Deploy

Push em `develop`, `release/*` ou `main` publica a imagem e grava os manifestos no repo
GitOps; o Argo CD implanta em dev, hml ou prd. O time só ajusta `deploy/values*.yaml`
(recursos, réplicas, variáveis, probes, Route).

## Fluxo de branches (GitFlow)

`feature/*` → PR para `develop` → `release/*` → `main` + tag `vX.Y.Z`.
Escreva `AB#<id>` no PR para ligar ao work item do Azure Boards.
