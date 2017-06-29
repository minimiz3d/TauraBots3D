/**
 * *****************************************************************************
 * RoboNewbie NaoTeam Humboldt
 *
 * @author Hans-Dieter Burkhard, 11.2.2015, 12.3.2015
 * @version 1.1
 *
 * *****************************************************************************
 */
package tauraTeam.comportamentos;

import agentIO.PerceptorInput;
import agentIO.ServerCommunication;
import directMotion.LookAroundMotion;
import java.util.HashMap;
import keyframeMotion.KeyframeMotion;
import localFieldView.BallModel;
import localFieldView.GoalPostModel;
import localFieldView.FlagModel;
import localFieldView.LocalFieldView;
import util.FieldConsts;
import util.FieldConsts.GoalPostID;
import util.FieldConsts.FlagID;
import util.Logger;

// Testes
import tauraTeam.*;

/**
 * The decide method for the think part in class Agent_SoccerTeam depends on the
 * role of a player. The roles are assigned according to the player numbers ("1"
 * for the goalie etc.). The related decide methods are implement in special
 * classes (inheriting from class Role).
 *
 * The assignments of the roles are performed here: The numbered roles have to
 * be declared, the classes have to be instantiated, and the related
 * decide-procedures are then called in each cycle.
 *
 * The player numbers are usually assigned by the server (if id = 0 was set in
 * Agent_SoccerTeam). The related beam positions were defined in the class
 * util.BeamPoses.
 *
 * All parameters determining the identity (teamSide, teamName, finalRobotID,
 * finalBeamCoordsX, finalBeamCoordsY, finalBeamCoordsRot) are contained in
 * ServerCommunication.
 */
public class SoccerTeamThinking {

    Logger log;
    PerceptorInput percIn;
    LocalFieldView localView;
    KeyframeMotion motion;
    static BallModel ball;
    static GoalPostModel oppGoalLPost, oppGoalRPost, ownGoalLPost, ownGoalRPost;
    static FlagModel oppFlagRight, oppFlagLeft, ownFlagRight, ownFlagLeft;
    static String playerNumber;
    static double lookTime;
    /**
     * Declaration of roles.#
     *
     * Must be updated for new or changing roles.
     */
    GoleiroComportamento roleGoleiro;
    DefensorComportamento roleDefensor;
    VolantaoComportamento roleVolante;
    MeiaComportamento roleMeia;
    AtacanteComportamento roleAtacante;
    // SimpleSoccer role_d;

    /**
     * Constructor.
     *
     * @param All passed object must be already initalized, and correctly
     * synchronized with the simulation server. None of the arguments can be
     * null.
     */
    public SoccerTeamThinking(PerceptorInput percIn, LocalFieldView localView,
            KeyframeMotion kfMotion, Logger log, ServerCommunication sc) {
        this.percIn = percIn;
        this.localView = localView;
        this.motion = kfMotion;
        //this.motion.setLogging(true);
        this.log = log;

        /**
         * The decide procedures in the different roles need the coordinates of
         * the ball and the goals for orientation.
         */
        ball = this.localView.getBall();

        HashMap<GoalPostID, GoalPostModel> goalPosts = this.localView.getGoals();
        /**
         * The opponent goal is the right one (with oppGoalLPost = G1R) if the
         * team starts from the left side. It is the left one (with oppGoalLPost
         * = G2R from the players view) if the team starts on the right side.
         * The teamside is inferred after initialization in ServerCommunication.
         */
        HashMap<FieldConsts.FlagID, FlagModel> flags = this.localView.getFlags();
        /**
         * Like the goal posts, the flags depend on the side.
         */

        if (sc.teamSide.equals("left")) {

            oppGoalLPost = goalPosts.get(GoalPostID.G1R);
            oppGoalRPost = goalPosts.get(GoalPostID.G2R);
            ownGoalLPost = goalPosts.get(GoalPostID.G1L);
            ownGoalRPost = goalPosts.get(GoalPostID.G2L);

            oppFlagLeft = flags.get(FlagID.F1R);
            oppFlagRight = flags.get(FlagID.F2R);
            ownFlagLeft = flags.get(FlagID.F1L);
            ownFlagRight = flags.get(FlagID.F2L);
        }
        else {
            oppGoalLPost = goalPosts.get(GoalPostID.G2L);
            oppGoalRPost = goalPosts.get(GoalPostID.G1L);
            ownGoalLPost = goalPosts.get(GoalPostID.G1R);
            ownGoalRPost = goalPosts.get(GoalPostID.G2R);

            oppFlagLeft = flags.get(FlagID.F1L);
            oppFlagRight = flags.get(FlagID.F2L);
            ownFlagLeft = flags.get(FlagID.F1R);
            ownFlagRight = flags.get(FlagID.F2R);
        }

        playerNumber = sc.finalRobotID;
        lookTime = LookAroundMotion.LOOK_TIME;

        /**
         * Assignment of decide methods to the roles.
         *
         * They are assigned according to the player numbers. Decide methods are
         * implemented by the related role classes. They are called in method
         * decide below.
         *
         * If another role is assigned to a number, the declaration above must
         * be changed accordingly.
         */
        switch (playerNumber) {
            /* 4,2,3,1
            DEF: 2,3,4,6 (default)
            VOL: 5,8
            MEI: 7,10,11
            ATA: 9
            */
            case "1":
                System.out.println("Iniciando goleiro.");
                roleGoleiro = new GoleiroComportamento(motion, percIn, log);
                break;

            /* Volantes */
            case "5":
            case "8":
                System.out.println("Iniciando volante.");
                roleVolante = new VolantaoComportamento(motion, percIn, log);
                break;

            /* Meias */
            case "7":
            case "10":
            case "11":
                System.out.println("Iniciando meia.");
                roleMeia = new MeiaComportamento(motion, percIn, log);
                break;

            /* Atacante */
            case "9":
                System.out.println("Iniciando Atacante.");
                roleAtacante = new AtacanteComportamento(motion, percIn, log);
                break;

            /* Defensores */
            default:
                System.out.println("Iniciando defensor.");
                roleDefensor = new DefensorComportamento(motion, percIn, log);
                break;

        }
    }

    /**
     * To use the decide procedure for number n, the related
     * role for player n must have been declared and specified above.
     * Instead you can use the default role.
     */
    public void decide() {
        switch (playerNumber) {
            /* Goleiro */
            case "1":
                roleGoleiro.decide();
                break;

            /* Volantes */
            case "5":
            case "8":
                roleVolante.decide();
                break;

            /* Meias */
            case "7":
            case "10":
            case "11":
                roleMeia.decide();
                break;

            /* Atacante */
            case "9":
                roleAtacante.decide();
                break;

            /* Defensores */
            default:
                roleDefensor.decide();
        }
    }
}
