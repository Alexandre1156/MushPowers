package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Shieldshroom extends ItemMushPowers {

	public Shieldshroom() {
		super(1, 0.0f, "shieldshroom");
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
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Absorbs "+MushConfig.damageAbsordPercentShieldshroom+"% of the damage taken.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts when absorbing "+MushConfig.maxDamageAbsorbShieldshroom+" half-heaths of damage");
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.GRAY;
		
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setShieldDamageAbsorb((byte) MushConfig.maxDamageAbsorbShieldshroom);
		return true;
	}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return ShroomParticle.SHIELD;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return true;
	}

}
