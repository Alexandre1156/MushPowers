package fr.alexandre1156.mushpowers.capabilities.eat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EatMushPowersProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IEatMushPower.class)
	public static final Capability<IEatMushPower> EAT_MUSHS_CAP = null;
	
	private IEatMushPower instance = EAT_MUSHS_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == EAT_MUSHS_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == EAT_MUSHS_CAP ? EAT_MUSHS_CAP.cast(instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return EAT_MUSHS_CAP.getStorage().writeNBT(EAT_MUSHS_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		EAT_MUSHS_CAP.getStorage().readNBT(EAT_MUSHS_CAP, this.instance, null, nbt);
	}
	
}
