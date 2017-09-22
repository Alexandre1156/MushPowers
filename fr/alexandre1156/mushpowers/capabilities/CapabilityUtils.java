package fr.alexandre1156.mushpowers.capabilities;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.Flyshroom;
import fr.alexandre1156.mushpowers.network.PacketCapabilitiesMushPowers;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayer;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayer;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;

public class CapabilityUtils {
	
	public static void resetOtherMainMushPower(EntityLivingBase entLiv, Mushs power){
		IPlayerMush mushCap = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		for(Mushs mush : Mushs.values()) {
			if(!power.getId().equals(mush.getId()) && mush.isBig() && mush.getItemInstance() != null) {
				for(Entry<String, Object> entry : mushCap.getDatas().entrySet()) {
					for(Entry<String, Object> entry2 : mush.getItemInstance().getPersonnalDatas().entrySet()) {
						if(entry.getKey().equals(entry2.getKey())) {
							if(entry.getValue() instanceof Boolean)
								entry.setValue(false);
							else if(entry.getValue() instanceof Integer)
								entry.setValue((int) 0);
							else if(entry.getValue() instanceof Short)
								entry.setValue((short) 0);
						}
					}
				}
			}
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
		IPlayerMush mushCap = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		HashSet<String> clearShroom = new HashSet<>();
		for(Mushs mush : Mushs.values()) {
			if(mush.getItemInstance() != null) {
				if(isMilk && mush.getItemInstance() instanceof Flyshroom)
					continue;
				for(Entry<String, Object> entry : mushCap.getDatas().entrySet()) {
					for(Entry<String, Object> entry2 : mush.getItemInstance().getPersonnalDatas().entrySet()) {
						if(entry.getKey().equals(entry2.getKey())) {
							if(entry.getValue() instanceof Boolean) {
								if(((boolean) entry.getValue()) == true)
									clearShroom.add(mush.getItemInstance().getRegistryName().getResourcePath());
								entry.setValue(false);
							} else if(entry.getValue() instanceof Integer) {
								if(((int) entry.getValue()) != 0)
									clearShroom.add(mush.getItemInstance().getRegistryName().getResourcePath());
								entry.setValue((int) 0);
							} else if(entry.getValue() instanceof Short) {
								if(((Short) entry.getValue()) != 0)
									clearShroom.add(mush.getItemInstance().getRegistryName().getResourcePath());
								entry.setValue((short) 0);
							}
						}
					}
				}
			}
		}
		syncCapabilities(player);
		sendSquidPacket(player, false);
		sendGhostPacket(player, false);
		if(!clearShroom.isEmpty())
			player.sendStatusMessage(new TextComponentTranslation("capability.cleared.mushs", 
					MushUtils.startStringWithCapital(clearShroom.stream().collect(Collectors.joining(", ")))), true);
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
