package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.eat.EatMushPowersProvider;
import fr.alexandre1156.mushpowers.capabilities.eat.IEatMushPower;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class ItemMushPowers extends ItemFood {

	private HashMap<String, Object> personnalDatas;
	
	public ItemMushPowers(int amount, float saturation, String unlocalizedAndRegistryName) {
		super(amount, saturation, false);
		this.setUnlocalizedName(unlocalizedAndRegistryName);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, unlocalizedAndRegistryName));
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setAlwaysEdible();
		personnalDatas = Maps.newHashMap();
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(player instanceof EntityPlayerMP)
			this.onMushEaten(getMushType(), (EntityPlayerMP) player);
	}
	
	private void onMushEaten(Mushs mush, EntityPlayerMP player) {
		if(!player.world.isRemote) {
			IEatMushPower eat = player.getCapability(EatMushPowersProvider.EAT_MUSHS_CAP, null);
			if(!eat.hasEat(mush)) {
				eat.addEatenMushPowers(mush);
				MushPowers.ALL_MUSH_POWERS.trigger(player);
			}
		}
	}
	
	protected void registerData(String id, Types type) {
		System.out.println("CLASS "+this.getClass().getName()+" REGISTER "+id+" WITH TYPES "+type.name());
		switch (type) {
		case BOOLEAN:
			personnalDatas.put(id, false);
			break;
		case INTEGER:
			personnalDatas.put(id, (int) 0);
			break;
		case SHORT:
			personnalDatas.put(id, (short) 0);
			break;
		}
	}
	
	protected enum Types {
		SHORT, INTEGER, BOOLEAN
	}
	
	public HashMap<String, Object> getPersonnalDatas() {
		return this.personnalDatas;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult<>(EnumActionResult.FAIL, itemstack);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}
	
	public void resetPower(IPlayerMush mushCap, EntityLivingBase entLiv) {
		
	}

	public abstract TextFormatting getColorName();

	public abstract boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player);
	
	public abstract ShroomParticle getParticleOnLivingEntity();
	
	protected abstract Mushs getMushType();
	
	public boolean compatibleEntityBase(EntityLivingBase ent){
		return ent instanceof EntityArmorStand || ent instanceof EntityDragon || ent instanceof EntityFlying || ent instanceof EntityWaterMob ? false : true;
	}
	
	public abstract boolean isEntityLivingCompatible();

}
