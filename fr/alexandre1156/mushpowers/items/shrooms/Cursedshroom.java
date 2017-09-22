package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.capabilities.regen.RegenProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Cursedshroom extends ItemMushPowers {
	
	public Cursedshroom() {
		super(1, 0.0f, "cursedshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote) {
			int duration = this.itemRand.nextInt(2)+1 * 200;
			int level = this.itemRand.nextInt(1);
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration, level));
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration, level));
			player.addPotionEffect(new PotionEffect(MobEffects.POISON, duration, level));
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"Adds 3 negatives effects with random amplifier and duration when eat.\nShift + Left Click to transform to a Bad Regenshroom.");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
		if(Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())){
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			ItemStack regenshroom = new ItemStack(CommonProxy.itemRegenshroom, itemstack.getCount());
			regenshroom.getCapability(RegenProvider.REGEN_CAP, null).setBad(true);
			//((Regenshroom) regenshroom.getItem()).setBad();
			playerIn.setHeldItem(handIn, regenshroom);
			return new ActionResult<>(EnumActionResult.FAIL, itemstack);
		} else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.DARK_PURPLE;
		
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {return false;}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return null;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return false;
	}

	@Override
	protected Mushs getMushType() {
		return Mushs.CURSED;
	}
	
	

}
