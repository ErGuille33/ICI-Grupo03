package es.ucm.fdi.ici.c2021.practica3.grupo03.PacMan.Actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.rules.Action;
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

			ArrayList<MOVE>possibleMoves=new ArrayList<MOVE>();
			MOVE nextMove = MOVE.NEUTRAL;
			
			//Quitamos los movimientos que pongan en problemas a PacMan
			RemoveMovements rm = new RemoveMovements();

			possibleMoves = rm.getPossibleMoves(game);
			
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
		
		@Override
		public void parseFact(Fact actionFact) {
			// TODO Auto-generated method stub
			
		}
		
	}