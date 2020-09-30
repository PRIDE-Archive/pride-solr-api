# Build stage
FROM maven:3.3.9-jdk-8-alpine AS build-env

# Create app directory
WORKDIR /app

COPY src ./src
COPY pom.xml ./
RUN mvn clean package -DskipTests -DjarFinalName=${JAR_FILE_NAME}

ENV USER=docker
ENV UID=${NFS_UID}
ENV GID=${NFS_GID}
RUN addgroup --gid "$GID" "$USER" \
   && adduser \
   --disabled-password \
   --gecos "" \
   --home "$(pwd)" \
   --ingroup "$USER" \
   --no-create-home \
   --uid "$UID" \
   "$USER"

RUN addgroup -g ${NFS_GID2}  group2
RUN addgroup $USER group2

# Package stage
FROM openjdk:8-alpine3.9
WORKDIR /app
COPY --from=build-env /app/target/${JAR_FILE_NAME}.jar ./

ENTRYPOINT java ${JAVA_OPTS} -jar ${JAR_FILE_NAME}.jar