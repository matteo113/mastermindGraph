package mastermindGraph;

import ch.aplu.jgamegrid.Actor;

/**
 * La classe Pion représente un Pion de couleur dans l'interface de jeu
 * Mastermind. Elle hérite de la classe Actor de JGameGrid.
 * 
 * @author Matteo B. & Olga K.
 *
 */
public class Pion extends Actor {

	/**
	 * Construit un pion de couleur en fonction du nombre de couleur différentes
	 * que le pion peut prendre.
	 * 
	 * @param nbCoul
	 *            le nombre de couleurs que le pion peut prendre
	 */
	public Pion(int nbCoul) {
		super("paint/pion.png", nbCoul);
	}
}
