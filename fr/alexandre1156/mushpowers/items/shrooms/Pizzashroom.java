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

public class Pizzashroom extends ItemMushPowers {

	public static final String PIZZA_COUNT = "pizzacount";
	
	public Pizzashroom() {
		super(2, 1.2f, "pizzashroom");
		this.registerData("pizzacount", Types.INTEGER);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!worldIn.isRemote){
			mush.setInteger(this.PIZZA_COUNT, MushConfig.foodCountPizzashroom);
			CapabilityUtils.syncCapabilities(player);
			super.onFoodEaten(stack, worldIn, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"Each food you eat will restore "+MushConfig.foodRegenPizzashroom+" more half-hunger point.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts after eating "+MushConfig.foodCountPizzashroom+" foods");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.GRAY;
		
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
		return Mushs.PIZZA;
	}
	

}
