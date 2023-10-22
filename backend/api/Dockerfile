FROM amazoncorretto:17.0.9

ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD

ENV DB_URL $DB_URL
ENV DB_USER $DB_URL
ENV DB_PASSWORD $DB_PASSWORD

WORKDIR /build
COPY . .
RUN ./gradlew backend:api:bootJar

FROM amazoncorretto:17.0.9

RUN yum update -y \
    && yum install -y shadow-utils \
    && yum clean all

RUN mkdir -p /app/jar

RUN groupadd --system javauser && useradd -r -s /bin/false -g javauser javauser

COPY --from=0 /build/backend/api/build/libs/api.jar /app/jar/api.jar
COPY --from=0 /build/schema.graphql /schema.graphql

WORKDIR /app

RUN chmod -R 755 /app
USER javauser

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Djava/awt.headless=true", "-XX:+UseG1GC", "-Xms2g", "-Xmx2g", "-XX:-TieredCompilation", "-jar", "/app/api.jar"]
