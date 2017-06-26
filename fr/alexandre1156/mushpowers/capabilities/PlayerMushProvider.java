package fr.alexandre1156.mushpowers.capabilities;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.network.PacketCapabilitiesMushPowers;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayer;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
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
	
	public static void resetOtherMainMushPower(EntityLivingBase entLiv, MainMushPowers power){
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(mush.isChicken() && power != MainMushPowers.CHICKEN) { 
			mush.setChicken(false); 
			mush.setCooldown(MainMushPowers.CHICKEN, (short) 0);
		}
		if(mush.isGhost() && power != MainMushPowers.GHOST) { 
			mush.setGhost(false); 
			mush.setCooldown(MainMushPowers.GHOST, MushConfig.getCooldown(MainMushPowers.GHOST));
			if(entLiv instanceof EntityPlayer)
				sendGhostPacket((EntityPlayer) entLiv, false);
		}
		if(mush.isHostile() && power != MainMushPowers.HOSTILE) { mush.setHostile(false); mush.setCooldown(MainMushPowers.HOSTILE, MushConfig.getCooldown(MainMushPowers.HOSTILE));}
		if(mush.isSquid() && power != MainMushPowers.SQUID) { 
			mush.setSquid(false); 
			mush.setSquidAir(6000); 
			mush.setCooldown(MainMushPowers.SQUID, MushConfig.getCooldown(MainMushPowers.SQUID));
			if(entLiv instanceof EntityPlayer)
				sendSquidPacket((EntityPlayer) entLiv, false);
		}
		if(mush.isZombieAway() && power != MainMushPowers.ZOMBIEAWAY) { mush.setZombieAway(false); mush.setCooldown(MainMushPowers.ZOMBIEAWAY, MushConfig.getCooldown(MainMushPowers.ZOMBIEAWAY));}
		if(mush.isElectric() && power != MainMushPowers.ELECTRIC) {mush.setElectric(false); mush.setCooldown(MainMushPowers.ELECTRIC, MushConfig.getCooldown(MainMushPowers.ELECTRIC));}
		if(mush.isFlying() && power != MainMushPowers.FLY) {
			mush.setFly(false);
			mush.setCooldown(MainMushPowers.FLY, (short) 0);
			entLiv.removeActivePotionEffect(MobEffects.LEVITATION);
		}
		if(entLiv instanceof EntityPlayer) syncCapabilities((EntityPlayer) entLiv);
	}
	
	public static void syncCapabilities(EntityPlayer entLiv){
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		PacketCapabilitiesMushPowers packetCapabilities = new PacketCapabilitiesMushPowers(mush);
		if(!entLiv.world.isRemote && entLiv instanceof EntityPlayerMP)
			MushPowers.network.sendTo(packetCapabilities, (EntityPlayerMP) entLiv);
	}
	
	public static void resetPlayer(EntityPlayer player, boolean isMilk){
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		mush.setSquid(false);
		mush.setSquidAir(300);
		mush.setCooldown(MainMushPowers.SQUID, MushConfig.getCooldown(MainMushPowers.SQUID));
		mush.setCooldown(MainMushPowers.ZOMBIEAWAY, MushConfig.getCooldown(MainMushPowers.ZOMBIEAWAY));
		mush.setCooldown(MainMushPowers.HOSTILE, MushConfig.getCooldown(MainMushPowers.HOSTILE));
		mush.setShroomCount((byte) 0);
		mush.setZombieAway(false);
		mush.setHostile(false);
		mush.setChicken(false);
		mush.setCooldown(MainMushPowers.CHICKEN, MushConfig.getCooldown(MainMushPowers.CHICKEN));
		mush.setGhost(false);
		mush.setCooldown(MainMushPowers.GHOST, MushConfig.getCooldown(MainMushPowers.GHOST));
		mush.setElectric(false);
		mush.setCooldown(MainMushPowers.ELECTRIC, MushConfig.getCooldown(MainMushPowers.ELECTRIC));
		mush.setShieldDamageAbsorb((byte) 0);
		if(!isMilk) {
			mush.setFly(false);
			mush.setCooldown(MainMushPowers.FLY, MushConfig.getCooldown(MainMushPowers.FLY)); 
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
