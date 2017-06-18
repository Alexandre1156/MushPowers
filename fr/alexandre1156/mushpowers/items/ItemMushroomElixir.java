package fr.alexandre1156.mushpowers.items;

import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemMushroomElixir extends Item {

	public ItemMushroomElixir() {
		this.setUnlocalizedName("ime");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "ime"));
		this.setCreativeTab(CreativeTabs.MISC);
	}
	
}
