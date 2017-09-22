package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.Flyshroom;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class FlyshroomEvent extends ShroomEvent {

	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
		PotionEffect potion = entLiv.getActivePotionEffect(MobEffects.LEVITATION);
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			if(potion != null && potion.getDuration() >= 0) 
				mush.setShort(Flyshroom.FLY_COOLDOWN, (short) potion.getDuration());
			else if(potion == null && mush.getShort(Flyshroom.FLY_COOLDOWN) == 1)
				mush.setShort(Flyshroom.FLY_COOLDOWN, (short) 0);
		} else {
			if(potion != null && potion.getDuration() >= 0) {
				mush.setShort(Flyshroom.FLY_COOLDOWN, (short) potion.getDuration());
				MushPowers.proxy.spawnShroomParticle(entLiv, ShroomParticle.FLY);
			} else if(potion == null && mush.getShort(Flyshroom.FLY_COOLDOWN) == 1)
				mush.setShort(Flyshroom.FLY_COOLDOWN, (short) 0);
		}
		
		if(mush.getBoolean(Flyshroom.IS_FLY) && potion == null && mush.getShort(Flyshroom.FLY_COOLDOWN) <= 0 && entLiv.onGround) {
			mush.setBoolean(Flyshroom.IS_FLY, false);
			mush.setShort(Flyshroom.FLY_COOLDOWN, (short) 0); 
			if(entLiv instanceof EntityPlayer)
				CapabilityUtils.syncCapabilities((EntityPlayer) entLiv);
		}
	}
	
	@Override
	protected boolean onLivingEntityFall(Entity ent, EntityLivingBase entLiv, float distance, float damageMultiplier) {
		if(!entLiv.world.isRemote && entLiv instanceof EntityPlayer) {
			IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getShort(Flyshroom.FLY_COOLDOWN) <= 0){
				mush.setBoolean(Flyshroom.IS_FLY, false);
				mush.setShort(Flyshroom.FLY_COOLDOWN, (short) 0);
				CapabilityUtils.syncCapabilities((EntityPlayer) entLiv);
				return true;
			}
		} else if(!entLiv.world.isRemote){
			IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getShort(Flyshroom.FLY_COOLDOWN) <= 0){
				mush.setBoolean(Flyshroom.IS_FLY, false);
				mush.setShort(Flyshroom.FLY_COOLDOWN, (short) 0);
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
			mush.setBoolean(Flyshroom.IS_FLY, mush2.getBoolean(Flyshroom.IS_FLY));
			mush.setShort(Flyshroom.FLY_COOLDOWN, mush2.getShort(Flyshroom.FLY_COOLDOWN));
		}
	}
	
	
	
}
