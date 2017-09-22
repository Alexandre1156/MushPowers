package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.items.shrooms.Pizzashroom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PizzashroomEvent extends ShroomEvent {
	
	@Override
	protected void onLivingEntityUseItemFinish(EntityLivingBase entLiv, ItemStack item, World world) {
		if(entLiv instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getInteger(Pizzashroom.PIZZA_COUNT) > 0 && !world.isRemote && item.getItem() instanceof ItemFood && !(item.getItem() instanceof Pizzashroom)){
				mush.setInteger(Pizzashroom.PIZZA_COUNT, mush.getInteger(Pizzashroom.PIZZA_COUNT)-1);
				CapabilityUtils.syncCapabilities(p);
				p.getFoodStats().setFoodLevel(p.getFoodStats().getFoodLevel()+MushConfig.foodRegenPizzashroom);
			}
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death)
			p.getCapability(PlayerMushProvider.MUSH_CAP, null).setInteger(Pizzashroom.PIZZA_COUNT, 
					pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null).getInteger(Pizzashroom.PIZZA_COUNT));
	}
	
}
