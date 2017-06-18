package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class FlyshroomEvent extends ShroomEvent {

	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			PotionEffect potion = p.getActivePotionEffect(MobEffects.LEVITATION);
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(potion != null && potion.getDuration() >= 0) {
				mush.setCooldown(MainMushPowers.FLY, (short) potion.getDuration());
				PlayerMushProvider.syncCapabilities(p);
			} else if(potion == null && mush.getCooldown(MainMushPowers.FLY) == 1){
				mush.setCooldown(MainMushPowers.FLY, (short) 0);
				PlayerMushProvider.syncCapabilities(p);
			}
		}
	}
	
	@Override
	protected boolean onLivingEntityFall(Entity ent, EntityLivingBase entLiv, float distance, float damageMultiplier) {
		if(entLiv instanceof EntityPlayer && !entLiv.world.isRemote) {
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getCooldown(MainMushPowers.FLY) <= 0){
				mush.setFly(false);
				mush.setCooldown(MainMushPowers.FLY, (short) MushConfig.cooldownFly);
				PlayerMushProvider.syncCapabilities(p);
				return true;
			}
		}
		return super.onLivingEntityFall(ent, entLiv, distance, damageMultiplier);
	}

	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setFly(mush2.isFlying());
			mush.setCooldown(MainMushPowers.FLY, mush2.getCooldown(MainMushPowers.FLY));
		}
	}
	
	
	
}
