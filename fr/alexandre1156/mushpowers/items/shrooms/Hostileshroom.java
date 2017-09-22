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

public class Hostileshroom extends ItemMushPowers {

	public static final String IS_HOSTILE = "hostile";
	public static final String HOSTILE_COOLDOWN = "hostileCooldown";
	
	public Hostileshroom() {
		super(1, 0.0f, "hostileshroom");
		this.registerData("hostile", Types.BOOLEAN);
		this.registerData("hostileCooldown", Types.SHORT);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(this.IS_HOSTILE, true);
			mush.setShort(this.HOSTILE_COOLDOWN, MushConfig.getCooldown(Mushs.HOSTILE));
			//List<EntityCreature> ent = worldIn.getEntities(EntityCreature.class, Predicates.alwaysTrue());
//			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setHostile(true);
//			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setCooldown(Mushs.HOSTILE, MushConfig.getCooldown(Mushs.HOSTILE));
			CapabilityUtils.resetOtherMainMushPower(player, Mushs.HOSTILE);
			CapabilityUtils.syncCapabilities(player);
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"Hostile mobs will target you even if you are 100 blocks away");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(Mushs.HOSTILE)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.DARK_GREEN;
		
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
		return Mushs.HOSTILE;
	}

}
