package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushSquid extends BushMush {

	public BlockBushSquid() {
		super(CommonProxy.itemSquidshroom);
		this.func_149663_c("bushsquid");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushsquid"));
	}
	
	
	
}
