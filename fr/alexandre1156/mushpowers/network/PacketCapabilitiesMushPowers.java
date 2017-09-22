package fr.alexandre1156.mushpowers.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCapabilitiesMushPowers implements IMessage{

	private HashMap<String, Object> datas;
//	private boolean isSquid;
//	private int squidAir;
//	private short squidTimeLeft;
//	private byte shroomEaten;
//	private boolean isZombieAway;
//	private short zombieRunawayCooldown;
//	private boolean isHostile;
//	private short hostileCooldown;
//	private boolean isChicken;
//	private short chickenCooldown;
//	private boolean isGhost;
//	private short ghostCooldown;
//	private boolean isElectric;
//	private short electricCooldown;
//	private float shieldAbsorb;
//	private boolean isFlying;
//	private short flyCooldown;
//	private byte repairCostLeft;
	
	public PacketCapabilitiesMushPowers() {}
	
	public PacketCapabilitiesMushPowers(IPlayerMush capabilities){
		this.datas = capabilities.getDatas();
//		this.isSquid = capabilities.isSquid();
//		this.squidAir = capabilities.getSquidAir();
//		this.squidTimeLeft = capabilities.getCooldown(Mushs.SQUID);
//		this.shroomEaten = capabilities.getShroomCount();
//		this.isZombieAway = capabilities.isZombieAway();
//		this.zombieRunawayCooldown = capabilities.getCooldown(Mushs.ZOMBIEAWAY);
//		this.isHostile = capabilities.isHostile();
//		this.hostileCooldown = capabilities.getCooldown(Mushs.HOSTILE);
//		this.isChicken = capabilities.getValue("chicken", false);
//		this.chickenCooldown = capabilities.getValue("chickenCooldown", (short) 0);
//		this.isGhost = capabilities.isGhost();
//		this.ghostCooldown = capabilities.getCooldown(Mushs.GHOST);
//		this.isElectric = capabilities.isElectric();
//		this.electricCooldown = capabilities.getCooldown(Mushs.ELECTRIC);
//		this.shieldAbsorb = capabilities.getShieldDamage();
//		this.isFlying = capabilities.isFlying();
//		this.flyCooldown = capabilities.getCooldown(Mushs.FLY);
//		this.repairCostLeft = capabilities.getRepairCostLeft();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.datas = Maps.newHashMap();
		int sizeShort = buf.readInt();
		for(int i = 0; i < sizeShort; i++) {
			String key = ByteBufUtils.readUTF8String(buf);
			this.datas.put(key, buf.readShort());
		}
		int sizeInt = buf.readInt();
		for(int i = 0; i < sizeInt; i++) {
			String key = ByteBufUtils.readUTF8String(buf);
			this.datas.put(key, buf.readInt());
		}
		int sizeBoolean = buf.readInt();
		for(int i = 0; i < sizeBoolean; i++) {
			String key = ByteBufUtils.readUTF8String(buf);
			this.datas.put(key, buf.readBoolean());
		}
//		this.squidAir = buf.readInt();
//		this.isSquid = buf.readBoolean();
//		this.squidTimeLeft = buf.readShort();
//		this.shroomEaten = buf.readByte();
//		this.isZombieAway = buf.readBoolean();
//		this.zombieRunawayCooldown = buf.readShort();
//		this.isHostile = buf.readBoolean();
//		this.hostileCooldown = buf.readShort();
//		this.isChicken = buf.readBoolean();
//		this.chickenCooldown = buf.readShort();
//		this.isGhost = buf.readBoolean();
//		this.ghostCooldown = buf.readShort();
//		this.isElectric = buf.readBoolean();
//		this.electricCooldown = buf.readShort();
//		this.shieldAbsorb = buf.readFloat();
//		this.isFlying = buf.readBoolean();
//		this.flyCooldown = buf.readShort();
//		this.repairCostLeft = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
//		buf.writeInt(this.datas.size());
//		for(Entry<String, Object> entry : this.datas.entrySet()) {
//			if(entry.getValue() instanceof Integer) {
//				ByteBufUtils.writeUTF8String(buf, entry.getKey());
//				buf.writeInt((int) entry.getValue());
//			} else if(entry.getValue() instanceof Short) {
//				ByteBufUtils.writeUTF8String(buf, entry.getKey());
//				buf.writeShort((int) entry.getValue());
//			}else if(entry.getValue() instanceof Boolean) {
//				ByteBufUtils.writeUTF8String(buf, entry.getKey());
//				buf.writeBoolean((boolean) entry.getValue());
//			}
//		}
		
		Map<String, Object> mapShort = Maps.filterValues(this.datas, new Predicate<Object>() {
			public boolean apply(Object input) {
				return input instanceof Short;
			};
		});
		buf.writeInt(mapShort.size());
		for(Entry<String, Object> entryShort : mapShort.entrySet()) {
			ByteBufUtils.writeUTF8String(buf, entryShort.getKey());
			buf.writeShort((short) entryShort.getValue());
		}
		Map<String, Object> mapInt = Maps.filterValues(this.datas, new Predicate<Object>() {
			public boolean apply(Object input) {
				return input instanceof Integer;
			};
		});
		buf.writeInt(mapInt.size());
		for(Entry<String, Object> entryInteger : mapInt.entrySet()) {
			ByteBufUtils.writeUTF8String(buf, entryInteger.getKey());
			buf.writeInt((int) entryInteger.getValue());
		}
		Map<String, Object> mapBoolean = Maps.filterValues(this.datas, new Predicate<Object>() {
			public boolean apply(Object input) {
				return input instanceof Boolean;
			};
		});
		buf.writeInt(mapBoolean.size());
		for(Entry<String, Object> entryBoolean : mapBoolean.entrySet()) {
			ByteBufUtils.writeUTF8String(buf, entryBoolean.getKey());
			buf.writeBoolean((boolean) entryBoolean.getValue());
		}
		
//		buf.writeInt(this.squidAir);
//		buf.writeBoolean(this.isSquid);
//		buf.writeShort(this.squidTimeLeft);
//		buf.writeByte(this.shroomEaten);
//		buf.writeBoolean(this.isZombieAway);
//		buf.writeShort(this.zombieRunawayCooldown);
//		buf.writeBoolean(this.isHostile);
//		buf.writeShort(this.hostileCooldown);
//		buf.writeBoolean(this.isChicken);
//		buf.writeShort(this.chickenCooldown);
//		buf.writeBoolean(this.isGhost);
//		buf.writeShort(this.ghostCooldown);
//		buf.writeBoolean(this.isElectric);
//		buf.writeShort(this.electricCooldown);
//		buf.writeFloat(this.shieldAbsorb);
//		buf.writeBoolean(this.isFlying);
//		buf.writeShort(this.flyCooldown);
//		buf.writeByte(this.repairCostLeft);
	}
	
	public static class ClientHandler implements IMessageHandler<PacketCapabilitiesMushPowers, IMessage> {

		@Override
		public IMessage onMessage(final PacketCapabilitiesMushPowers message, MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(new Runnable(){

					@Override
					public void run() {
						IPlayerMush mush = Minecraft.getMinecraft().player.getCapability(PlayerMushProvider.MUSH_CAP, null);
						for(Entry<String, Object> entry : message.datas.entrySet())
							mush.addKey(entry.getKey(), entry.getValue());
//						mush.setSquid(message.isSquid);
//						mush.setSquidAir(message.squidAir);
//						mush.setCooldown(Mushs.SQUID, message.squidTimeLeft);
//						mush.setCooldown(Mushs.ZOMBIEAWAY, message.zombieRunawayCooldown);
//						mush.setCooldown(Mushs.HOSTILE, message.hostileCooldown);
//						//mush.setCooldown(Mushs.CHICKEN, message.chickenCooldown);
//						
//						mush.setValue("chickenCooldown", message.chickenCooldown);
//						
//						mush.setCooldown(Mushs.GHOST, message.ghostCooldown);
//						mush.setCooldown(Mushs.ELECTRIC, message.electricCooldown);
//						mush.setCooldown(Mushs.FLY, message.flyCooldown);
//						mush.setShroomCount(message.shroomEaten);
//						mush.setZombieAway(message.isZombieAway);
//						mush.setHostile(message.isHostile);
//						//mush.setChicken(message.isChicken);
//						
//						mush.setValue("chicken", message.isChicken);
//						
//						mush.setGhost(message.isGhost);
//						mush.setElectric(message.isElectric);
//						mush.setShieldDamageAbsorb(message.shieldAbsorb);
//						mush.setFly(message.isFlying);
//						mush.setLowerRepairCost(message.repairCostLeft);
					}
					
				});
			return null;
		}
		
	}

}
