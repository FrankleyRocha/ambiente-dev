FROM ubuntu:24.04
SHELL ["/bin/bash", "-c"]
RUN apt update
RUN apt install zip unzip build-essential curl git nano -y
RUN curl -s "https://get.sdkman.io" | bash
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" \
    && sdk install java 21.0.6-tem \
    && sdk install maven