FROM openjdk

WORKDIR /home/app

COPY ./target/scala*/*-assembly*.jar ./app.jar

CMD java -jar app.jar