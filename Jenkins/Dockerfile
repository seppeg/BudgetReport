FROM jenkins
USER root
ENV  JAVA_VERSION=9.0.4 \
     JDK9=/usr/lib/jvm/current-java

RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install -y  wget tar bash

RUN cd /tmp && \
    wget "http://download.java.net/java/GA/jdk9/${JAVA_VERSION}/binaries/openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" && \
    tar xzf "openjdk-${JAVA_VERSION}_linux-x64_bin.tar.gz" && \
    mkdir -p /usr/lib/jvm && mv "/tmp/jdk-${JAVA_VERSION}" "/usr/lib/jvm/openjdk-${JAVA_VERSION}"

RUN rm -rf $JDK9/*.txt && \
    rm -rf $JDK9/*.html && \
    rm -rf $JDK9/man && \
    rm -rf $JDK9/lib/src.zip && \
    rm -rf /tmp/*

RUN apt-get remove --purge wget -y && apt-get clean

USER jenkins

ENV JENKINS_OPTS --httpPort=10000
EXPOSE 10000