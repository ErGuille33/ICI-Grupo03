

import es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.Ghost;
import es.ucm.fdi.ici.c2021.practica2.grupo03.Ghosts;
import es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.MsPacMan;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;


public class ExecutorTest {

    public static void main(String[] args) {
    	for(int i = 0; i < 50; i++) {
	        Executor executor = new Executor.Builder()
	                .setTickLimit(4000)
	                .setVisual(true)
	                .setScaleFactor(3.0)
	                .build();
	
	        PacmanController pacMan = new MsPacMan();
	        GhostController ghosts = new Ghosts();
	      
	        
	        System.out.println( 
	        		executor.runGame(pacMan, ghosts, 20)
	        );
    	}
        
    }
}
