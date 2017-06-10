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
	public boolean func_75250_a() {
		if (this.field_75299_d.func_70681_au().nextInt(10) != 0) 
            return false;
        else {
            this.field_75309_a = this.field_75299_d.field_70170_p.func_184150_a(this.field_75299_d.field_70165_t, this.field_75299_d.field_70163_u + (double)this.field_75299_d.func_70047_e(), this.field_75299_d.field_70161_v, 500D, 500D, new Function<EntityPlayer, Double>() {
                @Nullable
                public Double apply(@Nullable EntityPlayer p_apply_1_) {
                    ItemStack itemstack = p_apply_1_.func_184582_a(EntityEquipmentSlot.HEAD);

                    if (itemstack.func_77973_b() == Items.field_151144_bL) {
                        int i = itemstack.func_77952_i();
                        boolean flag = EntityAINearestAttackablePlayerMush.this.field_75299_d instanceof EntitySkeleton && i == 0;
                        boolean flag1 = EntityAINearestAttackablePlayerMush.this.field_75299_d instanceof EntityZombie && i == 2;
                        boolean flag2 = EntityAINearestAttackablePlayerMush.this.field_75299_d instanceof EntityCreeper && i == 4;

                        if (flag || flag1 || flag2)
                            return Double.valueOf(0.5D);
                    }

                    return Double.valueOf(1.0D);
                }
            }, (Predicate<EntityPlayer>)this.field_82643_g);
            if(this.field_75309_a != null && this.field_75309_a instanceof EntityPlayer) {
            	//System.out.println(targetEntity.getName()+" -");
            	IPlayerMush mush = ((EntityPlayer) this.field_75309_a).getCapability(PlayerMushProvider.MUSH_CAP, null);
            	//System.out.println(mush.isHostile()+" "+(this.targetEntity != null));
            	if(mush.isHostile())
            		return true;
            	else if(this.field_75309_a.func_70032_d(this.field_75299_d) <= 35.0D)
            		return this.field_75309_a != null;
            	else
            		return false;
            }
            return false;
        }
	}

}
