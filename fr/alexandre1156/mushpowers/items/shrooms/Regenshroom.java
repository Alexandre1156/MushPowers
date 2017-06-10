package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.RegenProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Regenshroom extends ItemFood {

	
	public Regenshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("regenshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "regenshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K){
			if(!stack.getCapability(RegenProvider.REGEN_CAP, null).isBad())
				player.func_70691_i(6f);
			else {
				int duration = this.field_77697_d.nextInt(2)+1 * 200;
				int level = this.field_77697_d.nextInt(1);
				player.func_70690_d(new PotionEffect(MobEffects.field_76431_k, duration, level));
				player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, duration, level));
				player.func_70690_d(new PotionEffect(MobEffects.field_76436_u, duration, level));
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.func_184586_b(handIn);
		if(playerIn.func_110143_aJ() >= 20f)
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.func_77659_a(worldIn, playerIn, handIn);
	}
	
	@Override
	public void func_77663_a(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//		System.out.println(isSelected+" "+entityIn+" "+MushPowers.cursedShroomViewers.contains(entityIn.getName())+" "+!entityIn.world.isRemote+" - "+
//				stack.getCapability(RegenProvider.REGEN_CAP, null).isBad());
		if(isSelected && entityIn instanceof EntityPlayer && MushPowers.cursedShroomViewers.contains(entityIn.func_70005_c_()) && !entityIn.field_70170_p.field_72995_K 
				&& stack.getCapability(RegenProvider.REGEN_CAP, null).isBad())
			((EntityPlayer) entityIn).func_146105_b(new TextComponentTranslation("cursedshroom.warning.message", new Object[0]), true);
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Regenerates 3 hearts.");
	}
	
}
