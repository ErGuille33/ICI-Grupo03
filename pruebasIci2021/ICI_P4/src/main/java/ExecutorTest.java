

import es.ucm.fdi.ici.c2021.practica4.grupo03.Ghosts;
import es.ucm.fdi.ici.practica4.demofuzzy.MsPacManFuzzy;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;


public class ExecutorTest {

    public static void main(String[] args) {
        Executor executor = new Executor.Builder()
                .setTickLimit(4000)
                .setGhostPO(true)
                .setPacmanPO(true)
                .setVisual(true)
                .setScaleFactor(3.0)
                .build();

        PacmanController pacMan = new MsPacManFuzzy();
        GhostController ghosts = new Ghosts();
        
        System.out.println( 
        		executor.runGame(pacMan, ghosts, 40)
        );
        
    }
}