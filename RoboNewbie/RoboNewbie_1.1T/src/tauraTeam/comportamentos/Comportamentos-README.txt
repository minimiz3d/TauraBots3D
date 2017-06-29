Lista de classes:

1. Role.java
    Classe abstrata que define o comportamento de cada robô. Possui o método 'decide()' que deve ser implementado individualmente por cada comportamento.
    Uso com uma nova classe de comportamento:
        import tauraTeam.comportamentos.Role;
        public class NovoComportamento extends Role {}

2. SoccerTeamThinking.java
    Com base no número do robô, é instanciada alguma classe de comportamento.
    Por exemplo: Num = 1 -> Role = Goleiro -> SoccerTeamThinking: GoleiroComportamento roleGoleiro = new GoleiroComportamento(parametros)

3. Comportamentos em si:
    3.1. GoleiroComportamento
    3.2. DefensorComportamento
    3.3. VolantaoComportamento
    3.4. MeiaComportamento
    3.5. AtacanteComportamento
