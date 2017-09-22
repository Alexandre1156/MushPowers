package fr.alexandre1156.mushpowers.capabilities.eat;

import java.util.ArrayList;

import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;

public interface IEatMushPower {

	public boolean hasEat(Mushs mush);
	
	public void addEatenMushPowers(Mushs mush);
	
	public boolean hasEatenAllMush();
	
	public ArrayList<Mushs> getAllEatenMushPowers();
	
}
