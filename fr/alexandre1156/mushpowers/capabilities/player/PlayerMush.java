package fr.alexandre1156.mushpowers.capabilities.player;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;

public class PlayerMush implements IPlayerMush{

//	private boolean squid;
//	private int squidAir;
//	private byte shroomEaten;
//	private boolean zombieAway;
//	private boolean hostile;
//	private boolean chicken;
//	private boolean ghost;
//	private boolean eletric;
//	private float shieldDamage;
//	private boolean flying;
//	private byte repairCost;
	
	private HashMap<String, Object> datas = Maps.newHashMap();
	
	{
		for(ItemMushPowers power : CommonProxy.getMushPowers()) {
			for(Entry<String, Object> entry : power.getPersonnalDatas().entrySet())
				this.datas.put(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public <E extends Object> E getValue(String key, E type) {
		if(this.datas.containsKey(key) && this.datas.get(key).getClass().isAssignableFrom(type.getClass()))
			return (E) datas.get(key);
		else {
			try {
				throw new NullPointerException("key : "+key+" not found ! Corrects datas are : "+this.datas.keySet());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	@Override
	public <E> void addKey(String key, E type) {
		this.datas.put(key, type);
	}
	
	@Override
	public HashMap<String, Object> getDatas() {
		return this.datas;	
	}
	
	@Override
	public <E> void setValue(String key, E value) {
		if(this.datas.containsKey(key) && this.datas.get(key).getClass().isAssignableFrom(value.getClass()))
			this.datas.put(key, value);
	}

	@Override
	public boolean getBoolean(String key) {
		return this.getValue(key, false);
	}

	@Override
	public short getShort(String key) {
		return this.getValue(key, (short) 0);
	}

	@Override
	public int getInteger(String key) {
		return this.getValue(key, (int) 0);
	}

	@Override
	public void setBoolean(String key, boolean value) {
		this.setValue(key, value);
	}

	@Override
	public void setShort(String key, short value) {
		this.setValue(key, value);
	}

	@Override
	public void setInteger(String key, int value) {
		this.setValue(key, value);
	}
	

}
