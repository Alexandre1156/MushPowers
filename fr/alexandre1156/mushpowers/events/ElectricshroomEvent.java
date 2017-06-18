package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.Electrocution;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public class ElectricshroomEvent extends ShroomEvent {
	
	@Override
	protected void onTickPlayer(EntityPlayer p, Phase phase, Side side) {
		if(side.isClient() && p.getCapability(PlayerMushProvider.MUSH_CAP, null).isElectric()){
			p.motionX *= 1.02D;
			p.motionZ *= 1.02D;
		}
	}
	
	@Override
	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {
		if(source.getEntity() instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) source.getEntity();
			if(this.rand.nextInt(9) == 5 && this.isSword(p.getHeldItemMainhand().getItem())){
				entLiv.setHealth(entLiv.getHealth() - 6);
				entLiv.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 255, false, false));
				p.sendStatusMessage(new TextComponentTranslation("eletric.extra", new Object[0]), true);
			}
		}
		return super.onLivingHurt(entLiv, source, amount);
	}
	
	@Override
	protected boolean onLivingDeath(EntityLivingBase entLiv, DamageSource source) {
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
				p.getCombatTracker().trackDamage(Electrocution.causeElectrocution(source.getEntity(), p), 0, 1);
		}
		return super.onLivingDeath(entLiv, source);
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.world.isRemote){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getCooldown(MainMushPowers.ELECTRIC) % 1200 == 0)
				p.sendStatusMessage(new TextComponentTranslation("eletric.time.left.min", (mush.getCooldown(MainMushPowers.ELECTRIC)/1200)), true);
			else if(mush.getCooldown(MainMushPowers.ELECTRIC) == 600 || mush.getCooldown(MainMushPowers.ELECTRIC) == 200 || mush.getCooldown(MainMushPowers.ELECTRIC) == 180 || mush.getCooldown(MainMushPowers.ELECTRIC) == 160 || mush.getCooldown(MainMushPowers.ELECTRIC) == 140 || mush.getCooldown(MainMushPowers.ELECTRIC) == 120 || mush.getCooldown(MainMushPowers.ELECTRIC) == 100 || mush.getCooldown(MainMushPowers.ELECTRIC) == 80 || mush.getCooldown(MainMushPowers.ELECTRIC) == 60 || mush.getCooldown(MainMushPowers.ELECTRIC) == 40 || mush.getCooldown(MainMushPowers.ELECTRIC) == 20)
				p.sendStatusMessage(new TextComponentTranslation("eletric.time.left.sec", (mush.getCooldown(MainMushPowers.ELECTRIC)/20)), true);
			else if(mush.getCooldown(MainMushPowers.ELECTRIC) == 2700)
				p.sendStatusMessage(new TextComponentTranslation("eletric.time.start", mush.getCooldown(MainMushPowers.ELECTRIC)), true);
			if(mush.getCooldown(MainMushPowers.ELECTRIC) <= 0) {
				mush.setCooldown(MainMushPowers.ELECTRIC, (short) MushConfig.cooldownElectric);
				mush.setElectric(false);
				PlayerMushProvider.syncCapabilities(p);
				return;
			}
			mush.setCooldown(MainMushPowers.ELECTRIC, (short) (mush.getCooldown(MainMushPowers.ELECTRIC)-1));
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setElectric(mush2.isElectric());
			mush.setCooldown(MainMushPowers.ELECTRIC, mush2.getCooldown(MainMushPowers.ELECTRIC));
		}
	}
	
	private boolean isSword(Item itemInHand){
		return itemInHand == Items.DIAMOND_SWORD ? true : 
			(itemInHand == Items.GOLDEN_SWORD ? true : 
				(itemInHand == Items.IRON_SWORD ? true : 
					(itemInHand == Items.STONE_SWORD ? true : 
						(itemInHand == Items.WOODEN_SWORD ? true : 
							false))));
	}
	
}
