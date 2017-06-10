package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Pizzashroom extends ItemFood {

	public Pizzashroom() {
		super(2, 1.2f, false);
		this.func_77655_b("pizzashroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "pizzashroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}

	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!worldIn.field_72995_K){
			mush.setShroomCount((byte) 3);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Each food you eat will restore one more hunger point.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts after eating 3 foods");
	}
	

}
