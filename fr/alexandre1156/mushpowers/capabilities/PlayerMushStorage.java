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
		nbt.func_74757_a("isSquid", instance.isSquid());
		nbt.func_74768_a("squidAir", instance.getSquidAir());
		nbt.func_74777_a("squidCooldown", instance.getCooldown(MainMushPowers.SQUID));
		nbt.func_74774_a("numberShroomEaten", instance.getShroomCount());
		nbt.func_74757_a("zombieRunAway", instance.isZombieAway());
		nbt.func_74777_a("zombieRunAwayCooldown", instance.getCooldown(MainMushPowers.ZOMBIEAWAY));
		nbt.func_74757_a("hostile", instance.isHostile());
		nbt.func_74777_a("hostileCooldown", instance.getCooldown(MainMushPowers.HOSTILE));
		nbt.func_74757_a("chicken", instance.isChicken());
		nbt.func_74777_a("chickenCooldown", instance.getCooldown(MainMushPowers.CHICKEN));
		nbt.func_74757_a("ghost", instance.isGhost());
		nbt.func_74777_a("ghostCooldown", instance.getCooldown(MainMushPowers.GHOST));
		nbt.func_74757_a("electric", instance.isElectric());
		nbt.func_74777_a("electricCooldown", instance.getCooldown(MainMushPowers.ELECTRIC));
		nbt.func_74776_a("shieldDamage", instance.getShieldDamage());
		nbt.func_74757_a("fly", instance.isFlying());
		nbt.func_74777_a("flyCooldown", instance.getCooldown(MainMushPowers.FLY));
		nbt.func_74774_a("repairCost", instance.getRepairCostLeft());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPlayerMush> capability, IPlayerMush instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagByte) return;
		if(nbt instanceof NBTTagCompound){
			NBTTagCompound nbtTag = (NBTTagCompound) nbt;
			instance.setSquid(nbtTag.func_74767_n("isSquid"));
			instance.setSquidAir(nbtTag.func_74762_e("squidAir"));
			instance.setCooldown(MainMushPowers.SQUID, nbtTag.func_74765_d("squidCooldown"));
			instance.setShroomCount(nbtTag.func_74771_c("numberShroomEaten"));
			instance.setZombieAway(nbtTag.func_74767_n("zombieRunAway"));
			instance.setCooldown(MainMushPowers.ZOMBIEAWAY, nbtTag.func_74765_d("zombieRunAwayCooldown"));
			instance.setHostile(nbtTag.func_74767_n("hostile"));
			instance.setCooldown(MainMushPowers.HOSTILE, nbtTag.func_74765_d("hostileCooldown"));
			instance.setGhost(nbtTag.func_74767_n("ghost"));
			instance.setCooldown(MainMushPowers.GHOST, nbtTag.func_74765_d("ghostCooldown"));
			instance.setChicken(nbtTag.func_74767_n("chicken"));
			instance.setCooldown(MainMushPowers.CHICKEN, nbtTag.func_74765_d("chickenCooldown"));
			instance.setElectric(nbtTag.func_74767_n("electric"));
			instance.setCooldown(MainMushPowers.ELECTRIC, nbtTag.func_74765_d("electricCooldown"));
			instance.setShieldDamageAbsorb(nbtTag.func_74760_g("shieldDamage"));
			instance.setFly(nbtTag.func_74767_n("fly"));
			instance.setCooldown(MainMushPowers.FLY, nbtTag.func_74765_d("flyCooldown"));
			instance.setLowerRepairCost(nbtTag.func_74771_c("repairCost"));
		}
	}

}
