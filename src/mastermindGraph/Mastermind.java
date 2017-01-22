package mastermindGraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import ch.aplu.jgamegrid.*;

public class Mastermind extends GameGrid implements GGMouseTouchListener {
	private int nbCache, nbCoul, line;
	private Pion[] rep;
	private Pion[][] history;
	private int[] sol;
	private Actor btEval;
	private boolean isFinished;

	public Mastermind(int nbCache) {
		super(nbCache + 3, 12, 60, null, false);
		this.setBgColor(222, 184, 135);
		this.setTitle("MasterMind");
		this.nbCache = nbCache;
		this.nbCoul = nbCache + 2;
		this.rep = new Pion[nbCache];
		this.sol = new int[nbCache];
		this.history = new Pion[this.getNbVertCells() - 2][nbCache];
		show();
		initGame();
		doRun();
	}

	public void initGame() {
		removeAllActors();
		creatCombination();
		creatCommand();
		line = this.getNbVertCells() - 2;
		isFinished = false;
		getBg().clear();
	}

	private void creatCombination() {
		for (int i = 0; i < sol.length; i++) {
			sol[i] = (int) (Math.random() * nbCoul);
			System.out.println(sol[i]);
		}
	}

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

	public void finPartie(String raison) {
		Font msg = new Font("verdana", Font.PLAIN, 30);
		getBg().setPaintColor(Color.red);
		getBg().setFont(msg);
		getBg().drawText(raison, new Point(10, 30));
		isFinished = true;
		showSol();
		nouvelPartie();
	}
	
	public void nouvelPartie(){
		BtNouv nouvPartie = new BtNouv();
		for (int i =0; i<this.getNbHorzCells();i++){
			removeActorsAt(new Location(i,this.getNbVertCells()-1));
		}
		addActor(nouvPartie, new Location(1,this.getNbVertCells()-1));
		nouvPartie.addMouseTouchListener(this, GGMouse.lPress);
		
	}

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

	public void showCombination() {
		int[] repInt = new int[nbCache];
		for (int i = 0; i < rep.length; i++) {
			repInt[i] = rep[i].getIdVisible();
			Pion pion = new Pion(nbCoul);
			addActor(pion, new Location(i + 2, line));
			pion.show(repInt[i]);
		}
	}

	public void showSol() {
		for (int i = 0; i < sol.length; i++) {
			Pion pionS = new Pion(nbCoul);
			addActor(pionS, new Location(2+i,0));
			pionS.show(sol[i]);
		}
	}

	@Override
	public void mouseTouched(Actor arg0, GGMouse mouse, Point arg2) {
		Location loc = toLocation(mouse.getX(), mouse.getY());

		if (!isFinished){
			if (arg0.getClass().getName().equals("mastermindGraph.BtEval")) {
				evalCombination();
			}

			if (loc.y == this.getNbVertCells() - 1 && loc.x > 1 && loc.x < this.getNbHorzCells()) {
				if (mouse.getEvent() == GGMouse.lPress) {
					arg0.showNextSprite();
				}
				else {
					arg0.showPreviousSprite();
				}
			}
		}
		else{
			if (arg0.getClass().getName().equals("mastermindGraph.BtNouv")) {
				initGame();
			}
		}
	}

	public static void main(String[] args) {
		new ChoixNiv();
		

	}
}
