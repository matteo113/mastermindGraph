package mastermindGraph;

import ch.aplu.jgamegrid.Actor;

/**
 * La classe BtEval représente un bouton d'évaluation de l'interface de jeu Mastermind. Elle hérite de la classe Actor de JGameGrid.
 * 
 * @author Matteo B. & Olga K.
 *
 */
public class BtEval extends Actor {
	/**
	 * Construit un bouton d'évaluation avec l'image correspondante.
	 */
	public BtEval(){
		super("paint/eval.png");
	}
}
