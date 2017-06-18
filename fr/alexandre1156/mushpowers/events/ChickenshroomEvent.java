package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public class ChickenshroomEvent extends ShroomEvent {
	
	@Override
	protected void onTickPlayer(EntityPlayer p, Phase phase, Side side) {
		if(side.isClient() && p.getCapability(PlayerMushProvider.MUSH_CAP, null).isChicken()){
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
			mush.setChicken(mush2.isChicken());
			mush.setCooldown(MainMushPowers.CHICKEN, mush2.getCooldown(MainMushPowers.CHICKEN));
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.world.isRemote){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getCooldown(MainMushPowers.CHICKEN) % 1200 == 0)
				p.sendStatusMessage(new TextComponentTranslation("chicken.time.left.min", (mush.getCooldown(MainMushPowers.CHICKEN)/1200)), true);
			else if(mush.getCooldown(MainMushPowers.CHICKEN) == 600 || mush.getCooldown(MainMushPowers.CHICKEN) == 200 || mush.getCooldown(MainMushPowers.CHICKEN) == 180 || mush.getCooldown(MainMushPowers.CHICKEN) == 160 || mush.getCooldown(MainMushPowers.CHICKEN) == 140 || mush.getCooldown(MainMushPowers.CHICKEN) == 120 || mush.getCooldown(MainMushPowers.CHICKEN) == 100 || mush.getCooldown(MainMushPowers.CHICKEN) == 80 || mush.getCooldown(MainMushPowers.CHICKEN) == 60 || mush.getCooldown(MainMushPowers.CHICKEN) == 40 || mush.getCooldown(MainMushPowers.CHICKEN) == 20)
				p.sendStatusMessage(new TextComponentTranslation("chicken.time.left.sec", (mush.getCooldown(MainMushPowers.CHICKEN)/20)), true);
			if(mush.getCooldown(MainMushPowers.CHICKEN) <= 0) {
				mush.setCooldown(MainMushPowers.CHICKEN, (short) MushConfig.cooldownChicken);
				mush.setChicken(false);
				PlayerMushProvider.syncCapabilities(p);
				return;
			}
			mush.setCooldown(MainMushPowers.CHICKEN, (short) (mush.getCooldown(MainMushPowers.CHICKEN)-1));
		}
	}
	
}
