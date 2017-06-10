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

public class Shieldshroom extends ItemFood {

	public Shieldshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("shieldshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "shieldshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setShieldDamageAbsorb((byte) 10);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Absorbs 25% of the damage taken.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts when absorbing 5HP of damage");
	}

}
