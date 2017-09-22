package fr.alexandre1156.mushpowers.capabilities.player;

import java.util.HashMap;

public interface IPlayerMush {
	
	public <E extends Object> E getValue(String key, E type);
	
	public <E extends Object> void addKey(String key, E type);
	
	public HashMap<String, Object> getDatas();
	
	public <E extends Object> void setValue(String key, E value);
	
	public boolean getBoolean(String key);
	
	public short getShort(String key);
	
	public int getInteger(String key);
	
	public void setBoolean(String key, boolean value);
	
	public void setShort(String key, short value);
	
	public void setInteger(String key, int value);
}
