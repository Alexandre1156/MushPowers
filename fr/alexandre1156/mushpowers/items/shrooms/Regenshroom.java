package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.RegenProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Regenshroom extends ItemMushPowers {
	
	public Regenshroom() {
		super(1, 0.0f, "regenshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			if(!stack.getCapability(RegenProvider.REGEN_CAP, null).isBad())
				player.heal(MushConfig.hearthRegenshroom);
			else {
				int duration = this.itemRand.nextInt(2)+1 * 200;
				int level = this.itemRand.nextInt(1);
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration, level));
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration, level));
				player.addPotionEffect(new PotionEffect(MobEffects.POISON, duration, level));
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(playerIn.getHealth() >= playerIn.getMaxHealth())
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//		System.out.println(isSelected+" "+entityIn+" "+MushPowers.cursedShroomViewers.contains(entityIn.getName())+" "+!entityIn.world.isRemote+" - "+
//				stack.getCapability(RegenProvider.REGEN_CAP, null).isBad());
		if(isSelected && 
				entityIn instanceof EntityPlayer && 
				MushConfig.playersCanSeeFakeRegenshroom.contains(entityIn.getName()) && 
				!entityIn.world.isRemote 
				&& stack.getCapability(RegenProvider.REGEN_CAP, null).isBad())
			((EntityPlayer) entityIn).sendStatusMessage(new TextComponentTranslation("cursedshroom.warning.message", new Object[0]), true);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Regenerates "+MushConfig.hearthRegenshroom+" half-hearts.");
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.LIGHT_PURPLE;
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		if(!world.isRemote && entLiv.getHealth() != entLiv.getMaxHealth()) {
			entLiv.heal(MushConfig.hearthRegenshroom);
			for(int i = 0; i <= 2; i++)
				MushPowers.instance.proxy.spawnShroomParticle(entLiv, ShroomParticle.HEARTH);
			return true;
		} else if(entLiv.getHealth() == entLiv.getMaxHealth()) {
			player.sendStatusMessage(new TextComponentTranslation("rodstick.fullhearth.message", new Object[0]), true);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean compatibleEntityBase(EntityLivingBase ent) {
		return super.compatibleEntityBase(ent) && ent instanceof EntityZombie || ent instanceof EntitySkeleton ? false : true;
	}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return ShroomParticle.HEARTH;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return true;
	}
	
}
