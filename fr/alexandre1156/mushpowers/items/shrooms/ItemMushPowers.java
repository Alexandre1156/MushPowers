package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class ItemMushPowers extends ItemFood {

	public ItemMushPowers(int amount, float saturation, String unlocalizedAndRegsitryName) {
		super(amount, saturation, false);
		this.setUnlocalizedName(unlocalizedAndRegsitryName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, unlocalizedAndRegsitryName));
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setAlwaysEdible();
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
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

	public abstract TextFormatting getColorName();

	public abstract boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player);
	
	public abstract ShroomParticle getParticleOnLivingEntity();
	
	public boolean compatibleEntityBase(EntityLivingBase ent){
		return ent instanceof EntityArmorStand || ent instanceof EntityDragon || ent instanceof EntityFlying || ent instanceof EntityWaterMob ? false : true;
	}
	
	public abstract boolean isEntityLivingCompatible();

}
