package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.items.shrooms.Lowershroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;

public class LowershroomEvent extends ShroomEvent {
	
	private int lastMaximumCost;
	private boolean originalCost;
	private int[] lastLevel = new int[3];
	private boolean originalLevel;
	private boolean needLaunchIsServerSide;
	private EntityPlayer player;
	private int xp;
	private int lastXP;
	private boolean xpChange;
	public static final double HUNDRED = 100;

	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setInteger(Lowershroom.LOWER_REPAIR, mush2.getInteger(Lowershroom.LOWER_REPAIR));
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(p.openContainer instanceof ContainerRepair) {
				ContainerRepair container = (ContainerRepair) p.openContainer;
				if(container.maximumCost != 0 && !originalCost && this.lastMaximumCost == 0){
					this.lastMaximumCost = container.maximumCost;
					this.originalCost = true;
				} else if(container.maximumCost == 0 && originalCost && this.lastMaximumCost > 0) {
					this.originalCost = false;
					this.lastMaximumCost = 0;
				} if(this.originalCost){
					container.maximumCost = (int) (this.lastMaximumCost * ((100 - MushConfig.percentLowershroom) / this.HUNDRED));
					if(container.maximumCost <= 0)
						container.maximumCost = 1;
				}
			}
			if(p.openContainer instanceof ContainerEnchantment) {
				if(xp != p.experienceLevel || xp == 0) {
					lastXP = xp;
					xp = p.experienceLevel;
					this.xpChange = true;
				} else if(this.xpChange)
					this.xpChange = false;
				ContainerEnchantment container = (ContainerEnchantment) p.openContainer;
				ItemStack is = container.tableInventory.getStackInSlot(0);
				if(!is.isEmpty() && is.isItemEnchantable() && !is.isItemEnchanted()){
					for(int i = 0; i < container.enchantLevels.length; i++){
						if(container.enchantLevels[i] > 0){
							if(!this.originalLevel) {
								this.lastLevel[i] = (int) (container.enchantLevels[i] * ((100 - MushConfig.percentLowershroom) / this.HUNDRED));
								if(this.lastLevel[i] <= 0)
									this.lastLevel[i] = 1;
							}
							container.enchantLevels[i] = this.lastLevel[i];
						}
					}
					if(this.lastLevel[0] > 0 && this.lastLevel[1] > 0 && this.lastLevel[2] > 0)
						this.originalLevel = true;
				} else if(this.originalLevel && !is.isEmpty() && is.isItemEnchanted()){
					//System.out.println(xp+" "+lastXP);
					if(p.world.isRemote)
						this.needLaunchIsServerSide = true;
					else {
						this.needLaunchIsServerSide = false;
						mush.setInteger(Lowershroom.LOWER_REPAIR, (byte) (xpChange ? (mush.getInteger(Lowershroom.LOWER_REPAIR) - ((lastXP - xp) * 2)) : mush.getInteger(Lowershroom.LOWER_REPAIR) - (lastXP * 2)));
						CapabilityUtils.syncCapabilities(p);
					}
					this.originalLevel = false;
					this.lastLevel[0] = 0;
					this.lastLevel[1] = 0;
					this.lastLevel[2] = 0;
				} else if(this.originalLevel){
					this.originalLevel = false;
					this.lastLevel[0] = 0;
					this.lastLevel[1] = 0;
					this.lastLevel[2] = 0;
				}
			} else {
				this.xp = 0;
				this.lastXP = 0;
			}
			if(this.needLaunchIsServerSide && !p.world.isRemote){
				this.needLaunchIsServerSide = false;
				mush.setInteger(Lowershroom.LOWER_REPAIR, (byte) (xpChange ? (mush.getInteger(Lowershroom.LOWER_REPAIR) - ((lastXP - xp) * 2)) : mush.getInteger(Lowershroom.LOWER_REPAIR) - (lastXP * 2)));
				CapabilityUtils.syncCapabilities(p);
			}
		}
	}
	
	@Override
	public void onAnvilRepair(float breakChance, EntityPlayer p, ItemStack firstInput, ItemStack secondInput, ItemStack output) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(p.openContainer instanceof ContainerRepair){
			mush.setInteger(Lowershroom.LOWER_REPAIR, (byte) (mush.getInteger(Lowershroom.LOWER_REPAIR)-((ContainerRepair) p.openContainer).maximumCost));
			CapabilityUtils.syncCapabilities(p);
		}
	}

}
