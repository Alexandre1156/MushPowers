package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushPizza extends BushMush {

	public BlockBushPizza() {
		super(CommonProxy.itemPizzashroom);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushpizza"));
		this.setUnlocalizedName("bushpizza");
	}
	
}
