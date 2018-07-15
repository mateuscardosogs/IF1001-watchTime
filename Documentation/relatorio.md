# Projeto: watchTime

## Equipe:
- Mateus Cardoso: Desenvolvedor
- Marcílio Freitas: Desenvolvedor

## Objetivo
Desenvolver uma aplicação mobile para ajudar os usuários a organizar filmes, séries, animes e vídeos, de forma que sempre saibam em que episódio pararam ou até mesmo em qual tempo do vídeo pausaram.

## Páginas Criadas (Activitys)
- Splashscreen
- Tela de login
- Tela de cadastro
- Menu principal
- Tela de listagem (filtrada por categorias ou todos)
- Tela de cadastro de anotação

## Banco de Dados e Sistema de Autenticação Utilizados
- [Firebase] (https://firebase.google.com/)

Dentre os Bancos de Dados analisados, este foi o mais simples em relação a implementação no nosso ponto de vista e já tínhamos utilizado em outro projeto.

## Funcionalidades Implementadas
- Activitys
- Content Provider
- Broadcast Receiver

## Layouts Utilizados
- DrawerLayout
- RelativeLayout
- LinearLayout
 - ScrollView
 - CardView
 - DatePicker
 - FloatingActionButton
 - SwipeMenuListView

# Features Implementadas
- Sistema de Login e Cadastro
- Listagem de Anotações com Filtros por Categoria
- Ordenação das Listagens
- Cadastro de Anotações
- Identificação das Anotações por Usuário
- Sistema de Notificação para o Usuário (Deadline)
- Speech Recognition

# Descrição da Implementação

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Iniciamos o projeto criando a activity inicial que se tornou a splashcreen, com isso iniciamos o sistema de autenticação pelo firebase, configurando o ambiente para se comunicar o mesmo, e logo após, iniciamos a divisão das funcionalidades em pastas, onde foi criado primeiro o DAO (Data Access Object) para comunicação com o firebase, e os Helpers de Preferences e Base64Custom. Depois criamos a activity de login e alteramos a identidade visual da mesma, após isso seguimos para a activity de registro do usuário onde criamos uma entidade Usuários que possuíam os campos de email, password, name, lastname, birthday e gender.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Depois foi criada a activity Main, que possuía os menus de seleção (cards), eles foram divididos por categorias (filmes, séries, animes) e a opção do cadastro de uma nova anotação. Logo após foi iniciado o processo de transferência e leitura dos dados para o database do firebase, onde criamos nosso banco no sistema e adicionamos as regras iniciais para leitura e escrita.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Também criamos a activity Content onde listava todas as anotações cadastradas que eram jogadas num array, baseado no filtro selecionado no Menu Principal, onde é possível compartilhar a anotação através de outros aplicativos que permitem o compartilhamento (ex: Whatsapp, Twitter, etc.) por meio do swape menu, podendo ser compartilhado também no calendário, adicionando um lembrete do deadline da anotação, além de excluir ou editar a anotação. 


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
A activity CreateAnnotation foi criada logo após a main, onde nela era feito o preenchimento dos campos (title, content, category, priority e deadline) para serem jogados no banco, para persistência de dados. Também foi criado um Broadcastreceiver para assim ter a criação dos eventos de notificação baseado no ID do device do usuário toda vez que atingisse o dia da deadline cadastrado da anotação. Criamos um CustomAdapter que preenche as linhas personalizadas da ListView com o ArrayList criado para a activity Content.
