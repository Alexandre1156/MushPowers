package fr.alexandre1156.mushpowers.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.MushPowers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSquidPlayersList implements IMessage{

	private HashMap<String, Boolean> squidPlayers;
	private int playersConnectedNumber;
	
	public PacketSquidPlayersList() {}
	
	public PacketSquidPlayersList(HashMap<String, Boolean> squidPlayers){
		this.squidPlayers = Maps.newHashMap();
		this.squidPlayers = squidPlayers;
		this.playersConnectedNumber = squidPlayers.size();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.playersConnectedNumber = buf.readInt();
		this.squidPlayers = Maps.newHashMap();
		for(int i = 0; i < this.playersConnectedNumber; i++)
			this.squidPlayers.put(ByteBufUtils.readUTF8String(buf), buf.readBoolean());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(playersConnectedNumber);
		Iterator<Entry<String, Boolean>> iter = this.squidPlayers.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, Boolean> entry = iter.next();
			ByteBufUtils.writeUTF8String(buf, entry.getKey());
			buf.writeBoolean(entry.getValue());
		}
	}
	
	public static class Handler implements IMessageHandler<PacketSquidPlayersList, IMessage> {

		@Override
		public IMessage onMessage(PacketSquidPlayersList message, MessageContext ctx) {
			Iterator<Entry<String, Boolean>> iter = message.squidPlayers.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, Boolean> entry = iter.next();
				MushPowers.getInstance().addSquidPlayer(entry.getKey(), entry.getValue());
			}
			return null;
		}
		
	}

}
