FROM ubuntu:16.04

RUN apt-get -qq -y update && \
    apt-get -qq -y install openjdk-8-jdk maven gradle curl && \
    rm -rf /var/cache/apt /var/lib/apt/lists/*

WORKDIR /
ADD . .
RUN ./service.sh dev_build
CMD ["/service.sh", "dev_run", "/"]