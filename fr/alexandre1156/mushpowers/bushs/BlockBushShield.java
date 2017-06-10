package fr.alexandre1156.mushpowers.bushs;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class BlockBushShield extends BushMush {

	public BlockBushShield() {
		super(CommonProxy.itemShieldshroom);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "bushshield"));
		this.func_149663_c("bushshield");
	}
	
}
