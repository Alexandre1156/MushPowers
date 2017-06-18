package fr.alexandre1156.mushpowers.events;

import java.util.Iterator;

import com.google.common.base.Predicate;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
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
			
			zombie.tasks.addTask(1, new EntityAIAvoidEntity(zombie, EntityPlayer.class, new Predicate<Entity>() {

				@Override
				public boolean apply(Entity input) {
					if(input instanceof EntityPlayer) {
						return ((EntityPlayer) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isZombieAway();
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
			if(mush.isZombieAway() && !p.world.isRemote){
				if(mush.getCooldown(MainMushPowers.ZOMBIEAWAY) % 1200 == 0)
					p.sendStatusMessage(new TextComponentTranslation("zombierunaway.time.left.min", (mush.getCooldown(MainMushPowers.ZOMBIEAWAY)/1200)).setStyle(new Style().setColor(TextFormatting.AQUA)), true);
				else if(mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 600 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 200 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 180 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 160 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 140 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 120 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 100 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 80 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 60 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 40 || mush.getCooldown(MainMushPowers.ZOMBIEAWAY) == 20)
					p.sendStatusMessage(new TextComponentTranslation("zombierunaway.time.left.sec", (mush.getCooldown(MainMushPowers.ZOMBIEAWAY)/20)).setStyle(new Style().setColor(TextFormatting.DARK_AQUA)), true);
				if(mush.getCooldown(MainMushPowers.ZOMBIEAWAY) <= 0) {
					mush.setCooldown(MainMushPowers.ZOMBIEAWAY, (short) MushConfig.cooldownZombieAway);
					mush.setZombieAway(false);
					PlayerMushProvider.syncCapabilities(p);
					return;
				}
				mush.setCooldown(MainMushPowers.ZOMBIEAWAY, (short) (mush.getCooldown(MainMushPowers.ZOMBIEAWAY)-1));
			}
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!death) {
			mush.setCooldown(MainMushPowers.ZOMBIEAWAY, mush2.getCooldown(MainMushPowers.ZOMBIEAWAY));
			mush.setZombieAway(mush2.isZombieAway());
		}
	}

}
