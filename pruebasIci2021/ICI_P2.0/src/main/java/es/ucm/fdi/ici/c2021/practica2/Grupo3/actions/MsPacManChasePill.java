package es.ucm.fdi.ici.c2021.practica2.Grupo3.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.fsm.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacManChasePill implements Action{
	
	double maxPathDistance = 50;
	DM euristic = DM.PATH;
		
		public MsPacManChasePill() {

		}
		
		@Override
		public MOVE execute(Game game) {
			
			int pacManNode = game.getPacmanCurrentNodeIndex(); 
			MOVE[] moves = game.getPossibleMoves(pacManNode, game.getPacmanLastMoveMade());
			ArrayList<MOVE>possibleMoves=new ArrayList<MOVE>();
			MOVE nextMove = MOVE.NEUTRAL;
			
			for(MOVE m: moves) {
				possibleMoves.add(m);
			}
			
			for(GHOST ghostType : GHOST.values()) {
				
				int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
					
				double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
				//Si estan dentro del radio, no son comestibles.
				if(!game.isGhostEdible(ghostType)) {
					//Si el estan dentro del radio maximo siguiendo el camino
					if(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType))
							<= maxPathDistance){
						//Borran los posibles movimientos que le dirijan hacia un fantasma demasiado cercano que vaya hacia él
						if(game.getGhostLastMoveMade(ghostType) == game.getNextMoveTowardsTarget(ghostNode, pacManNode, euristic)) {
							possibleMoves.remove(game.getNextMoveTowardsTarget(pacManNode, ghostNode, euristic));
						}
					}
				}
			}
			//Pildora mas cercana
			int pill = getClosestPossiblePill(game, possibleMoves, pacManNode);
			
			
			if(possibleMoves.isEmpty())
				nextMove=MOVE.NEUTRAL;
			//Va hacia pildora
			else if(pill !=-1)
				nextMove=game.getNextMoveTowardsTarget(pacManNode, pill, euristic);
			//Movimiento random
			else {
				int siz = possibleMoves.size();
					
				int mv = (int)Math.floor(Math.random()*siz);
					
				nextMove=possibleMoves.get(mv);
			}
			
			return nextMove;
			
		}
		
		public int getClosestPossiblePill(Game game, ArrayList<MOVE> moves, int pacManNode) {
			double pillDist=Double.MAX_VALUE;
			int closestPill=-1;
			for(int pill: game.getActivePillsIndices()) {
				double dist=game.getDistance(pacManNode, pill, euristic);
				//SI el siguiente movimiento hacia la pill es posible, y la distancia es menor que la anterior.
				if(moves.contains(game.getNextMoveTowardsTarget(pacManNode, pill, euristic)) && 
						game.getDistance(pacManNode, pill, euristic)<pillDist) {
					pillDist=dist;
					closestPill=pill;
				}
			}
			return closestPill;
		}
		
	}
