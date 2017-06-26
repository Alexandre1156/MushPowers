package fr.alexandre1156.mushpowers;

public class MushUtils {

	public static int ticksInMinutes(int ticks){
		return Integer.valueOf(String.valueOf(ticks / 20 / 60).split(",")[0]);
	}
	
	public static int ticksInSeconds(int ticks){
		return (ticks - (ticks % 20)) / 20;
	}
	
	public static int ticksToRestSecond(int ticks){
		return ticksInSeconds(ticks % 1200);
	}
	
	public static String correctCooldownMessage(int ticks){
		String cooldownMessage = "";
		if(ticksInMinutes(ticks) == 1)
			cooldownMessage = String.valueOf(ticksInMinutes(ticks)) + " minute";
		else if(ticksInMinutes(ticks) >= 2)
			cooldownMessage = String.valueOf(ticksInMinutes(ticks)) + " minutes";
		if(ticksToRestSecond(ticks) == 1)
			cooldownMessage += cooldownMessage.isEmpty() ? String.valueOf(ticksToRestSecond(ticks)) + " second" : " "+ String.valueOf(ticksToRestSecond(ticks)) + " second";
		else if(ticksToRestSecond(ticks) > 2)
			cooldownMessage += cooldownMessage.isEmpty() ? String.valueOf(ticksToRestSecond(ticks)) + " seconds" : " " + String.valueOf(ticksToRestSecond(ticks)) + " seconds";
		return cooldownMessage;
	}
	
	public static String startStringWithCapital(String string){
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
}
