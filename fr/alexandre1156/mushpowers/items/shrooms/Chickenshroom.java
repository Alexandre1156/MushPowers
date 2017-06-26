package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Chickenshroom extends ItemMushPowers {

	public Chickenshroom() {
		super(1, 0.0f, "chickenshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setChicken(true);
			mush.setCooldown(MainMushPowers.CHICKEN, MushConfig.getCooldown(MainMushPowers.SQUID));
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.CHICKEN);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"When falling, you will slow down, like a chicken. \nUnfortunately, you will move slower.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(MainMushPowers.CHICKEN)));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.YELLOW;
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setChicken(true);
		mush.setCooldown(MainMushPowers.CHICKEN, (short) (MushConfig.getCooldown(MainMushPowers.CHICKEN)*2));
		PlayerMushProvider.resetOtherMainMushPower(entLiv, MainMushPowers.CHICKEN);
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

}
