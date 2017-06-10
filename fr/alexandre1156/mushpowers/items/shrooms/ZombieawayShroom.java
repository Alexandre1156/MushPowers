package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ZombieawayShroom extends ItemFood {

	public ZombieawayShroom() {
		super(1, 1.0f, false);
		this.func_77655_b("zombieawayshroom");
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, "zombieawayshroom"));
		this.func_77637_a(CreativeTabs.field_78039_h);
		this.func_77848_i();
	}
	
	@Override
	protected void func_77849_c(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.field_72995_K){
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setZombieAway(true);
			player.getCapability(PlayerMushProvider.MUSH_CAP, null).setCooldown(MainMushPowers.ZOMBIEAWAY, (short) 6000);
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
		List<EntityZombie> zombies = player.field_70170_p.func_175644_a(EntityZombie.class, Predicates.alwaysTrue());
		for(EntityZombie zombie : zombies)
			zombie.field_70714_bg.func_75774_a();
	}
	
	@Override
	public void func_77624_a(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.WHITE+"Zombies around you will run away from you.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in 5 minutes");
	}

}
