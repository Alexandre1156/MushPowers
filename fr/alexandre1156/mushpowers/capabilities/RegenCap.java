package fr.alexandre1156.mushpowers.capabilities;

public class RegenCap implements IRegen {

	private boolean isBad;
	
	@Override
	public void setBad(boolean bad) {
		this.isBad = bad;
	}

	@Override
	public boolean isBad() {
		return isBad;
	}

}
