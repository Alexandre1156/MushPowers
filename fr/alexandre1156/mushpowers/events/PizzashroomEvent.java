package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
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
			if(mush.isPizzaEaten() && !world.field_72995_K && item.func_77973_b() instanceof ItemFood && !(item.func_77973_b() instanceof Pizzashroom)){
				mush.setShroomCount((byte) (mush.getShroomCount()-1));
				PlayerMushProvider.syncCapabilities(p);
				p.func_71024_bL().func_75114_a(p.func_71024_bL().func_75116_a()+2);
			}
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(death)
			p.getCapability(PlayerMushProvider.MUSH_CAP, null).setShroomCount(pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null).getShroomCount());
	}
	
}
