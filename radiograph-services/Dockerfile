FROM java:8
VOLUME /c
ADD target/radiograph-service-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]