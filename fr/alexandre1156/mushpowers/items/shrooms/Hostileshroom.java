package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Hostileshroom extends ItemFood {

	public Hostileshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("hostileshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "hostileshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K){
			//List<EntityCreature> ent = worldIn.getEntities(EntityCreature.class, Predicates.alwaysTrue());
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setHostile(true);
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setCooldown(MainMushPowers.HOSTILE, (short) MushConfig.cooldownHostile);
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.HOSTILE);
			PlayerMushProvider.syncCapabilities(player);
		}
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
		tooltip.add(ChatFormatting.WHITE+"Hostile mobs will target you even if you are 100 blocks away");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.cooldownHostile));
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

}
