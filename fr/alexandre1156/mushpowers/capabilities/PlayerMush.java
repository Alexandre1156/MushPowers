package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.config.MushConfig;

public class PlayerMush implements IPlayerMush{

	private boolean squid;
	private int squidAir;
	private byte shroomEaten;
	private boolean zombieAway;
	private boolean hostile;
	private boolean chicken;
	private boolean ghost;
	private boolean eletric;
	private float shieldDamage;
	private boolean flying;
	private byte repairCost;
	
	@Override
	public boolean isSquid() {
		return squid;
	}

	@Override
	public void setSquid(boolean squid) {
		this.squid = squid;
	}

	@Override
	public void setSquidAir(int squidAir) {
		this.squidAir = squidAir;
	}

	@Override
	public int getSquidAir() {
		return this.squidAir;
	}

	@Override
	public void setShroomCount(byte shroomsEaten) {
		this.shroomEaten = shroomsEaten;
	}

	@Override
	public byte getShroomCount() {
		return this.shroomEaten;
	}
	
	@Override
	public boolean isPizzaEaten(){
		return this.shroomEaten > 0 && this.shroomEaten <= MushConfig.foodCountPizzashroom;
	}

	@Override
	public void setZombieAway(boolean zombieAway) {
		this.zombieAway = zombieAway;
	}

	@Override
	public boolean isZombieAway() {
		return this.zombieAway;
	}
	
	@Override
	public void setCooldown(MainMushPowers power, short time){
		power.setTimeLeft(time);
	}
	
	@Override
	public short getCooldown(MainMushPowers power){
		return power.getTimeLeft();
	}
	

	@Override
	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	@Override
	public boolean isHostile() {
		return this.hostile;
	}
	
	public static enum MainMushPowers{
		SQUID((short) 0), ZOMBIEAWAY((short) 0), HOSTILE((short) 0), CHICKEN((short) 0), GHOST((short) 0), ELECTRIC((short) 0), FLY((short) 0);
		
		private short timeLeft;
		
		private MainMushPowers(short timeLeft){
			this.timeLeft = timeLeft;
		}
		
		private void setTimeLeft(short timeLeft){
			this.timeLeft = timeLeft;
		}
		
		private short getTimeLeft(){
			return this.timeLeft;
		}
	}
	
	public static enum SecondaryMushPowers {
		PIZZA, REGEN
	}

	@Override
	public void setChicken(boolean chicken) {
		this.chicken = chicken;
	}

	@Override
	public boolean isChicken() {
		return this.chicken;
	}

	@Override
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}

	@Override
	public boolean isGhost() {
		return this.ghost;	
	}

	@Override
	public void setElectric(boolean eletric) {
		this.eletric = eletric;
	}

	@Override
	public boolean isElectric() {
		return this.eletric;	
	}

	@Override
	public void setShieldDamageAbsorb(float damage) {
		this.shieldDamage = damage;
	}

	@Override
	public boolean isShieldActive() {
		return this.shieldDamage > 0;
	}

	@Override
	public float getShieldDamage() {
		return this.shieldDamage;
	}

	@Override
	public void setFly(boolean isFlying) {
		this.flying = isFlying;
	}

	@Override
	public boolean isFlying() {
		return this.flying;
	}

	@Override
	public void setLowerRepairCost(byte value) {
		this.repairCost = value;
	}

	@Override
	public boolean isRepairCostLower() {
		return this.repairCost <= 20 && this.repairCost > 0;
	}

	@Override
	public byte getRepairCostLeft() {
		return this.repairCost;
	}

}
