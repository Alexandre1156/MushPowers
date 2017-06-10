package fr.alexandre1156.mushpowers.events;

import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
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
		ClientProxy.ghostRender.func_188297_a(false);
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		//System.out.println("is "+p.getName()+" ghost ? : "+MushPowers.getInstance().isGhost(p.getDisplayNameString())+" - "+Minecraft.getMinecraft().player.getName());
		if(MushPowers.getInstance().isGhost(p.getDisplayNameString()) && !p.getDisplayNameString().equals(Minecraft.func_71410_x().field_71439_g.getDisplayNameString())){
			ClientProxy.ghostRender.func_76986_a((AbstractClientPlayer) p, x, y, z, p.field_70177_z, partialTick);
			if(this.canSeeGhostPlayers){
				//ClientProxy.ghostRender.getMainModel().setInvisible(true);
				p.func_82142_c(false);
				ClientProxy.ghostRender.func_188297_a(true);
			} else
				//ClientProxy.ghostRender.getMainModel().setInvisible(false);
				p.func_82142_c(true);
			return true;
		}
		if(mush.isGhost() && p.getDisplayNameString().equals(Minecraft.func_71410_x().field_71439_g.getDisplayNameString())) {
			ClientProxy.ghostRender.func_76986_a((AbstractClientPlayer) p, x, y, z, p.field_70177_z, partialTick);
			//ClientProxy.ghostRender.getMainModel().setInvisible(true);
			p.func_82142_c(true);
			return true;
		}
		if(this.canSeeGhostPlayers && !mush.isGhost()){
			if(MushPowers.getInstance().isGhost(p.getDisplayNameString()) && !p.getDisplayNameString().equals(Minecraft.func_71410_x().field_71439_g.getDisplayNameString())){
				//ClientProxy.ghostRender.getMainModel().setInvisible(true);
				p.func_82142_c(true);
				ClientProxy.ghostRender.func_188297_a(true);
			}
		}
		return super.onRenderPlayerPre(p, partialTick, x, y, z, render);
	}
	
	@Override
	protected boolean onRenderLivingSpecialsPre(EntityLivingBase ent) {
		if (ent instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) ent;
			//System.out.println(MushPowers.getInstance().isSquid(p.getDisplayNameString())+" "+p.getDisplayNameString());
			if (MushPowers.getInstance().isSquid(p.getDisplayNameString()))
				 return true;
		}
		return super.onRenderLivingSpecialsPre(ent);
	}
	
	@Override
	protected void onLivingEntityUseItemFinish(EntityLivingBase entLiv, ItemStack item, World world) {
		if(entLiv instanceof EntityPlayer){
			if(item.func_77973_b() == Items.field_151172_bF) {
				this.canSeeGhostPlayers = true;
				this.canSeeGhostsCooldown = 80;
			}
		}
	}
	
	@Override
	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {
		if(entLiv instanceof EntityPlayer && source.func_76346_g() instanceof EntityPlayer && !entLiv.field_70170_p.field_72995_K){
			EntityPlayer victim = (EntityPlayer) entLiv;
			EntityPlayer attacker = (EntityPlayer) source.func_76346_g();
			IPlayerMush mush = victim.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = attacker.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.isGhost()){
				if(mush2.isGhost()){
					mush.setGhost(false);
					mush2.setGhost(false);
					PlayerMushProvider.syncCapabilities(attacker);
					PlayerMushProvider.syncCapabilities(victim);
					PlayerMushProvider.sendGhostPacket(attacker, false);
					PlayerMushProvider.sendGhostPacket(victim, false);
					victim.func_82142_c(false);
					attacker.func_82142_c(false);
				} else {
					victim.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, victim.field_70165_t, victim.field_70163_u, victim.field_70161_v, 1.0D, 0.0D, 0.0D, new int[0]);
					victim.field_70170_p.func_184148_a((EntityPlayer)null, victim.field_70165_t, victim.field_70163_u, victim.field_70161_v, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0F, (1.0F + (victim.field_70170_p.field_73012_v.nextFloat() - victim.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
					victim.func_70606_j(0f);
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
		if(ent instanceof EntityPlayer && !ent.field_70170_p.field_72995_K){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.isGhost()){
				if(mush.getCooldown(MainMushPowers.GHOST) % 1200 == 0)
					p.func_146105_b(new TextComponentTranslation("ghost.time.left.min", (mush.getCooldown(MainMushPowers.GHOST)/1200)), true);
				else if(mush.getCooldown(MainMushPowers.GHOST) == 600 || mush.getCooldown(MainMushPowers.GHOST) == 200 || mush.getCooldown(MainMushPowers.GHOST) == 180 || mush.getCooldown(MainMushPowers.GHOST) == 160 || mush.getCooldown(MainMushPowers.GHOST) == 140 || mush.getCooldown(MainMushPowers.GHOST) == 120 || mush.getCooldown(MainMushPowers.GHOST) == 100 || mush.getCooldown(MainMushPowers.GHOST) == 80 || mush.getCooldown(MainMushPowers.GHOST) == 60 || mush.getCooldown(MainMushPowers.GHOST) == 40 || mush.getCooldown(MainMushPowers.GHOST) == 20)
					p.func_146105_b(new TextComponentTranslation("ghost.time.left.sec", (mush.getCooldown(MainMushPowers.GHOST)/20)), true);
				else if(mush.getCooldown(MainMushPowers.GHOST) == 2700)
					p.func_146105_b(new TextComponentTranslation("ghost.time.start", mush.getCooldown(MainMushPowers.GHOST)), true);
				if(mush.getCooldown(MainMushPowers.GHOST) <= 0) {
					mush.setCooldown(MainMushPowers.GHOST, (short) 2700);
					mush.setGhost(false);
					PlayerMushProvider.sendGhostPacket(p, false);
					PlayerMushProvider.syncCapabilities(p);
					return;
				}
				mush.setCooldown(MainMushPowers.GHOST, (short) (mush.getCooldown(MainMushPowers.GHOST)-1));
			}
			if(this.canSeeGhostPlayers && entLiv.field_70170_p.field_72995_K){
				this.canSeeGhostsCooldown--;
				if(this.canSeeGhostsCooldown <= 0)
					this.canSeeGhostPlayers = false;
			}
		}
	}
	
	@Override
	protected void onPlayerLoggedIn(EntityPlayer p) {
		if (p.field_70170_p.field_73010_i.size() > 1) {
			Iterator<EntityPlayer> playersList = p.field_70170_p.field_73010_i.iterator();
			HashMap<String, Boolean> ghostList = Maps.newHashMap();
			while (playersList.hasNext()) {
				EntityPlayer player = playersList.next();
				if (player.getCapability(PlayerMushProvider.MUSH_CAP, null).isGhost())
					ghostList.put(player.getDisplayNameString(), true);
				else
					ghostList.put(player.getDisplayNameString(), false);
			}
			MushPowers.network.sendTo(new PacketGhostPlayersList(ghostList), (EntityPlayerMP) p);
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if (death) {
			MushPowers.getInstance().getGhostPlayers().replace(p.func_70005_c_(), false);
			PlayerMushProvider.sendGhostPacket(pOriginal, false);
		} else {
			mush.setGhost(mush2.isGhost());
			mush.setCooldown(MainMushPowers.GHOST, mush2.getCooldown(MainMushPowers.GHOST));
		}
	}
	
	
}
