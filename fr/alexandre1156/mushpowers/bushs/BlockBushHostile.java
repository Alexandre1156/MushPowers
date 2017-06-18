package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushHostile extends BushMush {

	public BlockBushHostile() {
		super(CommonProxy.itemHostileshroom);
		this.setUnlocalizedName("bushhostile");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushhostile"));
	}
	
}
