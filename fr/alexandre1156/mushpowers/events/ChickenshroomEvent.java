package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.Chickenshroom;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public class ChickenshroomEvent extends ShroomEvent {
	
	@Override
	protected void onTickPlayer(EntityPlayer p, Phase phase, Side side) {
		if(p.getCapability(PlayerMushProvider.MUSH_CAP, null).getBoolean(Chickenshroom.IS_CHICKEN)){
			if(p.motionY < 0.0D && !p.onGround) 
				p.motionY *= 0.77D;
			p.motionX *= 0.8D;
			p.motionZ *= 0.8D;
		}
	}
	
	@Override
	public boolean onLivingEntityFall(Entity ent, EntityLivingBase entLiv, float distance, float damageMultiplier) {
		if(!entLiv.world.isRemote)
			return true;
		return super.onLivingEntityFall(ent, entLiv, distance, damageMultiplier);
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(Chickenshroom.IS_CHICKEN, mush2.getBoolean(Chickenshroom.IS_CHICKEN));
			mush.setShort(Chickenshroom.CHICKEN_COOLDOWN, mush2.getShort(Chickenshroom.CHICKEN_COOLDOWN));
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.world.isRemote){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) % 1200 == 0)
				p.sendStatusMessage(new TextComponentTranslation("chicken.time.left.min", (mush.getShort(Chickenshroom.CHICKEN_COOLDOWN)/1200)), true);
			else if(mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 600 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 200 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 180 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 160 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 140 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 120 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 100 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 80 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 60 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 40 || mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) == 20)
				p.sendStatusMessage(new TextComponentTranslation("chicken.time.left.sec", (mush.getShort(Chickenshroom.CHICKEN_COOLDOWN)/20)), true);
			if(mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) <= 0) {
				mush.setShort(Chickenshroom.CHICKEN_COOLDOWN, (short) 0);
				mush.setBoolean(Chickenshroom.IS_CHICKEN, false);
				CapabilityUtils.syncCapabilities(p);
				return;
			}
			mush.setShort(Chickenshroom.CHICKEN_COOLDOWN, (short) (mush.getShort(Chickenshroom.CHICKEN_COOLDOWN)-1));
		} else {
			IPlayerMush mush = entLiv.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getShort(Chickenshroom.CHICKEN_COOLDOWN) <= 0){
				//System.out.println("CHICKEN SHROOM FINISHED FOR "+entLiv);
				mush.setShort(Chickenshroom.CHICKEN_COOLDOWN, (short) 0);
				mush.setBoolean(Chickenshroom.IS_CHICKEN, false);
				return;
			}
			mush.setShort(Chickenshroom.CHICKEN_COOLDOWN, (short) (mush.getShort(Chickenshroom.CHICKEN_COOLDOWN)-1));
		}
		if(!(entLiv instanceof EntityPlayer)){
			if(entLiv.ticksExisted % 20 == 0)
				MushPowers.getInstance().proxy.spawnShroomParticle(entLiv, ShroomParticle.CHICKEN);
			if(entLiv.motionY < 0.0D && !entLiv.onGround) 
				entLiv.motionY *= 0.77D;
			entLiv.motionX *= 0.8D;
			entLiv.motionZ *= 0.8D;
		}
	}
	
}
