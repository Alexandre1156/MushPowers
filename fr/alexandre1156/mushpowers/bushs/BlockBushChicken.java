package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushChicken extends BushMush {

	public BlockBushChicken() {
		super(CommonProxy.itemChickenshroom);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushchicken"));
		this.func_149663_c("bushchicken");
	}
	
}
