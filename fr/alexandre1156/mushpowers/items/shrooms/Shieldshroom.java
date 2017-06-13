package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
			mush.setShieldDamageAbsorb((byte) MushConfig.maxDamageAbsorbShieldshroom);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.func_184586_b(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.func_77659_a(worldIn, playerIn, handIn);
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Absorbs "+MushConfig.damageAbsordPercentShieldshroom+"% of the damage taken.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts when absorbing "+MushConfig.maxDamageAbsorbShieldshroom+" half-heaths of damage");
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

}
