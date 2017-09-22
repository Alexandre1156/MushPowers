package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.collect.Lists;
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
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Flyshroom extends ItemMushPowers {

	public static final String IS_FLY = "fly";
	public static final String FLY_COOLDOWN = "flyCooldown";
	
	public Flyshroom() {
		super(1, 0.0f, "flyshroom");
		this.registerData("fly", Types.BOOLEAN);
		this.registerData("flyCooldown", Types.SHORT);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote) {
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(this.IS_FLY, true);
			mush.setShort(this.FLY_COOLDOWN, MushConfig.getCooldown(Mushs.FLY));
			PotionEffect potionEffect;
			player.addPotionEffect(potionEffect = new PotionEffect(MobEffects.LEVITATION, MushConfig.getCooldown(Mushs.FLY), 0, false, false));
			List<ItemStack> curative = Lists.<ItemStack>newArrayList();
			curative.add(new ItemStack(Items.AIR));
			potionEffect.setCurativeItems(curative);
			CapabilityUtils.syncCapabilities(player);
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"You will levitate for 1 minute and the fall damage will be absorbed.\nThe bad part is than milk cannot clear this shroom.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(Mushs.FLY)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.WHITE;
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setBoolean(this.IS_FLY, true);
		mush.setShort(this.FLY_COOLDOWN, MushConfig.getCooldown(Mushs.FLY));
		if(!entLiv.world.isRemote)
			entLiv.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, MushConfig.getCooldown(Mushs.FLY), 0, false, false));
		CapabilityUtils.resetOtherMainMushPower(entLiv, Mushs.FLY);
		return true;
	}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return ShroomParticle.FLY;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return true;
	}

	@Override
	protected Mushs getMushType() {
		return Mushs.FLY;
	}

}
