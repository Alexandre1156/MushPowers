package fr.alexandre1156.mushpowers.items;

import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemMushroomElixir extends Item {

	public ItemMushroomElixir() {
		this.func_77655_b("ime");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "ime"));
		this.func_77637_a(CreativeTabs.field_78026_f);
	}
	
}
