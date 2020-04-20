# Instruções para executar a Dockerfile

1. É necessário adicionar o repositório do Ubuntu na maquina, para isso rodamos o seguinte comando:

`docker build -t="dockerfile/ubuntu" github.com/dockerfile/ubuntu`

2. Depois que a download da imagem do Ubuntu finalizar, precisamos baixar a imagem do Java 8, com o seguinte comando:

`docker build -t="dockerfile/java:oracle-java8" github.com/dockerfile/java`


É importante manter a ordem dos comandos.

Assim que finalizar o Java, basta executar o comando de Build com a Dockerfile