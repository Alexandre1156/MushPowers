package fr.alexandre1156.mushpowers.network;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCapabilitiesMushPowers implements IMessage{

	private boolean isSquid;
	private int squidAir;
	private short squidTimeLeft;
	private byte shroomEaten;
	private boolean isZombieAway;
	private short zombieRunawayCooldown;
	private boolean isHostile;
	private short hostileCooldown;
	private boolean isChicken;
	private short chickenCooldown;
	private boolean isGhost;
	private short ghostCooldown;
	private boolean isElectric;
	private short electricCooldown;
	private float shieldAbsorb;
	private boolean isFlying;
	private short flyCooldown;
	private byte repairCostLeft;
	
	public PacketCapabilitiesMushPowers() {}
	
	public PacketCapabilitiesMushPowers(IPlayerMush capabilities){
		this.isSquid = capabilities.isSquid();
		this.squidAir = capabilities.getSquidAir();
		this.squidTimeLeft = capabilities.getCooldown(MainMushPowers.SQUID);
		this.shroomEaten = capabilities.getShroomCount();
		this.isZombieAway = capabilities.isZombieAway();
		this.zombieRunawayCooldown = capabilities.getCooldown(MainMushPowers.ZOMBIEAWAY);
		this.isHostile = capabilities.isHostile();
		this.hostileCooldown = capabilities.getCooldown(MainMushPowers.HOSTILE);
		this.isChicken = capabilities.isChicken();
		this.chickenCooldown = capabilities.getCooldown(MainMushPowers.CHICKEN);
		this.isGhost = capabilities.isGhost();
		this.ghostCooldown = capabilities.getCooldown(MainMushPowers.GHOST);
		this.isElectric = capabilities.isElectric();
		this.electricCooldown = capabilities.getCooldown(MainMushPowers.ELECTRIC);
		this.shieldAbsorb = capabilities.getShieldDamage();
		this.isFlying = capabilities.isFlying();
		this.flyCooldown = capabilities.getCooldown(MainMushPowers.FLY);
		this.repairCostLeft = capabilities.getRepairCostLeft();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.squidAir = buf.readInt();
		this.isSquid = buf.readBoolean();
		this.squidTimeLeft = buf.readShort();
		this.shroomEaten = buf.readByte();
		this.isZombieAway = buf.readBoolean();
		this.zombieRunawayCooldown = buf.readShort();
		this.isHostile = buf.readBoolean();
		this.hostileCooldown = buf.readShort();
		this.isChicken = buf.readBoolean();
		this.chickenCooldown = buf.readShort();
		this.isGhost = buf.readBoolean();
		this.ghostCooldown = buf.readShort();
		this.isElectric = buf.readBoolean();
		this.electricCooldown = buf.readShort();
		this.shieldAbsorb = buf.readFloat();
		this.isFlying = buf.readBoolean();
		this.flyCooldown = buf.readShort();
		this.repairCostLeft = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.squidAir);
		buf.writeBoolean(this.isSquid);
		buf.writeShort(this.squidTimeLeft);
		buf.writeByte(this.shroomEaten);
		buf.writeBoolean(this.isZombieAway);
		buf.writeShort(this.zombieRunawayCooldown);
		buf.writeBoolean(this.isHostile);
		buf.writeShort(this.hostileCooldown);
		buf.writeBoolean(this.isChicken);
		buf.writeShort(this.chickenCooldown);
		buf.writeBoolean(this.isGhost);
		buf.writeShort(this.ghostCooldown);
		buf.writeBoolean(this.isElectric);
		buf.writeShort(this.electricCooldown);
		buf.writeFloat(this.shieldAbsorb);
		buf.writeBoolean(this.isFlying);
		buf.writeShort(this.flyCooldown);
		buf.writeByte(this.repairCostLeft);
	}
	
	public static class ClientHandler implements IMessageHandler<PacketCapabilitiesMushPowers, IMessage> {

		@Override
		public IMessage onMessage(final PacketCapabilitiesMushPowers message, MessageContext ctx) {
				Minecraft.func_71410_x().func_152344_a(new Runnable(){

					@Override
					public void run() {
						IPlayerMush mush = Minecraft.func_71410_x().field_71439_g.getCapability(PlayerMushProvider.MUSH_CAP, null);
						mush.setSquid(message.isSquid);
						mush.setSquidAir(message.squidAir);
						mush.setCooldown(MainMushPowers.SQUID, message.squidTimeLeft);
						mush.setCooldown(MainMushPowers.ZOMBIEAWAY, message.zombieRunawayCooldown);
						mush.setCooldown(MainMushPowers.HOSTILE, message.hostileCooldown);
						mush.setCooldown(MainMushPowers.CHICKEN, message.chickenCooldown);
						mush.setCooldown(MainMushPowers.GHOST, message.ghostCooldown);
						mush.setCooldown(MainMushPowers.ELECTRIC, message.electricCooldown);
						mush.setCooldown(MainMushPowers.FLY, message.flyCooldown);
						mush.setShroomCount(message.shroomEaten);
						mush.setZombieAway(message.isZombieAway);
						mush.setHostile(message.isHostile);
						mush.setChicken(message.isChicken);
						mush.setGhost(message.isGhost);
						mush.setElectric(message.isElectric);
						mush.setShieldDamageAbsorb(message.shieldAbsorb);
						mush.setFly(message.isFlying);
						mush.setLowerRepairCost(message.repairCostLeft);
					}
					
				});
			return null;
		}
		
	}

}
