package fr.alexandre1156.mushpowers.network;

import fr.alexandre1156.mushpowers.MushPowers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSquidPlayer implements IMessage{

	private String username;
	private boolean isSquid;
	
	public PacketSquidPlayer() {}
	
	public PacketSquidPlayer(String username, boolean isSquid){
		this.username = username;
		this.isSquid = isSquid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.username = ByteBufUtils.readUTF8String(buf);
		this.isSquid = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, username);
		buf.writeBoolean(isSquid);
	}
	
	public static class Handler implements IMessageHandler<PacketSquidPlayer, IMessage> {

		@Override
		public IMessage onMessage(PacketSquidPlayer message, MessageContext ctx) {
			MushPowers.getInstance().addSquidPlayer(message.username, message.isSquid);
			return null;
		}
		
	}

}
