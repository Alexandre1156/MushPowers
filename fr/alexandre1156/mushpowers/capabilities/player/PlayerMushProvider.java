package fr.alexandre1156.mushpowers.capabilities.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerMushProvider implements ICapabilitySerializable<NBTBase>{

	@CapabilityInject(IPlayerMush.class)
	public static final Capability<IPlayerMush> MUSH_CAP = null;
	
	private IPlayerMush instance = MUSH_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == MUSH_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {	
		return capability == MUSH_CAP ? MUSH_CAP.<T>cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return MUSH_CAP.getStorage().writeNBT(MUSH_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		MUSH_CAP.getStorage().readNBT(MUSH_CAP, this.instance, null, nbt);
	}

}
