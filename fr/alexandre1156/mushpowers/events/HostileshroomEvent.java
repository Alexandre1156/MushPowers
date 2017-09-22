package fr.alexandre1156.mushpowers.events;

import java.util.Iterator;

import fr.alexandre1156.mushpowers.EntityAINearestAttackablePlayerMush;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.Hostileshroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class HostileshroomEvent extends ShroomEvent {

	@Override
	protected void onEntityJoinWorld(Entity entity, World world) {
		if(entity instanceof EntityMob && !(entity instanceof EntityZombie)) {
			final EntityMob entCreature = (EntityMob) entity;
			entCreature.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100D);
			EntityAIBase entryToDelete = null;
			Iterator<EntityAITaskEntry> iter = entCreature.targetTasks.taskEntries.iterator();
			while(iter.hasNext()){
				EntityAITaskEntry entry = iter.next();
				if(entry.action instanceof EntityAINearestAttackableTarget){
					Class<EntityLivingBase> target = ObfuscationReflectionHelper.getPrivateValue(EntityAINearestAttackableTarget.class, (EntityAINearestAttackableTarget) entry.action, 0);
					if(target.isAssignableFrom(EntityPlayer.class)){
						entryToDelete = entry.action;
						break;
					}
				}
			}
			if(entryToDelete != null) {
				entCreature.targetTasks.removeTask(entryToDelete);
				entCreature.targetTasks.addTask(1, new EntityAINearestAttackablePlayerMush(entCreature));
			}
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.world.isRemote){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getBoolean(Hostileshroom.IS_HOSTILE)){
				if(mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) % 1200 == 0)
					p.sendStatusMessage(new TextComponentTranslation("hostile.time.left.min", (mush.getShort(Hostileshroom.HOSTILE_COOLDOWN)/1200)), true);
				else if(mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 600 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 200 ||
						mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 180 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 160 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 140 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 120 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 100 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 80 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 60 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 40 || mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) == 20)
					p.sendStatusMessage(new TextComponentTranslation("hostile.time.left.sec", (mush.getShort(Hostileshroom.HOSTILE_COOLDOWN)/20)), true);
				if(mush.getShort(Hostileshroom.HOSTILE_COOLDOWN) <= 0) {
					mush.setShort(Hostileshroom.HOSTILE_COOLDOWN, (short) 0);
					mush.setBoolean(Hostileshroom.IS_HOSTILE, false);
					CapabilityUtils.syncCapabilities(p);
					return;
				}
				mush.setShort(Hostileshroom.HOSTILE_COOLDOWN, (short) (mush.getShort(Hostileshroom.HOSTILE_COOLDOWN)-1));
			}
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(Hostileshroom.IS_HOSTILE, mush2.getBoolean(Hostileshroom.IS_HOSTILE));
			mush.setShort(Hostileshroom.HOSTILE_COOLDOWN, mush2.getShort(Hostileshroom.HOSTILE_COOLDOWN));
		}
	}
	
}
