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

public class Ghostshroom extends ItemMushPowers {

	public Ghostshroom() {
		super(1, 0.0f, "ghostshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setGhost(true);
			mush.setCooldown(MainMushPowers.GHOST, (short) MushConfig.getCooldown(MainMushPowers.GHOST));
			PlayerMushProvider.sendGhostPacket(player, true);
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.GHOST);
			PlayerMushProvider.syncCapabilities(player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"You will be 99% invisible. \n"
				+ "Particles around you wont show, "
				+ "the item you hold wont show,\n"
				+ "however if a player eats a carrot it will be able to see ghosts for 5 seconds.\n"
				+ "If a normal player hits you, "
				+ "you will cause a small explosion that doesn't damage blocks or players or ghosts, "
				+ "but insta-kills you\n. If you are being hit by a ghost, both you and the ghost that hit you will show.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(MainMushPowers.GHOST))+" if no ghost/player hit you");
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.WHITE;
		
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

}
