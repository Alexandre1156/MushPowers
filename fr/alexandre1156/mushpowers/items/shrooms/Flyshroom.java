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

public class Flyshroom extends ItemFood {

	public Flyshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("flyshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "flyshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
		PotionEffect potionEffect;
		this.func_185070_a(potionEffect = new PotionEffect(MobEffects.field_188424_y, MushConfig.cooldownFly, 0, false, false), 1.0f);
		List<ItemStack> curative = Lists.<ItemStack>newArrayList();
		curative.add(new ItemStack(Items.field_190931_a));
		potionEffect.setCurativeItems(curative);
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K) {
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setFly(true);
			mush.setCooldown(MainMushPowers.FLY, (short) MushConfig.cooldownFly);
			PlayerMushProvider.syncCapabilities(player);
		}
		super.func_77849_c(stack, worldIn, player);
	}
	
	@Override
	public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.func_184586_b(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.func_77659_a(worldIn, playerIn, handIn);
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"You will levitate for 1 minute and the fall damage will be absorbed.\nThe bad part is than milk cannot clear this shroom.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.cooldownFly));
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

}
