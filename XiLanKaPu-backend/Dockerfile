FROM openjdk:8-jdk-alpine
LABEL authors="AjaxZhan"

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY xilankapu.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]