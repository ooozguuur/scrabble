FROM maven:3.3-jdk-8 as builder
ENV HOME=/usr/src/scrabble
RUN mkdir -p $HOME
WORKDIR $HOME
RUN  n ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]
ADD . $HOME
RUN ["mvn", "package"]

FROM tomcat:9.0-jre8-alpine
COPY --from=builder /usr/src/scrabble/* /usr/local/tomcat/webapps/
ENV env=prod