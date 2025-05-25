## ✅ Guia Completo: Instalando o WSL2 com Ubuntu 24.04 no Windows

Este tutorial ensina como instalar o **WSL2 (Windows Subsystem for Linux)** com a distribuição **Ubuntu 24.04** no Windows 10 ou 11, de forma segura e atualizada.

---

### 🔧 Requisitos

* Windows 10 (versão 2004 ou superior) ou **Windows 11**
* Acesso de **administrador**
* Conexão com a Internet

1. **Passos que exigem conta de administrador (logado como administrador)**
2. **Passos que podem ser feitos com um usuário comum (sem elevação de privilégio)**

---

## 🧱 Pré-requisito importante

> 💡 **Logue-se em uma conta com privilégios de administrador no Windows.**
> Não basta clicar com o botão direito e "Executar como administrador" se sua conta for limitada.
> Isso evita falhas silenciosas e erros ao instalar componentes do sistema como o kernel do WSL2.

---

## 🔐 PARTE 1 — Passos que exigem conta de **Administrador**

Todos os comandos abaixo devem ser executados em um **terminal CMD ou PowerShell como administrador**, e estando **logado em uma conta com privilégios de administrador**.

### 🔹 1) Instale o WSL (subsistema Linux) e seus componentes básicos

```cmd
wsl --install --no-distribution
```

* Instala o WSL e componentes de base, sem baixar uma distribuição automaticamente.
* Pode solicitar reinicialização.

---

### 🔹 2) Atualize o kernel e componentes para garantir o uso do WSL2

```cmd
wsl --update
```

* Garante que você terá o WSL2 com o kernel Linux mais recente.

---

> ✅ Após essa etapa, o ambiente WSL estará pronto para receber distribuições Linux.

---

## 👤 PARTE 2 — Passos que podem ser feitos como **usuário comum**

Estes comandos não exigem elevação de privilégio e podem ser executados em terminais comuns (CMD, PowerShell ou Windows Terminal).

### 🔹 3) Instale a distribuição Ubuntu 24.04

```cmd
wsl --install -d Ubuntu-24.04
```

* Baixa e instala a distribuição `Ubuntu 24.04` LTS.
* Se não encontrar, execute `wsl --list --online` para verificar o nome correto.

---

### 🔹 4) Verifique se a versão usada é o WSL2

```cmd
wsl --list --verbose
```

* Mostra as distribuições e suas versões de WSL (1 ou 2).

**Exemplo de saída esperada:**

```
  NAME            STATE           VERSION
* Ubuntu-24.04    Running         2
```

> Se aparecer versão 1, converta com:

```cmd
wsl --set-version Ubuntu-24.04 2
```

---

### 🔹 5) Acesse o Ubuntu pela primeira vez

```cmd
wsl
```

* Isso iniciará a distribuição padrão (Ubuntu 24.04).
* Na primeira vez, será solicitado que você:

  * Defina um **nome de usuário Linux**
  * Crie uma **senha**

---

## ✅ Resumo das permissões necessárias

| Etapa                   | Terminal       | Privilégio necessário                        |
| ----------------------- | -------------- | -------------------------------------------- |
| Instalar WSL            | CMD/PowerShell | 🟢 Conta admin + Executar como administrador |
| Atualizar WSL (kernel)  | CMD/PowerShell | 🟢 Conta admin + Executar como administrador |
| Instalar Ubuntu         | CMD/PowerShell | ⚪️ Usuário comum                             |
| Verificar versão do WSL | CMD/PowerShell | ⚪️ Usuário comum                             |
| Acessar o WSL           | CMD/PowerShell | ⚪️ Usuário comum                             |

