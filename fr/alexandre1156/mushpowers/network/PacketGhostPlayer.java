package fr.alexandre1156.mushpowers.network;

import fr.alexandre1156.mushpowers.MushPowers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGhostPlayer implements IMessage {
	
	private String username;
	private boolean value;
	
	public PacketGhostPlayer() {}
	
	public PacketGhostPlayer(String username, boolean value) {
		this.username = username;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.username = ByteBufUtils.readUTF8String(buf);
		this.value = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, username);
		buf.writeBoolean(value);
	}
	
	public static class Handler implements IMessageHandler<PacketGhostPlayer, IMessage> {

		@Override
		public IMessage onMessage(PacketGhostPlayer message, MessageContext ctx) {
			MushPowers.getInstance().addGhostPlayer(message.username, message.value);
			return null;
		}
		
	}

}
