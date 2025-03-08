# Docker via WSL 

## Instalando o Docker no WSL

### 1. Habilitar 
Para habilitar o WSL2 na sua máquina Windows, execute este comando no Powershell (execute como Administrador) . Certifique-se de reiniciar seu computador após a conclusão deste comando.

Você pode pular esta etapa se já tiver instalado o WSL2 anteriormente.

```
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```

### 2. Instale o WSL2 

Instalar Ubuntu-24.04. Forneça o usuário e senha quando solicitado.
```
wsl --install Ubuntu-24.04
```
Para acessar o shell do Ubuntu, digite `wsl` no CMD ou Powershell.

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

### 4. [opcional] Configurar PROXY
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

### 5. Exponha o daemon do docker ao Windows via porta 2375

Este comando permite que o daemon do Docker receba instruções da porta 2375. Isso também significa que podemos acessar a porta 2375do Windows.
```bash
#!/bin/bash
sudo cp /lib/systemd/system/docker.service /etc/systemd/system/
sudo sed -i 's/\ -H\ fd:\/\//\ -H\ fd:\/\/\ -H\ tcp:\/\/127.0.0.1:2375/g' /etc/systemd/system/docker.service
sudo systemctl daemon-reload
sudo systemctl restart docker.service
```

## Integrar o cliente Docker (para Windows) com o daemon Docker no WSL

### 6. Baixe o cliente Docker para WINDOWS
Baixe o cliente docker [desta url](https://download.docker.com/win/static/stable/x86_64/docker-28.0.1.zip) . No momento da escrita, a versão mais recente é docker-28.0.1.zip. Extraia o zip baixado e adicione a pasta à sua variável de ambiente PATH.

Verifique se tudo funciona digitando `docker` em um novo CMD ou Powershell.

### 7. Integrar o cliente Docker Windows com o Docker instalado no WSL
```
# run this command in Powershell

docker --version
docker context create lin --docker host=tcp://127.0.0.1:2375
docker context use lin
docker run hello-world
```

[Para mais detalhes referente, inclusive como configurar no DevPod, acesse a fonte](https://devpod.sh/docs/tutorials/docker-provider-via-wsl#2-install-wsl2-distro)