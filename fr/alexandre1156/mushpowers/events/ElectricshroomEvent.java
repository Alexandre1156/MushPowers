package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.Electrocution;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
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
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(mush.isElectric() && side.isClient()){
			p.field_70159_w *= 1.02D;
			p.field_70179_y *= 1.02D;
		}
	}
	
	@Override
	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {
		if(source.func_76346_g() instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) source.func_76346_g();
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.isElectric() && this.rand.nextInt(9) == 5 && this.isSword(p.func_184614_ca().func_77973_b())){
				entLiv.func_70606_j(entLiv.func_110143_aJ() - 6);
				entLiv.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20, 255, false, false));
				p.func_146105_b(new TextComponentTranslation("eletric.extra", new Object[0]), true);
			}
		}
		return super.onLivingHurt(entLiv, source, amount);
	}
	
	@Override
	protected boolean onLivingDeath(EntityLivingBase entLiv, DamageSource source) {
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.isElectric())
				p.func_110142_aN().func_94547_a(Electrocution.causeElectrocution(source.func_76346_g(), p), 0, 1);
		}
		return super.onLivingDeath(entLiv, source);
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(ent instanceof EntityPlayer && !ent.field_70170_p.field_72995_K){
			EntityPlayer p = (EntityPlayer) ent;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(mush.isElectric()){
				if(mush.getCooldown(MainMushPowers.ELECTRIC) % 1200 == 0)
					p.func_146105_b(new TextComponentTranslation("eletric.time.left.min", (mush.getCooldown(MainMushPowers.ELECTRIC)/1200)), true);
				else if(mush.getCooldown(MainMushPowers.ELECTRIC) == 600 || mush.getCooldown(MainMushPowers.ELECTRIC) == 200 || mush.getCooldown(MainMushPowers.ELECTRIC) == 180 || mush.getCooldown(MainMushPowers.ELECTRIC) == 160 || mush.getCooldown(MainMushPowers.ELECTRIC) == 140 || mush.getCooldown(MainMushPowers.ELECTRIC) == 120 || mush.getCooldown(MainMushPowers.ELECTRIC) == 100 || mush.getCooldown(MainMushPowers.ELECTRIC) == 80 || mush.getCooldown(MainMushPowers.ELECTRIC) == 60 || mush.getCooldown(MainMushPowers.ELECTRIC) == 40 || mush.getCooldown(MainMushPowers.ELECTRIC) == 20)
					p.func_146105_b(new TextComponentTranslation("eletric.time.left.sec", (mush.getCooldown(MainMushPowers.ELECTRIC)/20)), true);
				else if(mush.getCooldown(MainMushPowers.ELECTRIC) == 2700)
					p.func_146105_b(new TextComponentTranslation("eletric.time.start", mush.getCooldown(MainMushPowers.ELECTRIC)), true);
				if(mush.getCooldown(MainMushPowers.ELECTRIC) <= 0) {
					mush.setCooldown(MainMushPowers.ELECTRIC, (short) 18000);
					mush.setElectric(false);
					PlayerMushProvider.syncCapabilities(p);
					return;
				}
				mush.setCooldown(MainMushPowers.ELECTRIC, (short) (mush.getCooldown(MainMushPowers.ELECTRIC)-1));
			}
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
		return itemInHand == Items.field_151048_u ? true : 
			(itemInHand == Items.field_151010_B ? true : 
				(itemInHand == Items.field_151040_l ? true : 
					(itemInHand == Items.field_151052_q ? true : 
						(itemInHand == Items.field_151041_m ? true : 
							false))));
	}
	
}
