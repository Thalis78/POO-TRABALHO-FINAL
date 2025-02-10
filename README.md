# Rede Social

Este repositório contém a implementação de uma rede social em Java, utilizando **Jackson** para persistência de dados em JSON. O sistema permite a criação de perfis, envio e aceitação de pedidos de amizade, publicações e interações entre usuários.

---

## Estrutura do Projeto

O projeto segue a seguinte organização:

```
/Controllers   -> Classes que controlam a lógica principal do sistema
/Models        -> Classes que representam os objetos do sistema (Perfis, Publicações, etc.)
/Services      -> Classe responsável pela persistência dos dados
/Main.java     -> Classe principal para executar a aplicação
/pom.xml       -> Configuração do Maven para gerenciamento de dependências
```

---

## Funcionalidades

- ✅ Criar perfis com apelido, email, emoji e status (ativo/inativo)
- ✅ Adicionar amigos e enviar pedidos de amizade
- ✅ Aceitar ou recusar solicitações de amizade
- ✅ Criar publicações e permitir interações
- ✅ Persistência dos dados em **JSON** para manter informações entre execuções
- ✅ Simulação de um **perfil logado**, permitindo que o usuário realize ações como se estivesse em uma rede social real

---

## Tecnologias Utilizadas

- **Java**
- **Maven** para gerenciamento de dependências
- **Jackson** para serialização/desserialização de JSON
- **Coleções Java** (`ArrayList`) para manipulação de listas de perfis, amizades e publicações

---

## Persistência de Dados

Os dados da rede social são armazenados no arquivo **`sistema_rede_social.json`**, utilizando **Jackson** para converter os objetos Java para JSON e vice-versa.

### Serialização e Desserialização

- **Serialização:** Converte objetos Java para JSON e os salva em um arquivo.
- **Desserialização:** Lê o arquivo JSON e recria os objetos Java na memória.

### Como os dados são armazenados?

- Perfis são salvos como objetos JSON
- Amigos são armazenados como **apelidos** (evitando referências cíclicas)
- Publicações possuem referência ao apelido do autor
- Pedidos de amizade possuem referência ao apelido do solicitante e solicitado
- O JSON também salva **o array de interações de cada publicação**, permitindo registrar curtidas e reações

### Exemplo de JSON salvo:

```json
{
  "publicacao": [
    {
      "id": 1,
      "perfilAssociado": "Thalisson",
      "dataPublicacao": "2025-02-09T17:09:46.957+00:00",
      "conteudo": "Foto no Trabalho",
      "Interacoes": [
        {
          "id": 1,
          "perfilAutor": "Thalisson",
          "tipo": "NAOCURTIR"
        },
        {
          "id": 2,
          "perfilAutor": "Natiele",
          "tipo": "SURPRESA"
        }
      ]
    }
  ],
  "perfil": [
    {
      "apelido": "Ely",
      "id": 1,
      "status": true,
      "email": "ely@gmail.com",
      "emoji": "\uD83D\uDE04",
      "amigos": []
    },
    {
      "apelido": "Thalisson",
      "id": 2,
      "status": true,
      "email": "thalisson@gmail.com",
      "emoji": "\uD83D\uDE00",
      "amigos": []
    }
  ],
  "pedidosDeAmizade": [
    {
      "perfilSolicitante": "Thalisson",
      "perfilSolicitado": "Ely"
    }
  ]
}
```

---

## Como Executar o Projeto

1. Clone este repositório:

   ```sh
   git clone https://github.com/Thalis78/POO-TRABALHO-FINAL.git
   ```

2. Abra o projeto em sua IDE favorita (Eclipse, IntelliJ, VS Code...)

3. Certifique-se de ter o Java 23 instalado:
   Este projeto foi desenvolvido para a versão Java 23. Para verificar sua versão, execute o seguinte comando no terminal:

   ```sh
    java -version
   ```

4. Instale as dependências com o Maven:

   Certifique-se de que o **Maven** está configurado corretamente na sua IDE. Para instalar as dependências do projeto, execute:

   ```sh
   mvn clean install


   ```

5. Execute a classe Main.java para iniciar o sistema.

6. O sistema criará automaticamente o arquivo **`sistema_rede_social.json`** caso ele não exista.

---

## Link Video Demostrativo

<a href="https://youtu.be/pexVjD5l8l0">
  <img src="https://img.icons8.com/?size=100&id=108794&format=png&color=000000" alt="Video Demonstrativo" width="50" />
</a><br>
Clique no ícone acima para assistir ao vídeo demonstrativo.

---

## Autores

| **Projeto** | **Autores**                       |
| ----------- | --------------------------------- |
| Feito por   | Lincoln Matheus e Thalisson Moura |
