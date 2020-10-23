import es.ucm.fdi.ici.PacManEvaluator;
import es.ucm.fdi.ici.Scores;

public class Evaluate {

	public static void main(String[] args) {
		PacManEvaluator evaluator = new PacManEvaluator();
		Scores scores = evaluator.evaluate();
		scores.printScoreAndRanking();

	}

}
