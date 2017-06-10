package fr.alexandre1156.mushpowers.events.custom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Unused.
 * @author Alexandre1156/Nocturne123
 *
 */
public class PlayerPullFishHookEvent extends Event {

	private EntityPlayer angler;
	private EntityFishHook hook;
	
	public PlayerPullFishHookEvent(EntityPlayer angler, EntityFishHook hook) {
		this.angler = angler;
		this.hook = hook;
	}
	
	public EntityPlayer getAngler() {
		return angler;
	}
	
	public EntityFishHook getHook() {
		return hook;
	}
}
