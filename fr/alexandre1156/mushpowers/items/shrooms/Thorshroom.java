package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Thorshroom extends ItemFood {

	public Thorshroom() {
		super(1, 0.0f, false);
		this.func_77655_b("thorshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "thorshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
	}

}
