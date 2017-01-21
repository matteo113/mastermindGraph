package mastermindGraph;

import java.awt.Point;

import ch.aplu.jgamegrid.*;

public class ChoixNiv extends GameGrid implements GGMouseTouchListener {

	public ChoixNiv() {
		super(5, 5, 60, null, false);
		this.setBgColor(222,184,135);
		this.setTitle("MasterMind");
		BtNiv bt1 = new BtNiv();
		BtNiv bt2 = new BtNiv();
		BtNiv bt3 = new BtNiv();
		addActor(bt1, new Location(2,1));
		addActor(bt2, new Location(2,2));
		addActor(bt3, new Location(2,3));
		bt2.show(1);
		bt3.show(2);
		bt1.addMouseTouchListener(this, GGMouse.lPress);
		bt2.addMouseTouchListener(this, GGMouse.lPress);
		bt3.addMouseTouchListener(this, GGMouse.lPress);
		show();
		doRun();
	}

	@Override
	public void mouseTouched(Actor arg0, GGMouse arg1, Point arg2) {
		
		if (arg0.getIdVisible()==0)
			new Mastermind(4);
		else if (arg0.getIdVisible()==1)
			new Mastermind(5);
		else if (arg0.getIdVisible()==2)
			new Mastermind(6);
	}

}
