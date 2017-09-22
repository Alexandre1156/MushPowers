package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.eat.EatMushPowersProvider;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.capabilities.regen.RegenProvider;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

	public static final ResourceLocation MUSH_CAP = new ResourceLocation(Reference.MOD_ID, "squid");
	public static final ResourceLocation REGEN_CAP = new ResourceLocation(Reference.MOD_ID, "regen");
	public static final ResourceLocation ALL_MUSH_EATEN_CAP = new ResourceLocation(Reference.MOD_ID, "allmushs");
	
	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e){
		if(!(e.getObject() instanceof EntityLivingBase)) return;
		
		e.addCapability(MUSH_CAP, new PlayerMushProvider());
		if(e.getObject() instanceof EntityPlayer)
			e.addCapability(ALL_MUSH_EATEN_CAP, new EatMushPowersProvider());
	}
	
	@SubscribeEvent
	public void attachCapabilityItem(AttachCapabilitiesEvent<ItemStack> e){
		if(e.getObject().getItem() == CommonProxy.itemRegenshroom)
			e.addCapability(REGEN_CAP, new RegenProvider());
	}
	
}
