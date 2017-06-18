package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushGhost extends BushMush {

	public BlockBushGhost() {
		super(CommonProxy.itemGhostshroom);
		this.setUnlocalizedName("bushghost");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushghost"));
	}
	
}
