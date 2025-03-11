# Docker via WSL

## Instalando o Docker no WSL

### 1. Habilitar
Para habilitar o WSL2 na sua máquina Windows, execute este comando no Powershell (execute como Administrador) . Certifique-se de reiniciar seu computador após a conclusão deste comando.

* Você pode pular esta etapa se já tiver instalado o WSL2 anteriormente.

```
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```

### 2. Instale o WSL2

Instalar Ubuntu-24.04. Forneça o usuário e senha quando solicitado.
```
wsl --install Ubuntu-24.04
```

Definir o Ubuntu-24.04 como distribuição padrão
```
wsl -s Ubuntu-24.04
```

* Para acessar o shell do Ubuntu, digite `wsl` no CMD ou Powershell.

### 3. Instale o Docker no Ubuntu

Use o comando abaixo para instalar o Docker no Ubuntu 24.04.
```bash
#!/bin/bash
# If your machine is behind corporate firewall,
# make sure to define your HTTP_PROXY and HTTPS_PROXY before running the command below

sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo -E curl --verbose -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo usermod -aG docker $USER
sudo systemctl enable docker.service
sudo systemctl enable containerd.service

sudo systemctl start docker.service
sudo systemctl start containerd.service
```

### 4. Configurando o git do WSL para integração ao git Windows

* Instale o git para windows [download git](https://git-scm.com/downloads)

No WSL execute:
```bash
git config --global user.name "Your Name"
```

```bash
git config --global user.email "your.email@address"
```

Caso o git windows esteja na pasta de seu usuario, execute no WSL:
```bash
/mnt/c/
git config --global credential.helper "/mnt/c/Users/$(cmd.exe /c echo %username% | tr -d '\r')/AppData/Local/Programs/Git/mingw64/bin/git-credential-manager.exe"
cd ~
```

Caso o git windows foi instalado globalmente, execute no WSL:
```bash
git config --global credential.helper "/mnt/c/Program\ Files/Git/mingw64/bin/git-credential-manager.exe"
```

### 5. Configurar PROXY [opcional]
```bash
#!/bin/bash
# If your machine is behind corporate firewall,
# make sure to define your HTTP_PROXY and HTTPS_PROXY before running the command below

sudo mkdir -p /etc/systemd/system/docker.service.d
sudo touch /etc/systemd/system/docker.service.d/http-proxy.conf
echo "[Service]" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf
echo "Environment='HTTP_PROXY=$HTTP_PROXY'" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf
echo "Environment='HTTPS_PROXY=$HTTPS_PROXY'" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf
echo "Environment='NO_PROXY=$NO_PROXY'" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf
echo "Environment='http_proxy=$http_proxy'" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf
echo "Environment='https_proxy=$https_proxy'" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf
echo "Environment='no_proxy=$no_proxy'" | sudo tee -a /etc/systemd/system/docker.service.d/http-proxy.conf

# restart docker daemon
sudo systemctl daemon-reload
sudo systemctl restart docker.service
sudo systemctl restart containerd.service
```

### 6. Exponha o daemon do docker ao Windows via porta 2375 [opcional]

Este comando permite que o daemon do Docker receba instruções da porta 2375. Isso também significa que podemos acessar a porta 2375 do Windows.
```bash
#!/bin/bash
sudo cp /lib/systemd/system/docker.service /etc/systemd/system/
sudo sed -i 's/\ -H\ fd:\/\//\ -H\ fd:\/\/\ -H\ tcp:\/\/127.0.0.1:2375/g' /etc/systemd/system/docker.service
sudo systemctl daemon-reload
sudo systemctl restart docker.service
```

## Integrar o cliente Docker (para Windows) com o daemon Docker no WSL [opcional]

### 7. Baixe o cliente Docker para WINDOWS
Baixe o cliente docker [desta url](https://download.docker.com/win/static/stable/x86_64/docker-28.0.1.zip) . No momento da escrita, a versão mais recente é docker-28.0.1.zip. Extraia o zip baixado e adicione a pasta à sua variável de ambiente PATH.

https://github.com/docker/compose/releases/download/v2.33.1/docker-compose-windows-x86_64.exe

* Verifique se tudo funciona digitando `docker` em um novo CMD ou Powershell.

### 8. Integrar o cliente Docker Windows com o Docker instalado no WSL [opcional]
```
# rode este commando no CMD ou Powershell

docker --version
docker context create lin --docker host=tcp://127.0.0.1:2375
docker context use lin
docker run hello-world
```

[Para mais detalhes, inclusive como configurar no DevPod, acesse a fonte deste tutorial em inglês](https://devpod.sh/docs/tutorials/docker-provider-via-wsl#integrate-devpod-with-docker-in-wsl)
