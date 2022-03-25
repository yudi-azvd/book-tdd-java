# Sobre os testes de integração

## MongoDB

**_Se_ tudo estivesse dando certo, você faria assim**:

Se você tiver Docker instalado, execute

    docker run --name tdd-java-mongo -d mongo:3.0.4

E espera um pouco. Quando o download terminar, provavelmente o MongoDB já vai estar
de pé localmente. Cheque com `docker ps`. Se não estiver, suba-o com

    docker start tdd-java-mongo

Para interrompê-lo:

    docker stop tdd-java-mongo

## Testes

Para executar os testes de integração (`**/*Integ.java`) rode no terminal

    gradle testInteg

Para esse último você precisa do MongoDB rodando em seu computador.

## Quando começou a dar errado

Não consegui fazer o teste de integração do capítulo 6 passar. Imagino que de 
2015 pra hoje, as versões do Jongo, MongoDB Driver e MongoDB tenham mudado tanto
que não são mais compatíveis.

Tentei várias algumas do MongoDB: 3.x, 2.2, 2.8 e as mais atuais. Nada deu certo.

No livro, a versão do `mongo-java-driver` é 2.+. A versão atual na documentação 
é a 5.4.1. É de se esperar que algumas coisas quebrem.

Ainda assim, posso ter errado em alguma coisa. Se souber de alguma coisa entra 
em contato por yudiyamane@gmail.com.

## Solução que eu tô pensando agora

Pra fazer esses testes de integração darem certo eu provavelmente teria que 
atualizar a dependência `mongo-java-driver` para a mais recente e descartar
`jongo`. E nos testes eu usaria MongoCollection do `mongo-java-driver`.
