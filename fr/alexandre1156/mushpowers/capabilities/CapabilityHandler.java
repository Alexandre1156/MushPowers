package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

	public static final ResourceLocation MUSH_CAP = new ResourceLocation(Reference.MOD_ID, "squid");
	public static final ResourceLocation REGEN_CAP = new ResourceLocation(Reference.MOD_ID, "regen");
	
	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent.Entity e){
		if(!(e.getEntity() instanceof EntityPlayer)) return;
		
		e.addCapability(MUSH_CAP, new PlayerMushProvider());
	}
	
	@SubscribeEvent
	public void attackCapabilityItem(AttachCapabilitiesEvent.Item e){
		if(e.getItem() != CommonProxy.itemRegenshroom) return;
		
		e.addCapability(REGEN_CAP, new RegenProvider());
	}
	
}
