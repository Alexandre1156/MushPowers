package fr.alexandre1156.mushpowers.events;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		if(!death){
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setLowerRepairCost(mush2.getRepairCostLeft());
		}
	}
	
	@Override
	protected void onPlayerOpenContainer(Container container, EntityPlayer p) {
//		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
//		if(container instanceof ContainerEnchantment && mush.isRepairCostLower()) {
//			ContainerEnchantment containerEnch = (ContainerEnchantment) container;
//			for(int level : containerEnch.enchantLevels){
//				System.out.println(level);
//			}
//		}
//			this.player = p;
//		else
//			this.player = null;
//		if(container instanceof ContainerRepair){
//			ContainerRepair containerRepair = (ContainerRepair) container;
//			this.lastMaximumCost = containerRepair.maximumCost;
////			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
////			if(mush.isRepairCostLower()){
////				containerRepair.maximumCost = (int) (containerRepair.maximumCost * 0.75);
////				if(containerRepair.maximumCost <= 0)
////					containerRepair.maximumCost = 1;
////			}
//		}
	}
	
	@Override
	protected int onEnchantementLevelSet(int enchantRow, ItemStack item, int level, World world, int power, int originalLevel) {
		
		return super.onEnchantementLevelSet(enchantRow, item, level, world, power, originalLevel);
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if(entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if(p.field_71070_bA instanceof ContainerRepair) {
				ContainerRepair container = (ContainerRepair) p.field_71070_bA;
				if(mush.isRepairCostLower()){
					if(container.field_82854_e != 0 && !originalCost && this.lastMaximumCost == 0){
						this.lastMaximumCost = container.field_82854_e;
						this.originalCost = true;
					} else if(container.field_82854_e == 0 && originalCost && this.lastMaximumCost > 0) {
						this.originalCost = false;
						this.lastMaximumCost = 0;
					} if(this.originalCost){
						container.field_82854_e = (int) (this.lastMaximumCost * 0.75);
						if(container.field_82854_e <= 0)
							container.field_82854_e = 1;
					}
				}
			}
//			if(p.openContainer instanceof ContainerEnchantment)
//				this.player = p;
//			else
//				this.player = null;
			if(p.field_71070_bA instanceof ContainerEnchantment && mush.isRepairCostLower()) {
				if(xp != p.field_71068_ca || xp == 0) {
					lastXP = xp;
					xp = p.field_71068_ca;
					this.xpChange = true;
				} else if(this.xpChange)
					this.xpChange = false;
				ContainerEnchantment container = (ContainerEnchantment) p.field_71070_bA;
				ItemStack is = container.field_75168_e.func_70301_a(0);
				if(!is.func_190926_b() && is.func_77956_u() && !is.func_77948_v()){
					for(int i = 0; i < container.field_75167_g.length; i++){
						if(container.field_75167_g[i] > 0){
							if(!this.originalLevel) {
								this.lastLevel[i] = (int) (container.field_75167_g[i] * 0.75);
								if(this.lastLevel[i] <= 0)
									this.lastLevel[i] = 1;
							}
							container.field_75167_g[i] = this.lastLevel[i];
						}
					}
					if(this.lastLevel[0] > 0 && this.lastLevel[1] > 0 && this.lastLevel[2] > 0)
						this.originalLevel = true;
				} else if(this.originalLevel && !is.func_190926_b() && is.func_77948_v()){
					//System.out.println(xp+" "+lastXP);
					if(p.field_70170_p.field_72995_K)
						this.needLaunchIsServerSide = true;
					else {
						this.needLaunchIsServerSide = false;
						mush.setLowerRepairCost((byte) (xpChange ? (mush.getRepairCostLeft() - ((lastXP - xp) * 2)) : mush.getRepairCostLeft() - (lastXP * 2)));
						PlayerMushProvider.syncCapabilities(p);
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
			if(this.needLaunchIsServerSide && !p.field_70170_p.field_72995_K){
				this.needLaunchIsServerSide = false;
				mush.setLowerRepairCost((byte) (xpChange ? (mush.getRepairCostLeft() - ((lastXP - xp) * 2)) : mush.getRepairCostLeft() - (lastXP * 2)));
				PlayerMushProvider.syncCapabilities(p);
			}
		}
	}
	
	@Override
	public void onAnvilRepair(float breakChance, EntityPlayer p, ItemStack firstInput, ItemStack secondInput, ItemStack output) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(mush.isRepairCostLower() && p.field_71070_bA instanceof ContainerRepair){
			mush.setLowerRepairCost((byte) (mush.getRepairCostLeft()-((ContainerRepair) p.field_71070_bA).field_82854_e));
			PlayerMushProvider.syncCapabilities(p);
		}
	}

}
