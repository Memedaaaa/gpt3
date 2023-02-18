FROM openjdk:11

MAINTAINER Your Name zhangzimingmmz 

RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY target/gpt3-0.0.1-SNAPSHOT.jar /opt/app

CMD ["nohup","java", "-jar", "/opt/app/gpt3-0.0.1-SNAPSHOT.jar","> output 2>&1 &"]

EXPOSE 8000

ENTRYPOINT ["nohup", "java", "-jar", "/opt/app/gpt3-0.0.1-SNAPSHOT.jar","> output 2>&1 &"]

