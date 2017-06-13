package fr.alexandre1156.mushpowers.events;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;

import fr.alexandre1156.mushpowers.EntityAINearestAttackablePlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.proxy.ClientProxy;
import fr.alexandre1156.mushpowers.render.RenderGDD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class SharedMushEvent extends ShroomEvent {
	
	@Override
	protected void onEntityJoinWorld(Entity entity, World world) {
		if(entity instanceof EntityZombie){
			EntityZombie zombie = (EntityZombie) entity;
			zombie.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100D);
			//ZombieawayShroom and Hostileshroom
			zombie.field_70715_bh.func_75776_a(3, new EntityAINearestAttackablePlayerMush(zombie, new Predicate<Entity>() {

				@Override
				public boolean apply(Entity input) {
					if(input instanceof EntityPlayer)
						return !((EntityPlayer) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isZombieAway();
					else
						return false;
				}
			}));
		}
	}
	
	@Override
	protected void onLivingEntityUseItemTick(int duration, EntityLivingBase entLiv, ItemStack item) {
		if(entLiv instanceof EntityPlayer && !entLiv.field_70170_p.field_72995_K && item.func_77973_b() == Items.field_151117_aB && duration <= 1)
			PlayerMushProvider.resetPlayer((EntityPlayer) entLiv, true);
	}
		
	@Override //SYNC CAPABILITIES SERVER TO CLIENT
	protected void onPlayerLoggedIn(EntityPlayer p) {
		PlayerMushProvider.syncCapabilities(p);
	}

	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		//USELESS
	}
	
}
