/**
 * *****************************************************************************
 * RoboNewbie NaoTeam Humboldt
 *
 * @author Hans-Dieter Burkhard, 11.2.2015
 * @version 1.1
 *
 * *****************************************************************************
 */


package tauraTeam.comportamentos;

import agentIO.PerceptorInput;
import keyframeMotion.KeyframeMotion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import util.Logger;

/**
    * This class implements the decisions of a primitive goal keeper.
    * The Goalie walks to the ball if it becomes near.
    * TODO: Voltar pro gol. POR ENQUANTO: No return to the goal.
*/
public class GoleiroComportamento extends Role {
    public GoleiroComportamento(KeyframeMotion motion, PerceptorInput percIn, Logger log) {
        super(motion, percIn, log);
    }

    @Override
    public void decide() {
        double TOLLERATED_DEVIATION = Math.toRadians(6);
        double LIMIT_FOR_BALL = 2; // in meters

        if (motion.ready()) {
            double serverTime = percIn.getServerTime();

            // Se o robô tiver caído no chão
            if (percIn.getAcc().getZ() < 2) {
                if (percIn.getAcc().getY() > 0) {
                    motion.setStandUpFromBack();
                } else {
                    motion.setRollOverToBack();
                }
            }

            // Se o goleiro souber onde a bola está, ou seja, consegue obter suas coordenadas
            else if ((serverTime - ball.getTimeStamp()) < lookTime) {
                Vector3D ballCoords = ball.getCoords();
                double distBola = ballCoords.getNorm();
                // System.out.println(ballCoords);

                // Se a bola não estiver na frente do goleiro
                if (Math.abs(ballCoords.getAlpha()) > TOLLERATED_DEVIATION) {
                    if (robotIsWalking) {
                        motion.setStopWalking();
                        robotIsWalking = false;
                    }

                    else {
                        // TODO: Dá para melhorar esse movimento 'setTurnLeftSmall' para virar um pouco mais rápido e conseguir realizar o dive.

                        double alpha = ballCoords.getAlpha();
                        // Se a bola estiver mais à esquerda (alpha > 0), então se posicione mais para o lado esquerdo.
                        if (alpha > 0) {
                            if (alpha > .5 && alpha < .7)
                                motion.setDive_Right();
                            else {
                                System.out.println("Virando para esquerda.");
                                motion.setTurnLeftSmall();
                            }
                        }

                        // Caso contrário, a bola está à direita, e portanto, posicione-se mais para o outro lado.
                        else {
                            System.out.println("Virando para direita.");
                            motion.setTurnRightSmall();
                        }

                        System.out.println("ALPHA: " + alpha);
                    }
                }

                // Se a bola estiver próxima do goleiro
                else if (distBola < LIMIT_FOR_BALL) {
                    // TODO: Após o robô cair no chão, ele precisa se virar pra frente, ou seja, voltar pra posição inicial
                    // Descobrir como fazer isso.

                    System.out.println("Distância da bola: " + distBola);
                    motion.setWalkForward_Rijeka2013();
                    robotIsWalking = true;
                }
            }
        }
    }
}
