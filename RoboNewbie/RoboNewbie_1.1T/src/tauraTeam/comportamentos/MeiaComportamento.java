package tauraTeam.comportamentos;

import agentIO.PerceptorInput;
import keyframeMotion.KeyframeMotion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import util.Logger;
import tauraTeam.comportamentos.Role;
// TODO: Usar o role num pacote acessível.

public class MeiaComportamento extends Role {
    // Construtor
    public MeiaComportamento(KeyframeMotion motion, PerceptorInput percIn, Logger log) {
        super(motion, percIn, log);
    }

    // Tomada de decisão
    @Override
    public void decide() {
        // teste
        effOut.setSayMessage("IniciandoComportamento");
        log.log("IniciandoComportamento");

        if (motion.ready()) {
            log.log("Tomando nova decisão.");
            double serverTime = percIn.getServerTime();

            // Se estiver no chão, tem que levantar...
            if (estaNoChao(percIn) == true) {
                if (percIn.getAcc().getY() > 0)
                    motion.setStandUpFromBack();
                else
                    motion.setRollOverToBack();
            }

            // Caso a bola tenha sido vista há pouco...
            else if ((serverTime - ball.getTimeStamp() < lookTime)) {
                Vector3D ballCoords = ball.getCoords();


            }

            // Não está no chão, e também não possui as coordenadas da bola...
            else {}

            // Ao final do ciclo atual, envia-se as pecepções para o capitão
        //    enviarInfo();
        }
    }

    // TODO: Inserir em algum pacote de utilidades.
    private boolean estaNoChao(PerceptorInput percIn) {
        if (percIn.getAcc().getZ() < 2) return true;

        return false;
    }

    // TODO: Implementar método
    private void enviarInfo() {
        // TODO: Formatar informações dos perceptors e enviar ao coordenador
        effOut.setSayMessage("TesteCoord");
    }

}
