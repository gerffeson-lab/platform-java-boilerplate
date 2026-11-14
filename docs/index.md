# Documentação do serviço

Documentação do serviço. Mantida no próprio repositório (docs-as-code).

## Rodar localmente

```bash
mvn spring-boot:run
curl "http://localhost:8080/v1/hello?name=Ana"
```

## Health checks

- Liveness: `/actuator/health/liveness`
- Readiness: `/actuator/health/readiness`
