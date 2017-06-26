package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Flyshroom extends ItemMushPowers {

	public Flyshroom() {
		super(1, 0.0f, "flyshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote) {
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setFly(true);
			mush.setCooldown(MainMushPowers.FLY, MushConfig.getCooldown(MainMushPowers.FLY));
			PotionEffect potionEffect;
			player.addPotionEffect(potionEffect = new PotionEffect(MobEffects.LEVITATION, MushConfig.getCooldown(MainMushPowers.FLY), 0, false, false));
			List<ItemStack> curative = Lists.<ItemStack>newArrayList();
			curative.add(new ItemStack(Items.AIR));
			potionEffect.setCurativeItems(curative);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"You will levitate for 1 minute and the fall damage will be absorbed.\nThe bad part is than milk cannot clear this shroom.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(MainMushPowers.FLY)));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.WHITE;
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setFly(true);
		mush.setCooldown(MainMushPowers.FLY, (short) (MushConfig.getCooldown(MainMushPowers.FLY)*2));
		if(!entLiv.world.isRemote)
			entLiv.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, MushConfig.getCooldown(MainMushPowers.FLY), 0, false, false));
		PlayerMushProvider.resetOtherMainMushPower(entLiv, MainMushPowers.FLY);
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

}
