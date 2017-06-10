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

public class PacketGhostPlayersList implements IMessage {
	
	private HashMap<String, Boolean> playersGhostList;
	private int size;

	public PacketGhostPlayersList() {}
	
	public PacketGhostPlayersList(HashMap<String, Boolean> ghostPlayersList) {
		this.playersGhostList = ghostPlayersList;
		this.size = ghostPlayersList.size();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.size = buf.readInt();
		this.playersGhostList = Maps.newHashMap();
		for(int i = 0; i < size; i++)
			this.playersGhostList.put(ByteBufUtils.readUTF8String(buf), buf.readBoolean());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.size);
		Iterator<Entry<String, Boolean>> iter = this.playersGhostList.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, Boolean> entry = iter.next();
			ByteBufUtils.writeUTF8String(buf, entry.getKey());
			buf.writeBoolean(entry.getValue());
		}
	}
	
	public static class Handler implements IMessageHandler<PacketGhostPlayersList, IMessage> {

		@Override
		public IMessage onMessage(PacketGhostPlayersList message, MessageContext ctx) {
			Iterator<Entry<String, Boolean>> iter = message.playersGhostList.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, Boolean> entry = iter.next();
				MushPowers.getInstance().addGhostPlayer(entry.getKey(), entry.getValue());
			}
			return null;
		}
		
	}

}
