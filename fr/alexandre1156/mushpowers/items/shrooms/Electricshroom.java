package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Electricshroom extends ItemFood{

	public Electricshroom() {
		super(1, 0.0F, false);
		this.func_77655_b("electricshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "electricshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setElectric(true);
			mush.setCooldown(MainMushPowers.ELECTRIC, (short) 18000);
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.ELECTRIC);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"You will move faster and when hitting with a sword you have a 10% chance of electrifying your target,\nmaking it unable to move and dealing 3 extra hearts of damage.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts 15 minutes");
	}

}
