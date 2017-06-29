Nesse pacote ficam os arquivos responsáveis pelos testes dos comportamentos e do time.

Lista de classes:

1. TesteComportamentos.java:
    Classe executável que executa todo o time ou jogadores individualmente.
    Para executar todo o time, deve-se alterar o id para zero:
        final String id = "0";
    Caso executar algum jogador em específico, apenas utilizar o número.
        final String id = "5"; Executar um dos volantes do time.

2. trainer/Agent_Trainer.java:
    Para executar o testes com jogadores únicos, usa-se o trainer, que simplesmente movimenta a bola sem a necessidade de algum jogador interferir.
