package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.Electrocution;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.items.shrooms.Electricshroom;
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
		if(side.isClient() && p.getCapability(PlayerMushProvider.MUSH_CAP, null).getBoolean(Electricshroom.IS_ELECTRIC)){
			p.motionX *= 1.02D;
			p.motionZ *= 1.02D;
		}
	}
	
	@Override
	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {
		if(source.getImmediateSource() instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) source.getImmediateSource();
			if(this.rand.nextInt(9) == 5 && this.isSword(p.getHeldItemMainhand().getItem())){
				System.out.println("LOL");
				entLiv.setHealth(entLiv.getHealth() - 6);
				entLiv.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 255, false, false));
				p.sendStatusMessage(new TextComponentTranslation("electric.extra", new Object[0]), true);
			}
		}
		return super.onLivingHurt(entLiv, source, amount);
	}
	
	@Override
	protected boolean onLivingDeath(EntityLivingBase entLiv, DamageSource source) {
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			p.getCombatTracker().trackDamage(Electrocution.causeElectrocution(source.getImmediateSource(), p), 0, 1);
		}
		return super.onLivingDeath(entLiv, source);
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.world.isRemote){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.getShort(Electricshroom.FLY_COOLDOWN) % 1200 == 0)
				p.sendStatusMessage(new TextComponentTranslation("eletric.time.left.min", (mush.getShort(Electricshroom.FLY_COOLDOWN)/1200)), true);
			else if(mush.getShort(Electricshroom.FLY_COOLDOWN) == 600 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 200 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 180 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 160 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 140 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 120 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 100 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 80 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 60 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 40 || mush.getShort(Electricshroom.FLY_COOLDOWN) == 20)
				p.sendStatusMessage(new TextComponentTranslation("eletric.time.left.sec", (mush.getShort(Electricshroom.FLY_COOLDOWN)/20)), true);
			else if(mush.getShort(Electricshroom.FLY_COOLDOWN) == 2700)
				p.sendStatusMessage(new TextComponentTranslation("eletric.time.start", mush.getShort(Electricshroom.FLY_COOLDOWN)), true);
			if(mush.getShort(Electricshroom.FLY_COOLDOWN) <= 0) {
				mush.setShort(Electricshroom.FLY_COOLDOWN, (short) 0);
				mush.setBoolean(Electricshroom.IS_ELECTRIC, false);
				CapabilityUtils.syncCapabilities(p);
				return;
			}
			mush.setShort(Electricshroom.FLY_COOLDOWN, (short) (mush.getShort(Electricshroom.FLY_COOLDOWN)-1));
		}
	}
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(Electricshroom.IS_ELECTRIC, mush2.getBoolean(Electricshroom.IS_ELECTRIC));
			mush.setShort(Electricshroom.FLY_COOLDOWN, mush2.getShort(Electricshroom.FLY_COOLDOWN));
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
