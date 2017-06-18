package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ZombieawayShroom extends ItemFood {

	public ZombieawayShroom() {
		super(1, 1.0f, false);
		this.setUnlocalizedName("zombieawayshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "zombieawayshroom"));
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setAlwaysEdible();
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setZombieAway(true);
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setCooldown(MainMushPowers.ZOMBIEAWAY, (short) MushConfig.cooldownZombieAway);
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.ZOMBIEAWAY);
			PlayerMushProvider.syncCapabilities(player);
			this.updateZombiesTasks(player);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(MushConfig.isMushPowersDesactived(this))
			return new ActionResult(EnumActionResult.FAIL, itemstack);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
//	@Override
//	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
//		if(!player.world.isRemote){
//			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setZombieAway(false);
//			PlayerMushProvider.syncCapabilities(player);
//			this.updateZombiesTasks(player);
//		}
//		return super.onDroppedByPlayer(item, player);
//	}
	
	private void updateZombiesTasks(EntityPlayer player){
		List<EntityZombie> zombies = player.world.getEntities(EntityZombie.class, Predicates.alwaysTrue());
		for(EntityZombie zombie : zombies)
			zombie.tasks.onUpdateTasks();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Zombies around you will run away from you.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.cooldownZombieAway));
		if(MushConfig.isMushPowersDesactived(this))
			tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
	}

}
