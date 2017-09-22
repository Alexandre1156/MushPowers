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

public class Electricshroom extends ItemMushPowers {

	public static final String IS_ELECTRIC = "electric";
	public static final String FLY_COOLDOWN = "electricCooldown";
	
	public Electricshroom() {
		super(1, 0.0F, "electricshroom");
		this.registerData("electric", Types.BOOLEAN);
		this.registerData("electricCooldown", Types.SHORT);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(this.IS_ELECTRIC, true);
			mush.setShort(this.FLY_COOLDOWN, MushConfig.getCooldown(Mushs.ELECTRIC));
			//mush.setCooldown(Mushs.ELECTRIC, MushConfig.getCooldown(Mushs.ELECTRIC));
			CapabilityUtils.resetOtherMainMushPower(player, Mushs.ELECTRIC);
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"You will move faster and when hitting with a sword you have a 10% chance of electrifying your target,\nmaking it unable to move and dealing 3 extra hearts of damage.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(Mushs.ELECTRIC)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.YELLOW;
		
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
		return Mushs.ELECTRIC;
	}

}
