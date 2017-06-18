package fr.alexandre1156.mushpowers.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class RegenStorage implements IStorage<IRegen> {

	@Override
	public NBTBase writeNBT(Capability<IRegen> capability, IRegen instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("bad", instance.isBad());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IRegen> capability, IRegen instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound nbtTag = (NBTTagCompound) nbt;
		instance.setBad(nbtTag.getBoolean("bad"));
	}

}
