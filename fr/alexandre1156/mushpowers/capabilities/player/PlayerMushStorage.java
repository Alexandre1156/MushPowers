package fr.alexandre1156.mushpowers.capabilities.player;

import java.util.Map.Entry;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerMushStorage implements IStorage<IPlayerMush>{

	@Override
	public NBTBase writeNBT(Capability<IPlayerMush> capability, IPlayerMush instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		
		for(Entry<String, Object> entry : instance.getDatas().entrySet()) {
			if(entry.getValue() instanceof Boolean)
				nbt.setBoolean(entry.getKey(), (boolean) entry.getValue());
			if(entry.getValue() instanceof Short)
				nbt.setShort(entry.getKey(), (short) entry.getValue());
			if(entry.getValue() instanceof Integer)
				nbt.setInteger(entry.getKey(), (int) entry.getValue());
		}
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPlayerMush> capability, IPlayerMush instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagCompound){
			NBTTagCompound nbtTag = (NBTTagCompound) nbt;
			
			for(String key : nbtTag.getKeySet()) {
				NBTBase value = nbtTag.getTag(key);
				if(value instanceof NBTTagShort)
					instance.addKey(key, ((NBTTagShort) value).getShort());
				else if(value instanceof NBTTagByte)
					instance.addKey(key, ((NBTTagByte) value).getByte() == 1 ? true : false);
				else if(value instanceof NBTTagInt)
					instance.addKey(key, ((NBTTagInt) value).getInt());
			}
		}
	}

}
