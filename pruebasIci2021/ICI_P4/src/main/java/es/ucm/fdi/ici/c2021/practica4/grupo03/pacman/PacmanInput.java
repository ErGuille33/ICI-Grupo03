package es.ucm.fdi.ici.c2021.practica4.grupo03.pacman;
import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class PacmanInput implements Input {
	private boolean[] isChasedGhost = new boolean[4];
	private boolean[] edibleGhost = new boolean[4];
	private double[] distance = new double [4];
	private double[] confidence = new double [] {0, 0, 0, 0};

	DM euristic;

	
	public void parseInput(Game game) {	
		edibleGhost  = new boolean []  {false,false,false,false};
		isChasedGhost  = new boolean []  {false,false,false,false};
		distance = new double [] {-1,-1,-1,-1};
		euristic = DM.PATH;
		int i = 0;
		int pacManNode = game.getPacmanCurrentNodeIndex();
		for (GHOST g : GHOST.values()) {
			int ghostNode = game.getGhostCurrentNodeIndex(g);
			if(ghostNode ==-1) {
				if(confidence[i]>0)
					confidence[i]-=.1;
				continue;
			}
			
			if (game.isGhostEdible(g))
				edibleGhost[i] = true;
			
			if(game.getGhostLastMoveMade(g) == game.getNextMoveTowardsTarget(ghostNode, pacManNode, euristic))
				isChasedGhost[i] = true;
			
			distance[i] = game.getDistance(pacManNode, ghostNode, euristic);
			if(edibleGhost[i]) {
				confidence[i] = 0;
			}
			else if(!edibleGhost[i]) {
				if(!isChasedGhost[i]) {
					confidence[i]=50;
				}
				else {
					confidence[i]=100;
				}
			}
			i++;
		}
	
	}
	
	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		vars.put("BLINKYdistance", distance[0]);
		vars.put("PINKYdistance", distance[1]);
		vars.put("INKYdistance", distance[2]);
		vars.put("SUEdistance", distance[3]);
		vars.put("BLINKYconfidence", confidence[0]);
		vars.put("PINKYconfidence", confidence[1]);
		vars.put("INKYconfidence", confidence[2]);
		vars.put("SUEconfidence", confidence[3]);
		return vars;
	}
}
