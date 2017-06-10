package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushCursed extends BushMush {

	public BlockBushCursed() {
		super(CommonProxy.itemCursedshroom);
		this.func_149663_c("bushcursed");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushcursed"));
	}
	
}
