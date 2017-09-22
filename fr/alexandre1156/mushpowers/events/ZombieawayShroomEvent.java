package fr.alexandre1156.mushpowers.events;

import java.util.Iterator;

import com.google.common.base.Predicate;

import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.ZombieawayShroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ZombieawayShroomEvent extends ShroomEvent {
	
	@Override
	protected void onEntityJoinWorld(Entity entity, World world) {
		if (entity instanceof EntityZombie) {
			EntityZombie zombie = (EntityZombie) entity;
			Iterator<EntityAITaskEntry> iter = zombie.targetTasks.taskEntries.iterator();
			while(iter.hasNext()){
				EntityAITaskEntry ai = iter.next();
				if(ai.action instanceof EntityAINearestAttackableTarget){
					Class<EntityLivingBase> target = ObfuscationReflectionHelper.getPrivateValue(EntityAINearestAttackableTarget.class, (EntityAINearestAttackableTarget) ai.action, 0);
					if(target.isAssignableFrom(EntityPlayer.class)){
						iter.remove();
						break;
					}
				}
			}
			zombie.tasks.addTask(1, new EntityAIAvoidEntity<EntityPlayer>(zombie, EntityPlayer.class, new Predicate<Entity>() {

				@Override
				public boolean apply(Entity input) {
					if(input instanceof EntityPlayer) {
						return ((EntityPlayer) input).getCapability(PlayerMushProvider.MUSH_CAP, null).getBoolean(ZombieawayShroom.IS_ZOMBIEAWAY);
					} else
						return false;
				}
			
			}, 50f, 1D, 1.2D));
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(entLiv instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getBoolean(ZombieawayShroom.IS_ZOMBIEAWAY) && !p.world.isRemote){
				if(mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) % 1200 == 0)
					p.sendStatusMessage(new TextComponentTranslation("zombierunaway.time.left.min", (mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN)/1200)).setStyle(new Style().setColor(TextFormatting.AQUA)), true);
				else if(mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 600 || (mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 200) || (mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 180) || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 160 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 140 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 120 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 100 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 80 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 60 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 40 || mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) == 20)
					p.sendStatusMessage(new TextComponentTranslation("zombierunaway.time.left.sec", (mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN)/20)).setStyle(new Style().setColor(TextFormatting.DARK_AQUA)), true);
				if(mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN) <= 0) {
					mush.setShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN, (short) 0);
					mush.setBoolean(ZombieawayShroom.IS_ZOMBIEAWAY, false);
					CapabilityUtils.syncCapabilities(p);
					return;
				}
				mush.setShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN, (short) (mush.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN)-1));
			}
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!death) {
			mush.setShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN, mush2.getShort(ZombieawayShroom.ZOMBIEAWAY_COOLDOWN));
			mush.setBoolean(ZombieawayShroom.IS_ZOMBIEAWAY, mush2.getBoolean(ZombieawayShroom.IS_ZOMBIEAWAY));
		}
	}

}
