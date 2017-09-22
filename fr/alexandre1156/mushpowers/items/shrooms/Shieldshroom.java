package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

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

public class Shieldshroom extends ItemMushPowers {

	public static final String DAMAGE_ABSORB = "damageabsorb";
	
	public Shieldshroom() {
		super(1, 0.0f, "shieldshroom");
		this.registerData("damageabsorb", Types.INTEGER);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setInteger(this.DAMAGE_ABSORB, MushConfig.maxDamageAbsorbShieldshroom);
			CapabilityUtils.syncCapabilities(player);
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"Absorbs "+MushConfig.damageAbsordPercentShieldshroom+"% of the damage taken.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts when absorbing "+MushConfig.maxDamageAbsorbShieldshroom+" half-heaths of damage");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.GRAY;
		
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setInteger(this.DAMAGE_ABSORB, MushConfig.maxDamageAbsorbShieldshroom);
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

	@Override
	protected Mushs getMushType() {
		return Mushs.SHIELD;
	}

}
