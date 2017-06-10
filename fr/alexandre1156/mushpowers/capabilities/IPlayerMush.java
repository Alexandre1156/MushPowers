package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;

public interface IPlayerMush {
	
	public boolean isSquid();
	
	public void setSquid(boolean squid);
	
	public void setSquidAir(int squidAir);
	
	public int getSquidAir();
	
	public void setShroomCount(byte shroomsEaten);
	
	public byte getShroomCount();
	
	public boolean isPizzaEaten();
	
	public void setZombieAway(boolean zombieAway);
	
	public boolean isZombieAway();
	
	public void setHostile(boolean hostile);
	
	public boolean isHostile();
	
	public void setChicken(boolean chicken);
	
	public boolean isChicken();
	
	public void setGhost(boolean ghost);
	
	public boolean isGhost();
	
	public void setElectric(boolean eletric);
	
	public boolean isElectric();
	
	public void setShieldDamageAbsorb(float damage);
	
	public float getShieldDamage();
	
	public boolean isShieldActive();
	
	public void setFly(boolean isFlying);
	
	public boolean isFlying();
	
	public void setLowerRepairCost(byte value);
	
	public byte getRepairCostLeft();
	
	public boolean isRepairCostLower();
	
	public void setCooldown(MainMushPowers power, short time);
	
	public short getCooldown(MainMushPowers power);
}
