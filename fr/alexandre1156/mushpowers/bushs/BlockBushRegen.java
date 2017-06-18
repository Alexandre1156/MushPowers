package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushRegen extends BushMush {

	public BlockBushRegen() {
		super(CommonProxy.itemRegenshroom);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushregen"));
		this.setUnlocalizedName("bushregen");
	}
	
}
