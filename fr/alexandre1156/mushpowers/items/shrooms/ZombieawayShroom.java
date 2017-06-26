package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ZombieawayShroom extends ItemMushPowers {

	public ZombieawayShroom() {
		super(1, 1.0f, "zombieawayshroom");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setZombieAway(true);
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setCooldown(MainMushPowers.ZOMBIEAWAY, MushConfig.getCooldown(MainMushPowers.ZOMBIEAWAY));
			PlayerMushProvider.resetOtherMainMushPower(player, MainMushPowers.ZOMBIEAWAY);
			PlayerMushProvider.syncCapabilities(player);
			this.updateZombiesTasks(player);
		}
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
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(MainMushPowers.ZOMBIEAWAY)));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.GREEN;
		
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {return false;}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return null;
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return false;
	}

}
