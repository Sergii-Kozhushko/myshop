#FROM openjdk:17
#COPY ./build/libs/myshop-bff-0.0.1.jar /app/bff.jar
#COPY ./docker-files/myshop_keycloak_ssl.cer /usr/local/share/ca-certificates/keycloak-server.cer
##RUN keytool -import -trustcacerts -file /usr/local/share/ca-certificates/keycloak-server.cer -alias keycloak-server -keystore $JAVA_HOME/lib/security/cacerts -noprompt -storepass changeit
#
#
#WORKDIR /app
#EXPOSE 8902
#EXPOSE 8901
#CMD ["java", "-jar", "bff.jar", "--spring.profiles.active=prod", "-Djavax.net.debug=ssl"]

FROM gradle:7.5.1-jdk17-alpine as build
WORKDIR /app
COPY --chown=gradle:gradle . /app

RUN gradle build --no-daemon


FROM openjdk:17

EXPOSE 8902
EXPOSE 8901


COPY --from=build /app/build/libs/myshop-bff-0.0.1.jar /app/app-bff.jar
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app/app-bff.jar", "--spring.profiles.active=prod"]



