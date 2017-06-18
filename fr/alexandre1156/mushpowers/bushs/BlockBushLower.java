package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushLower extends BushMush {

	public BlockBushLower() {
		super(CommonProxy.itemLowershroom);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushlower"));
		this.setUnlocalizedName("bushlower");
	}
	
}
