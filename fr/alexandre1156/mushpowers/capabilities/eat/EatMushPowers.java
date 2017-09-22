package fr.alexandre1156.mushpowers.capabilities.eat;

import java.util.ArrayList;

import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;

public class EatMushPowers implements IEatMushPower {

	private ArrayList<Mushs> mushEaten = new ArrayList<>();
	
	@Override
	public boolean hasEat(Mushs mush) {
		return mushEaten.contains(mush);
	}

	@Override
	public void addEatenMushPowers(Mushs mush) {
		this.mushEaten.add(mush);
	}

	@Override
	public boolean hasEatenAllMush() {
		return mushEaten.size() == CommonProxy.getMushPowers().size()-1;
	}
	
	@Override
	public ArrayList<Mushs> getAllEatenMushPowers() {
		return this.mushEaten;
	}
	
	
	
}
