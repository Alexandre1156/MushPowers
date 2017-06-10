package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.RegenProvider;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Cursedshroom extends ItemFood {
	
	public Cursedshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("cursedshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "cursedshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K) {
			int duration = this.field_77697_d.nextInt(2)+1 * 200;
			int level = this.field_77697_d.nextInt(1);
			player.func_70690_d(new PotionEffect(MobEffects.field_76431_k, duration, level));
			player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, duration, level));
			player.func_70690_d(new PotionEffect(MobEffects.field_76436_u, duration, level));
		}
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Adds 3 negatives effects with random amplifier and duration when eat.\nShift + Left Click to transform to a Bad Regenshroom.");
	}
	
	@Override
	public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(Keyboard.isKeyDown(Minecraft.func_71410_x().field_71474_y.field_74311_E.func_151463_i())){
			ItemStack itemstack = playerIn.func_184586_b(handIn);
			ItemStack regenshroom = new ItemStack(CommonProxy.itemRegenshroom, itemstack.func_190916_E());
			regenshroom.getCapability(RegenProvider.REGEN_CAP, null).setBad(true);
			//((Regenshroom) regenshroom.getItem()).setBad();
			playerIn.func_184611_a(handIn, regenshroom);
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		} else
			return super.func_77659_a(worldIn, playerIn, handIn);
	}
	
	

}
