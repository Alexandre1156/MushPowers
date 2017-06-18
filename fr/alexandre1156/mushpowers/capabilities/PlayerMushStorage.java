package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerMushStorage implements IStorage<IPlayerMush>{

	@Override
	public NBTBase writeNBT(Capability<IPlayerMush> capability, IPlayerMush instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("isSquid", instance.isSquid());
		nbt.setInteger("squidAir", instance.getSquidAir());
		nbt.setShort("squidCooldown", instance.getCooldown(MainMushPowers.SQUID));
		nbt.setByte("numberShroomEaten", instance.getShroomCount());
		nbt.setBoolean("zombieRunAway", instance.isZombieAway());
		nbt.setShort("zombieRunAwayCooldown", instance.getCooldown(MainMushPowers.ZOMBIEAWAY));
		nbt.setBoolean("hostile", instance.isHostile());
		nbt.setShort("hostileCooldown", instance.getCooldown(MainMushPowers.HOSTILE));
		nbt.setBoolean("chicken", instance.isChicken());
		nbt.setShort("chickenCooldown", instance.getCooldown(MainMushPowers.CHICKEN));
		nbt.setBoolean("ghost", instance.isGhost());
		nbt.setShort("ghostCooldown", instance.getCooldown(MainMushPowers.GHOST));
		nbt.setBoolean("electric", instance.isElectric());
		nbt.setShort("electricCooldown", instance.getCooldown(MainMushPowers.ELECTRIC));
		nbt.setFloat("shieldDamage", instance.getShieldDamage());
		nbt.setBoolean("fly", instance.isFlying());
		nbt.setShort("flyCooldown", instance.getCooldown(MainMushPowers.FLY));
		nbt.setByte("repairCost", instance.getRepairCostLeft());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPlayerMush> capability, IPlayerMush instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagByte) return;
		if(nbt instanceof NBTTagCompound){
			NBTTagCompound nbtTag = (NBTTagCompound) nbt;
			instance.setSquid(nbtTag.getBoolean("isSquid"));
			instance.setSquidAir(nbtTag.getInteger("squidAir"));
			instance.setCooldown(MainMushPowers.SQUID, nbtTag.getShort("squidCooldown"));
			instance.setShroomCount(nbtTag.getByte("numberShroomEaten"));
			instance.setZombieAway(nbtTag.getBoolean("zombieRunAway"));
			instance.setCooldown(MainMushPowers.ZOMBIEAWAY, nbtTag.getShort("zombieRunAwayCooldown"));
			instance.setHostile(nbtTag.getBoolean("hostile"));
			instance.setCooldown(MainMushPowers.HOSTILE, nbtTag.getShort("hostileCooldown"));
			instance.setGhost(nbtTag.getBoolean("ghost"));
			instance.setCooldown(MainMushPowers.GHOST, nbtTag.getShort("ghostCooldown"));
			instance.setChicken(nbtTag.getBoolean("chicken"));
			instance.setCooldown(MainMushPowers.CHICKEN, nbtTag.getShort("chickenCooldown"));
			instance.setElectric(nbtTag.getBoolean("electric"));
			instance.setCooldown(MainMushPowers.ELECTRIC, nbtTag.getShort("electricCooldown"));
			instance.setShieldDamageAbsorb(nbtTag.getFloat("shieldDamage"));
			instance.setFly(nbtTag.getBoolean("fly"));
			instance.setCooldown(MainMushPowers.FLY, nbtTag.getShort("flyCooldown"));
			instance.setLowerRepairCost(nbtTag.getByte("repairCost"));
		}
	}

}
