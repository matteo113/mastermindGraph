package mastermindGraph;

import ch.aplu.jgamegrid.Actor;

/**
 * La classe BtEval représente un bouton de recomencement de partie dans l'interface de jeu Mastermind. Elle hérite de la classe Actor de JGameGrid.
 * 
 * @author Matteo B. & Olga K.
 *
 */
public class BtNouv extends Actor {
	/**
	 * Construit un bouton de nouvelle partie avec l'image correspondante.
	 */
	public BtNouv(){
		super("paint/btnouv.png");
	}
}
