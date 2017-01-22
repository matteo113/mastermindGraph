package mastermindGraph;

import ch.aplu.jgamegrid.Actor;

/**
 * La classe BtNiv représente un bouton de selection de niveau de l'interface de
 * niveau du jeu Mastermind. Elle hérite de la classe Actor de JGameGrid. Le
 * bouton peut prendre soir l'apparence "facile", "moyen" ou "difficile".
 * 
 * @author Matteo B. & Olga K.
 *
 */
public class BtNiv extends Actor {

	/**
	 *  Construit un bouton de selection de niveau avec les images correspondantes.
	 */
	public BtNiv() {
		super("paint/btMen.png", 3);
	}
}
