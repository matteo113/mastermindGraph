package mastermindGraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import ch.aplu.jgamegrid.*;

/**
 * La classe Mastermind permet de représenter une partie de mastermind. Elle
 * hérite de la classe GameGrid qui fait partis de la librairie JgameGrid. Elle
 * est disponible sur le site de son créateur
 * <a href="http://www.aplu.ch/home/apluhomex.jsp?site=45"> http://www.aplu.ch
 * </a>
 * 
 * Elle implémente également l'interface GGMouseListenenr de la même librairie.
 * 
 * @author Matteo B. & Olga K.
 *
 */
public class Mastermind extends GameGrid implements GGMouseTouchListener {
	private int nbCache, nbCoul, line;
	private Pion[] rep;
	private int[] sol;
	private Actor btEval;
	private boolean isFinished;

	/**
	 * Construit un objet Mastermind en fonction de nombre de pions qui sont
	 * cachés. /!\ attention à cause de nos resources graphiques (images) ce
	 * nombre ne dois pas dépasser 6. En construisant une partie on crée
	 * également un interface de jeu.
	 * 
	 * @param nbCache
	 *            le nombre de pions qui sont cachés
	 */
	public Mastermind(int nbCache) {
		super(nbCache + 3, 12, 60, null, false);
		this.setBgColor(222, 184, 135);
		this.setTitle("MasterMind");
		this.nbCache = nbCache;
		this.nbCoul = nbCache + 2;
		this.rep = new Pion[nbCache];
		this.sol = new int[nbCache];
		show();
		initGame();
		doRun();
	}

	/**
	 * Initialise le jeu en suprimant tout les acteurs présents sur l'interface
	 * de jeu et en initialisant les comandes ainsi qu'en crant une nouvelle
	 * combinaison mystère.
	 */
	public void initGame() {
		removeAllActors();
		creatCombination();
		creatCommand();
		line = this.getNbVertCells() - 2;
		isFinished = false;
		getBg().clear();
	}

	/**
	 * Crée une nouvelle combinaison mystère
	 */
	private void creatCombination() {
		for (int i = 0; i < sol.length; i++) {
			sol[i] = (int) (Math.random() * nbCoul);
		}
	}

	/**
	 * Crée un nouvel interface de commande
	 */
	private void creatCommand() {
		for (int i = 0; i < rep.length; i++) {
			rep[i] = new Pion(nbCoul);
			addActor(rep[i], new Location(2 + i, this.getNbVertCells() - 1));
			rep[i].addMouseTouchListener(this, GGMouse.lPress);
		}
		this.btEval = new BtEval();
		addActor(btEval, new Location(1, this.getNbVertCells() - 1));
		btEval.addMouseTouchListener(this, GGMouse.lPress);
	}

	/**
	 * Termine la partie en affaichant si elle est gagnée ou perdue et dévoile
	 * la combinaison mystère.
	 * 
	 * @param raison
	 *            un string contenant la raison de fin de la partie qui sera
	 *            affichée.
	 */
	public void finPartie(String raison) {
		Font msg = new Font("verdana", Font.PLAIN, 30);
		getBg().setPaintColor(Color.red);
		getBg().setFont(msg);
		getBg().drawText(raison, new Point(10, 30));
		isFinished = true;
		showSol();
		nouvelPartie();
	}

	/**
	 * Suprimme les commandes de jeu et fait apparaître un bouton pour rejouer.
	 */
	public void nouvelPartie() {
		BtNouv nouvPartie = new BtNouv();
		for (int i = 0; i < this.getNbHorzCells(); i++) {
			removeActorsAt(new Location(i, this.getNbVertCells() - 1));
		}
		addActor(nouvPartie, new Location(1, this.getNbVertCells() - 1));
		nouvPartie.addMouseTouchListener(this, GGMouse.lPress);

	}

	/**
	 * Evalue si la combinaison soumises est juste ou pas et l'affice dans
	 * l'historique avec les indices.
	 */
	public void evalCombination() {
		int[] repInt = new int[nbCache];
		ArrayList<Integer> solb = new ArrayList<Integer>();
		int in = 0, ib = 0;

		for (int i = 0; i < repInt.length; i++) {
			repInt[i] = rep[i].getIdVisible();
			solb.add(sol[i]);
			if (repInt[i] == sol[i]) {
				in++;
			}
		}
		for (int i = 0; i < repInt.length; i++) {
			Boolean cont = true;
			int j = 0;
			while (cont && j < solb.size()) {
				if (solb.get(j) == repInt[i]) {
					solb.remove(j);
					cont = false;
					ib++;
				}
				j++;
			}
		}
		ib -= in;
		showIndices(in, ib);
		showCombination();
		if (in == nbCache) {
			finPartie("Gagné !");
		}

		else {
			line--;
			if (line == 0) {
				finPartie("Perdu !");
			}
		}

		if (!isFinished) {

		}
	}

	/**
	 * Affiche les indices blancs et noirs
	 * 
	 * @param in
	 *            le nombre d'indices noires à afficher
	 * @param ib
	 *            le nombre d'indices blancs à afficher
	 */
	public void showIndices(int in, int ib) {
		int ti = in + ib;

		for (int i = 0; i < ti; i++) {
			if (in > 0) {
				IndiceN ipn = new IndiceN();
				addActor(ipn, new Location(1, line));
				ipn.show(i);
				in--;
			} else if (ib > 0) {
				IndiceB ipb = new IndiceB();
				addActor(ipb, new Location(1, line));
				ipb.show(i);
				ib--;
			}
		}
	}

	/**
	 * Affiche la combinaison soumise.
	 */
	public void showCombination() {
		int[] repInt = new int[nbCache];
		for (int i = 0; i < rep.length; i++) {
			repInt[i] = rep[i].getIdVisible();
			Pion pion = new Pion(nbCoul);
			addActor(pion, new Location(i + 2, line));
			pion.show(repInt[i]);
		}
	}

	/**
	 * Affiche la combinaison mystère
	 */
	public void showSol() {
		for (int i = 0; i < sol.length; i++) {
			Pion pionS = new Pion(nbCoul);
			addActor(pionS, new Location(2 + i, 0));
			pionS.show(sol[i]);
		}
	}

	/*
	 * @see
	 * ch.aplu.jgamegrid.GGMouseTouchListener#mouseTouched(ch.aplu.jgamegrid.
	 * Actor, ch.aplu.jgamegrid.GGMouse, java.awt.Point)
	 * 
	 * Permet la gestion de la souris dans le GameGrid
	 */
	@Override
	public void mouseTouched(Actor arg0, GGMouse mouse, Point arg2) {
		Location loc = toLocation(mouse.getX(), mouse.getY());

		if (!isFinished) {
			if (arg0.getClass().getName().equals("mastermindGraph.BtEval")) {
				evalCombination();
			}

			if (loc.y == this.getNbVertCells() - 1 && loc.x > 1 && loc.x < this.getNbHorzCells()) {
				if (mouse.getEvent() == GGMouse.lPress) {
					arg0.showNextSprite();
				}

				else if (mouse.getEvent() == GGMouse.rPress) { // pour une
																// raison
																// obscure ne
																// semble pas
																// marcher ....
					arg0.showPreviousSprite();
				}
			}
		} else {
			if (arg0.getClass().getName().equals("mastermindGraph.BtNouv")) {
				initGame();
			}
		}
	}

	public static void main(String[] args) {
		new ChoixNiv();

	}
}
