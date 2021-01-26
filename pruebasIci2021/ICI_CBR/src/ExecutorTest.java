

import es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.Ghost;
//import es.ucm.fdi.ici.c2021.practica2.grupo03.Ghosts;
//import es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.MsPacMan;
import es.ucm.fdi.ici.c2021.practica2.grupo03.MsPacMan;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;


public class ExecutorTest {

    public static void main(String[] args) {
    	int n = 0;
    	for(int i = 0; i < 10; i++) {
	        Executor executor = new Executor.Builder()
	                .setTickLimit(4000)
	                .setVisual(false)
	                .setScaleFactor(3.0)
	                .build();
	
	        PacmanController pacMan = new MsPacMan();
	        GhostController ghosts = new Ghost();
	      
	        n+=executor.runGame(pacMan, ghosts, 0);
	        System.out.println( 
	        		n/(i+1)
	        );
    	}
        
    }
}
