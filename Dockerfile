# Imagem base Red Hat (UBI). Na plataforma real, trocar pela imagem curada pela plataforma.
# O jar é construído pela esteira (mvn verify) e só copiado aqui: build uma vez, promove a mesma imagem.
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime:latest

COPY target/*.jar /deployments/app.jar

EXPOSE 8080
USER 185
