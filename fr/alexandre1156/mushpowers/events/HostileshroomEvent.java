package fr.alexandre1156.mushpowers.events;

import java.util.Iterator;

import fr.alexandre1156.mushpowers.EntityAINearestAttackablePlayerMush;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class HostileshroomEvent extends ShroomEvent {

	@Override
	protected void onEntityJoinWorld(Entity entity, World world) {
		if(entity instanceof EntityCreature && !(entity instanceof EntityZombie)) {
			final EntityCreature entCreature = (EntityCreature) entity;
			entCreature.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100D);
			EntityAIBase entryToDelete = null;
			Iterator<EntityAITaskEntry> iter = entCreature.field_70715_bh.field_75782_a.iterator();
			while(iter.hasNext()){
				EntityAITaskEntry entry = iter.next();
				if(entry.field_75733_a instanceof EntityAINearestAttackableTarget){
					Class target = ObfuscationReflectionHelper.getPrivateValue(EntityAINearestAttackableTarget.class, (EntityAINearestAttackableTarget) entry.field_75733_a, 0);
					if(target.isAssignableFrom(EntityPlayer.class)){
						entryToDelete = entry.field_75733_a;
						break;
					}
				}
			}
			entCreature.field_70715_bh.func_85156_a(entryToDelete);
			entCreature.field_70715_bh.func_75776_a(1, new EntityAINearestAttackablePlayerMush(entCreature));
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.field_70170_p.field_72995_K){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.isHostile()){
				if(mush.getCooldown(MainMushPowers.HOSTILE) % 1200 == 0)
					p.func_146105_b(new TextComponentTranslation("hostile.time.left.min", (mush.getCooldown(MainMushPowers.HOSTILE)/1200)), true);
				else if(mush.getCooldown(MainMushPowers.HOSTILE) == 600 || mush.getCooldown(MainMushPowers.HOSTILE) == 200 || mush.getCooldown(MainMushPowers.HOSTILE) == 180 || mush.getCooldown(MainMushPowers.HOSTILE) == 160 || mush.getCooldown(MainMushPowers.HOSTILE) == 140 || mush.getCooldown(MainMushPowers.HOSTILE) == 120 || mush.getCooldown(MainMushPowers.HOSTILE) == 100 || mush.getCooldown(MainMushPowers.HOSTILE) == 80 || mush.getCooldown(MainMushPowers.HOSTILE) == 60 || mush.getCooldown(MainMushPowers.HOSTILE) == 40 || mush.getCooldown(MainMushPowers.HOSTILE) == 20)
					p.func_146105_b(new TextComponentTranslation("hostile.time.left.sec", (mush.getCooldown(MainMushPowers.HOSTILE)/20)), true);
				if(mush.getCooldown(MainMushPowers.HOSTILE) <= 0) {
					mush.setCooldown(MainMushPowers.HOSTILE, (short) MushConfig.cooldownHostile);
					mush.setHostile(false);
					PlayerMushProvider.syncCapabilities(p);
					return;
				}
				mush.setCooldown(MainMushPowers.HOSTILE, (short) (mush.getCooldown(MainMushPowers.HOSTILE)-1));
			}
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setHostile(mush2.isHostile());
			mush.setCooldown(MainMushPowers.HOSTILE, mush2.getCooldown(MainMushPowers.HOSTILE));
		}
	}
	
}
