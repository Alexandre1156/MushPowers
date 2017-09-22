package fr.alexandre1156.mushpowers.events;

import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.Ghostshroom;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayersList;
import fr.alexandre1156.mushpowers.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class GhostshroomEvent extends ShroomEvent {
	
	private boolean canSeeGhostPlayers;
	private int canSeeGhostsCooldown;
	
	@Override
	protected boolean onRenderPlayerPre(EntityPlayer p, float partialTick, double x, double y, double z, RenderPlayer render) {
		ClientProxy.ghostRender.setRenderOutlines(false);
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(MushPowers.getInstance().isGhost(p.getName()) && !p.getName().equals(Minecraft.getMinecraft().player.getName())){
			ClientProxy.ghostRender.doRender((AbstractClientPlayer) p, x, y, z, p.rotationYaw, partialTick);
			if(this.canSeeGhostPlayers){
				p.setInvisible(false);
				ClientProxy.ghostRender.setRenderOutlines(true);
			} else
				p.setInvisible(true);
			return true;
		}
		if(mush.getBoolean(Ghostshroom.IS_GHOST) && p.getName().equals(Minecraft.getMinecraft().player.getName())) {
			ClientProxy.ghostRender.doRender((AbstractClientPlayer) p, x, y, z, p.rotationYaw, partialTick);
			p.setInvisible(true);
			return true;
		} else
			p.setInvisible(false);
		if(this.canSeeGhostPlayers && !mush.getBoolean(Ghostshroom.IS_GHOST)){
			if(MushPowers.getInstance().isGhost(p.getName()) && !p.getName().equals(Minecraft.getMinecraft().player.getName())){
				p.setInvisible(true);
				ClientProxy.ghostRender.setRenderOutlines(true);
			}
		}
		return super.onRenderPlayerPre(p, partialTick, x, y, z, render);
	}
	
	@Override
	protected boolean onRenderLivingSpecialsPre(EntityLivingBase ent) {
		if (ent instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) ent;
			if (MushPowers.getInstance().isSquid(p.getName()))
				 return true;
		}
		return super.onRenderLivingSpecialsPre(ent);
	}
	
	@Override
	protected void onLivingEntityUseItemFinish(EntityLivingBase entLiv, ItemStack item, World world) {
		if(entLiv instanceof EntityPlayer){
			if(item.getItem() == Items.CARROT) {
				this.canSeeGhostPlayers = true;
				this.canSeeGhostsCooldown = 80;
			}
		}
	}
	
	@Override
	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {
		if(entLiv instanceof EntityPlayer && source.getImmediateSource() instanceof EntityPlayer && !entLiv.world.isRemote){
			EntityPlayer victim = (EntityPlayer) entLiv;
			EntityPlayer attacker = (EntityPlayer) source.getImmediateSource();
			IPlayerMush mush = victim.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = attacker.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getBoolean(Ghostshroom.IS_GHOST)) {
				if(mush2.getBoolean(Ghostshroom.IS_GHOST)){
					mush.setBoolean(Ghostshroom.IS_GHOST, false);
					mush2.setBoolean(Ghostshroom.IS_GHOST, false);
					CapabilityUtils.syncCapabilities(attacker);
					CapabilityUtils.syncCapabilities(victim);
					CapabilityUtils.sendGhostPacket(attacker, false);
					CapabilityUtils.sendGhostPacket(victim, false);
					victim.setInvisible(false);
					attacker.setInvisible(false);
				} else {
					victim.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, victim.posX, victim.posY, victim.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
					victim.world.playSound((EntityPlayer)null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (victim.world.rand.nextFloat() - victim.world.rand.nextFloat()) * 0.2F) * 0.7F);
					victim.setHealth(0f);
				}
			}
		}
		return super.onLivingHurt(entLiv, source, amount);
	}
	
	@Override
	protected void onClientDisconnectionFromServer() {
		MushPowers.getInstance().getGhostPlayers().clear();
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.world.isRemote){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getBoolean(Ghostshroom.IS_GHOST)){
				if(mush.getShort(Ghostshroom.GHOST_COOLDOWN) % 1200 == 0)
					p.sendStatusMessage(new TextComponentTranslation("ghost.time.left.min", (mush.getShort(Ghostshroom.GHOST_COOLDOWN)/1200)), true);
				else if(mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 600 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 200 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 180 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 160 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 140 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 120 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 100 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 80 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 60 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 40 || mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 20)
					p.sendStatusMessage(new TextComponentTranslation("ghost.time.left.sec", (mush.getShort(Ghostshroom.GHOST_COOLDOWN)/20)), true);
				else if(mush.getShort(Ghostshroom.GHOST_COOLDOWN) == 2700)
					p.sendStatusMessage(new TextComponentTranslation("ghost.time.start", mush.getShort(Ghostshroom.GHOST_COOLDOWN)), true);
				if(mush.getShort(Ghostshroom.GHOST_COOLDOWN) <= 0) {
					mush.setShort(Ghostshroom.GHOST_COOLDOWN, (short) 0);
					mush.setBoolean(Ghostshroom.IS_GHOST, false);
					CapabilityUtils.sendGhostPacket(p, false);
					CapabilityUtils.syncCapabilities(p);
					return;
				}
				mush.setShort(Ghostshroom.GHOST_COOLDOWN, (short) (mush.getShort(Ghostshroom.GHOST_COOLDOWN)-1));
			}
			if(this.canSeeGhostPlayers && entLiv.world.isRemote){
				this.canSeeGhostsCooldown--;
				if(this.canSeeGhostsCooldown <= 0)
					this.canSeeGhostPlayers = false;
			}
		}
	}
	
	@Override
	protected void onPlayerLoggedIn(EntityPlayer p) {
		if (p.world.playerEntities.size() > 1) {
			Iterator<EntityPlayer> playersList = p.world.playerEntities.iterator();
			HashMap<String, Boolean> ghostList = Maps.newHashMap();
			while (playersList.hasNext()) {
				EntityPlayer player = playersList.next();
				if (player.getCapability(PlayerMushProvider.MUSH_CAP, null).getBoolean(Ghostshroom.IS_GHOST))
					ghostList.put(player.getName(), true);
				else
					ghostList.put(player.getName(), false);
			}
			MushPowers.network.sendTo(new PacketGhostPlayersList(ghostList), (EntityPlayerMP) p);
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if (death) {
			MushPowers.getInstance().getGhostPlayers().replace(p.getName(), false);
			CapabilityUtils.sendGhostPacket(pOriginal, false);
		} else {
			mush.setBoolean(Ghostshroom.IS_GHOST, mush2.getBoolean(Ghostshroom.IS_GHOST));
			mush.setShort(Ghostshroom.GHOST_COOLDOWN, mush2.getShort(Ghostshroom.GHOST_COOLDOWN));
		}
	}
	
	
}
