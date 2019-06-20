FROM maven:3.3-jdk-8 as builder
COPY . /usr/src/scrabble
WORKDIR /usr/src/scrabble
RUN mvn clean install -f /usr/src/scrabble && mkdir /usr/src/wars/
RUN find /usr/src/scrabble/ -iname '*.war' -exec cp {} /usr/src/wars/ \;

FROM tomcat:9.0-jre8-alpine
COPY --from=builder /usr/src/wars/* /usr/local/tomcat/webapps/
ENV env=prod