package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
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
			if(potion != null && potion.getDuration() >= 0) {
				mush.setCooldown(MainMushPowers.FLY, (short) potion.getDuration());
			} else if(potion == null && mush.getCooldown(MainMushPowers.FLY) == 1){
				mush.setCooldown(MainMushPowers.FLY, (short) 0);
			}
		} else {
			if(potion != null && potion.getDuration() >= 0) {
				mush.setCooldown(MainMushPowers.FLY, (short) potion.getDuration());
				MushPowers.proxy.spawnShroomParticle(entLiv, ShroomParticle.FLY);
			} else if(potion == null && mush.getCooldown(MainMushPowers.FLY) == 1)
				mush.setCooldown(MainMushPowers.FLY, (short) 0);
		}
		
		if(mush.isFlying() && potion == null && mush.getCooldown(MainMushPowers.FLY) <= 0 && entLiv.onGround) {
			mush.setFly(false);
			if(entLiv instanceof EntityPlayer)
				PlayerMushProvider.syncCapabilities((EntityPlayer) entLiv);
		}
	}
	
	@Override
	protected boolean onLivingEntityFall(Entity ent, EntityLivingBase entLiv, float distance, float damageMultiplier) {
		if(!entLiv.world.isRemote && entLiv instanceof EntityPlayer) {
			System.out.println("LOL");
			IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getCooldown(MainMushPowers.FLY) <= 0){
				mush.setFly(false);
				mush.setCooldown(MainMushPowers.FLY, (short) 0);
				PlayerMushProvider.syncCapabilities((EntityPlayer) entLiv);
				return true;
			}
		} else if(!entLiv.world.isRemote){
			IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getCooldown(MainMushPowers.FLY) <= 0){
				mush.setFly(false);
				mush.setCooldown(MainMushPowers.FLY, (short) 0);
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
