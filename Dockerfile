FROM ubuntu:24.04
SHELL ["/bin/bash", "-ic"]

RUN apt update

#pacotes essenciais
RUN apt install -y \
    vim \
    wget \
    curl \
    git \
    sudo \
    build-essential \
    zip unzip

#necessario para o python
RUN apt install -y \
    zlib1g-dev \
    libncurses5-dev \
    libgdbm-dev \
    libnss3-dev \
    libssl-dev \
    libreadline-dev \
    libffi-dev \
    libbz2-dev \
    libsqlite3-dev \
    liblzma-dev \
	python3-tk \
    tk-dev

#opcional docker
#RUN apt install -y \
#    docker.io \
#    docker-compose

#opcional fontes microsoft
#RUN apt install -y \
#    ttf-mscorefonts-installer

#opcional necessario para executar eclipse
#RUN apt install -y \
#    libswt-gtk-4-java

# add the user
RUN useradd --create-home toolbox -s /bin/bash
RUN echo "toolbox:toolbox" | chpasswd
RUN usermod -aG sudo toolbox
#RUN usermod -aG docker toolbox

USER toolbox
WORKDIR /home/toolbox
RUN mkdir ~/projects

RUN curl -s "https://get.sdkman.io" | bash

RUN curl https://pyenv.run | bash
RUN echo 'export PYENV_ROOT="$HOME/.pyenv"' >> ~/.bashrc
RUN echo 'command -v pyenv >/dev/null || export PATH="$PYENV_ROOT/bin:$PATH"' >> ~/.bashrc
RUN echo 'eval "$(pyenv init -)"' >> ~/.bashrc

RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash

RUN sdk install java 21.0.6-tem
RUN sdk install maven

RUN pyenv install 3.13
RUN pyenv global 3

RUN nvm install 22

RUN npm install -g @angular/cli
RUN npm i -g @ionic/cli