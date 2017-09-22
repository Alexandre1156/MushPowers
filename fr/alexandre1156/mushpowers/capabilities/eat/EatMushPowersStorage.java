package fr.alexandre1156.mushpowers.capabilities.eat;

import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EatMushPowersStorage implements IStorage<IEatMushPower> {

	@Override
	public NBTBase writeNBT(Capability<IEatMushPower> capability, IEatMushPower instance, EnumFacing side) {
		NBTTagList nbt = new NBTTagList();
		if(!instance.getAllEatenMushPowers().isEmpty()) {
			for(int i = 0; i < instance.getAllEatenMushPowers().size(); i++) 
				nbt.appendTag(new NBTTagString(instance.getAllEatenMushPowers().get(i).getId()));
		}
		return nbt;
	}

	@Override
	public void readNBT(Capability<IEatMushPower> capability, IEatMushPower instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagList) {
			NBTTagList nbtList = (NBTTagList) nbt;
			if(!nbtList.hasNoTags()) {
				for(int i = 0; i < nbtList.tagCount(); i++) 
					instance.addEatenMushPowers(Mushs.getMushsByID(nbtList.getStringTagAt(i)));
			}
		}
	}
	
}
