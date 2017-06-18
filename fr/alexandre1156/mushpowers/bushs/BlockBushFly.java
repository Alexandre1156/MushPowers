package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushFly extends BushMush {

	public BlockBushFly() {
		super(CommonProxy.itemFlyshroom);
		this.setUnlocalizedName("bushfly");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushfly"));
	}
	
}
