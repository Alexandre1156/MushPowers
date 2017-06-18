package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushElectric extends BushMush {

	public BlockBushElectric() {
		super(CommonProxy.itemElectricshroom);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushelectric"));
		this.setUnlocalizedName("bushelectric");
	}
	
}
