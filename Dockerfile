FROM openjdk:15
WORKDIR /app
COPY target/back-0.0.1-SNAPSHOT.jar /app
ENV PAR_API_URL=http://127.0.0.1:5000/predict
ENV WAV_TARGET=/home/alan/Bureau
ENV MYSQL_DRIVER=com.mysql.jdbc.Driver
ENV MYSQL_URL=jdbc:mysql://localhost:3306/parkinson?allowPublicKeyRetrieval\=true&useSSL\=false
ENV MYSQL_PASS=password
ENV MYSQL_USR=admin
ENTRYPOINT ["java","-jar","back-0.0.1-SNAPSHOT.jar"]
