package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Flyshroom extends ItemFood {

	public Flyshroom() {
		super(1, 0.0f, false);
		this.setUnlocalizedName("flyshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "flyshroom"));
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setAlwaysEdible();
		PotionEffect potionEffect;
		this.setPotionEffect(potionEffect = new PotionEffect(MobEffects.LEVITATION, MushConfig.cooldownFly, 0, false, false), 1.1f);
		List<ItemStack> curative = Lists.<ItemStack>newArrayList();
		curative.add(new ItemStack(Items.AIR));
		potionEffect.setCurativeItems(curative);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote) {
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setFly(true);
			mush.setCooldown(MainMushPowers.FLY, (short) MushConfig.cooldownFly);
			PlayerMushProvider.syncCapabilities(player);
		}
		super.onFoodEaten(stack, worldIn, player);
		System.out.println(player.getActivePotionEffects()+" "+ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, this, 5));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"You will levitate for 1 minute and the fall damage will be absorbed.\nThe bad part is than milk cannot clear this shroom.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.cooldownFly));
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

}
