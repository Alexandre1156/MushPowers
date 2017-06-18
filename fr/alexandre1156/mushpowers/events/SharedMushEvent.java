package fr.alexandre1156.mushpowers.events;

import com.google.common.base.Predicate;

import fr.alexandre1156.mushpowers.EntityAINearestAttackablePlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SharedMushEvent extends ShroomEvent {
	
	@Override
	protected void onEntityJoinWorld(Entity entity, World world) {
		if(entity instanceof EntityZombie){
			EntityZombie zombie = (EntityZombie) entity;
			zombie.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100D);
			//ZombieawayShroom and Hostileshroom
			zombie.targetTasks.addTask(3, new EntityAINearestAttackablePlayerMush(zombie, new Predicate<Entity>() {

				@Override
				public boolean apply(Entity input) {
					if(input instanceof EntityPlayer)
						return !((EntityPlayer) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isZombieAway();
					else
						return false;
				}
			}));
		}
	}
	
	@Override
	protected void onLivingEntityUseItemTick(int duration, EntityLivingBase entLiv, ItemStack item) {
		if(entLiv instanceof EntityPlayer && !entLiv.world.isRemote && item.getItem() == Items.MILK_BUCKET && duration <= 1)
			PlayerMushProvider.resetPlayer((EntityPlayer) entLiv, true);
	}
		
	@Override //SYNC CAPABILITIES SERVER TO CLIENT
	protected void onPlayerLoggedIn(EntityPlayer p) {
		PlayerMushProvider.syncCapabilities(p);
	}

	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		//USELESS
	}
	
}
