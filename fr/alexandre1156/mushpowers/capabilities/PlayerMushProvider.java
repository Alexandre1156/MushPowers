package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.network.PacketCapabilitiesMushPowers;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayer;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	
	public static void resetOtherMainMushPower(EntityPlayer p, MainMushPowers power){
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(mush.isChicken() && power != MainMushPowers.CHICKEN) { mush.setChicken(false); mush.setCooldown(MainMushPowers.CHICKEN, (short) MushConfig.cooldownChicken);}
		if(mush.isGhost() && power != MainMushPowers.GHOST) { 
			mush.setGhost(false); 
			mush.setCooldown(MainMushPowers.GHOST, (short) MushConfig.cooldownGhost);
			sendGhostPacket(p, false);
		}
		if(mush.isHostile() && power != MainMushPowers.HOSTILE) { mush.setHostile(false); mush.setCooldown(MainMushPowers.HOSTILE, (short) MushConfig.cooldownHostile);}
		if(mush.isSquid() && power != MainMushPowers.SQUID) { 
			mush.setSquid(false); 
			mush.setSquidAir(6000); 
			mush.setCooldown(MainMushPowers.SQUID, (short) MushConfig.cooldownSquid);
			sendSquidPacket(p, false);
		}
		if(mush.isZombieAway() && power != MainMushPowers.ZOMBIEAWAY) { mush.setZombieAway(false); mush.setCooldown(MainMushPowers.ZOMBIEAWAY, (short) MushConfig.cooldownZombieAway);}
		if(mush.isElectric() && power != MainMushPowers.ELECTRIC) {mush.setElectric(false); mush.setCooldown(MainMushPowers.ELECTRIC, (short) MushConfig.cooldownElectric);}
		syncCapabilities(p);
	}
	
	public static void syncCapabilities(EntityPlayer player){
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		PacketCapabilitiesMushPowers packetCapabilities = new PacketCapabilitiesMushPowers(mush);
		if(!player.world.isRemote && player instanceof EntityPlayerMP)
			MushPowers.network.sendTo(packetCapabilities, (EntityPlayerMP) player);
	}
	
	public static void resetPlayer(EntityPlayer player, boolean isMilk){
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setSquid(false);
		mush.setSquidAir(300);
		mush.setCooldown(MainMushPowers.SQUID, (short) MushConfig.cooldownSquid);
		mush.setCooldown(MainMushPowers.ZOMBIEAWAY, (short) MushConfig.cooldownZombieAway);
		mush.setCooldown(MainMushPowers.HOSTILE, (short) MushConfig.cooldownHostile);
		mush.setShroomCount((byte) 0);
		mush.setZombieAway(false);
		mush.setHostile(false);
		mush.setChicken(false);
		mush.setCooldown(MainMushPowers.CHICKEN, (short) MushConfig.cooldownChicken);
		mush.setGhost(false);
		mush.setCooldown(MainMushPowers.GHOST, (short) MushConfig.cooldownGhost);
		mush.setElectric(false);
		mush.setCooldown(MainMushPowers.ELECTRIC, (short) MushConfig.cooldownElectric);
		mush.setShieldDamageAbsorb((byte) 0);
		if(!isMilk) {
			mush.setFly(false);
			mush.setCooldown(MainMushPowers.FLY, (short) MushConfig.cooldownFly); 
		}
		mush.setLowerRepairCost((byte) 0);
		syncCapabilities(player);
		sendSquidPacket(player, false);
		sendGhostPacket(player, false);
	}
	
	public static void sendSquidPacket(EntityPlayer player, boolean isSquid){
		PacketSquidPlayer packetSquids = new PacketSquidPlayer(player.getDisplayNameString(), isSquid);
		if(!player.world.isRemote && player instanceof EntityPlayerMP)
			MushPowers.network.sendToAll(packetSquids);
	}
	
	public static void sendGhostPacket(EntityPlayer player, boolean isGhost){
		PacketGhostPlayer packet = new PacketGhostPlayer(player.getDisplayNameString(), isGhost);
		if(!player.world.isRemote && player instanceof EntityPlayerMP)
			MushPowers.network.sendToAll(packet);
	}

}
