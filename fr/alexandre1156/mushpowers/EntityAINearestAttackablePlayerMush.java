package fr.alexandre1156.mushpowers;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EntityAINearestAttackablePlayerMush extends EntityAINearestAttackableTarget{

	public EntityAINearestAttackablePlayerMush(EntityCreature creature) {
		super(creature, EntityPlayer.class, 10, true, false, Predicates.alwaysTrue());
	}
	
	public EntityAINearestAttackablePlayerMush(EntityCreature creature, Predicate predicate) {
		super(creature, EntityPlayer.class, 10, true, false, predicate);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.taskOwner.getRNG().nextInt(10) != 0) 
            return false;
        else {
            this.targetEntity = this.taskOwner.world.getNearestAttackablePlayer(this.taskOwner.posX, this.taskOwner.posY + (double)this.taskOwner.getEyeHeight(), this.taskOwner.posZ, 200D, 200D, new Function<EntityPlayer, Double>() {
                @Nullable
                public Double apply(@Nullable EntityPlayer p_apply_1_) {
                    ItemStack itemstack = p_apply_1_.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                    if (itemstack.getItem() == Items.SKULL) {
                        int i = itemstack.getItemDamage();
                        boolean flag = EntityAINearestAttackablePlayerMush.this.taskOwner instanceof EntitySkeleton && i == 0;
                        boolean flag1 = EntityAINearestAttackablePlayerMush.this.taskOwner instanceof EntityZombie && i == 2;
                        boolean flag2 = EntityAINearestAttackablePlayerMush.this.taskOwner instanceof EntityCreeper && i == 4;

                        if (flag || flag1 || flag2)
                            return Double.valueOf(0.5D);
                    }

                    return Double.valueOf(1.0D);
                }
            }, (Predicate<EntityPlayer>)this.targetEntitySelector);
            if(this.targetEntity != null && this.targetEntity instanceof EntityPlayer) {
            	IPlayerMush mush = ((EntityPlayer) this.targetEntity).getCapability(PlayerMushProvider.MUSH_CAP, null);
            	if(mush.isHostile())
            		return this.targetEntity != null;
            	else if(this.targetEntity.getDistanceToEntity(this.taskOwner) <= 35.0D)
            		return this.targetEntity != null;
            	else
            		return false;
            }
            return false;
        }
	}

}
