FROM bellsoft/liberica-openjdk-alpine:latest
COPY ./java ./src
RUN mkdir ./out
RUN javac -sourcepath ./src -d out src/ru/geekbrains/lesson1/sample/Main.java
CMD java -classpath ./out ru.geekbrains.lesson1.sample.Main