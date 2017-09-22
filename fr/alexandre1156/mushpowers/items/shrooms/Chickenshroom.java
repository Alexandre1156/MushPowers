package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Chickenshroom extends ItemMushPowers {

	public static final String IS_CHICKEN = "chicken";
	public static final String CHICKEN_COOLDOWN	= "chickenCooldown";
	
	public Chickenshroom() {
		super(1, 0.0f, "chickenshroom");
		this.registerData("chicken", Types.BOOLEAN);
		this.registerData("chickenCooldown", Types.SHORT);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			//mush.setChicken(true);
			mush.setBoolean(this.IS_CHICKEN, true);
			mush.setShort(this.CHICKEN_COOLDOWN, MushConfig.getCooldown(Mushs.SQUID));
			//mush.setCooldown(Mushs.CHICKEN, MushConfig.getCooldown(Mushs.SQUID));
			CapabilityUtils.resetOtherMainMushPower(player, Mushs.CHICKEN);
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"When falling, you will slow down, like a chicken. \nUnfortunately, you will move slower.");
		tooltip.add(ChatFormatting.GREEN+""+TextFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(Mushs.CHICKEN)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.YELLOW;
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setValue("chicken", true);
		mush.setValue("chickenCooldown", MushConfig.getCooldown(Mushs.CHICKEN)*2);
//		mush.setChicken(true);
//		mush.setCooldown(Mushs.CHICKEN, (short) (MushConfig.getCooldown(Mushs.CHICKEN)*2));
		CapabilityUtils.resetOtherMainMushPower(entLiv, Mushs.CHICKEN);
		return true;
	}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return ShroomParticle.CHICKEN;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return true;
	}

	@Override
	protected Mushs getMushType() {
		return Mushs.CHICKEN;
	}

}
