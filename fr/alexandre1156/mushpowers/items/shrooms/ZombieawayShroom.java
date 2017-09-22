package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ZombieawayShroom extends ItemMushPowers {

	public static final String IS_ZOMBIEAWAY = "zombieaway";
	public static final String ZOMBIEAWAY_COOLDOWN = "zombieawaycooldown";
	
	public ZombieawayShroom() {
		super(1, 1.0f, "zombieawayshroom");
		this.registerData("zombieaway", Types.BOOLEAN);
		this.registerData("zombieawaycooldown", Types.SHORT);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			mush.setBoolean(this.IS_ZOMBIEAWAY, true);
			mush.setShort(this.ZOMBIEAWAY_COOLDOWN, MushConfig.getCooldown(Mushs.ZOMBIEAWAY));
			CapabilityUtils.resetOtherMainMushPower(player, Mushs.ZOMBIEAWAY);
			this.updateZombiesTasks(player);
			super.onFoodEaten(stack, worldIn, player);
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
		for(EntityZombie zombie : zombies) {
			for(EntityAITaskEntry task : zombie.tasks.taskEntries) {
				if(task.action instanceof EntityAIAvoidEntity) {
					Class<Entity> ent = ObfuscationReflectionHelper.getPrivateValue(EntityAIAvoidEntity.class, (EntityAIAvoidEntity) task.action, 8);
					if(ent.isAssignableFrom(EntityPlayer.class)) 
						task.action.shouldExecute();
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"Zombies around you will run away from you.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(Mushs.ZOMBIEAWAY)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
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

	@Override
	protected Mushs getMushType() {
		return Mushs.ZOMBIEAWAY;
	}

}
