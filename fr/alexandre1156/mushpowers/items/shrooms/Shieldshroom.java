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
		this.setUnlocalizedName("shieldshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "shieldshroom"));
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setAlwaysEdible();
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setShieldDamageAbsorb((byte) MushConfig.maxDamageAbsorbShieldshroom);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Absorbs "+MushConfig.damageAbsordPercentShieldshroom+"% of the damage taken.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts when absorbing "+MushConfig.maxDamageAbsorbShieldshroom+" half-heaths of damage");
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

}
