package fr.alexandre1156.mushpowers.advancement;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;

import fr.alexandre1156.mushpowers.items.ItemShroomRodStick;
import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

public class ItemEntityPredicate {

	private static final ItemEntityPredicate ANY = new ItemEntityPredicate();
	private final boolean value;
	
	public ItemEntityPredicate() {
		this.value = true;
	}
	
	public ItemEntityPredicate(boolean value) {
		this.value = value;
	}
	
	public boolean test(ItemStack isInHand, EntityPlayer p, EntityLivingBase target) {
		if(this.value == false)
			return false;
		else if(isInHand == null || isInHand.isEmpty())
			return false;
		else if(!p.getHeldItemMainhand().isItemEqual(isInHand))
			return false;
		else if(!(isInHand.getItem() instanceof ItemShroomRodStick))
			return false;
		else {
			Item mushPowers = CommonProxy.getMushPowersRodStickByMetadata(isInHand.getMetadata());
			if(mushPowers != null)
				return ((ItemMushPowers) mushPowers).compatibleEntityBase(target);
			else
				return false;
		}
	}
	
	public static ItemEntityPredicate deserialize(@Nullable JsonElement element) {
		if(element == null || element.isJsonNull())
			return ANY;
		else 
			return new ItemEntityPredicate(JsonUtils.getBoolean(element, "item_entities"));
	}
	
}
