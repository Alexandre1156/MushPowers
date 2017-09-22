package fr.alexandre1156.mushpowers.advancement;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class RightClickEntityTrigger implements ICriterionTrigger<RightClickEntityTrigger.Instance> {

	private static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "right_click_entity");
	private final Map<PlayerAdvancements, RightClickEntityTrigger.Listeners> listeners = Maps.<PlayerAdvancements, RightClickEntityTrigger.Listeners>newHashMap();
	
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
		RightClickEntityTrigger.Listeners playerrightclickentity$listeners = this.listeners.get(playerAdvancementsIn);
	    if (playerrightclickentity$listeners == null) {
	        playerrightclickentity$listeners = new RightClickEntityTrigger.Listeners(playerAdvancementsIn);
	        this.listeners.put(playerAdvancementsIn, playerrightclickentity$listeners);
	    }
	    playerrightclickentity$listeners.add(listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
		RightClickEntityTrigger.Listeners playerrightclickentity$listeners = this.listeners.get(playerAdvancementsIn);
        if (playerrightclickentity$listeners != null) {
            playerrightclickentity$listeners.remove(listener);
            if (playerrightclickentity$listeners.isEmpty())
                this.listeners.remove(playerAdvancementsIn);
        }
	}

	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
		this.listeners.remove(playerAdvancementsIn);
	}
	
	public void trigger(EntityPlayerMP player, EntityLivingBase entityIn) {
		RightClickEntityTrigger.Listeners playerrightclickentitytrigger$listeners = this.listeners.get(player.getAdvancements());
        if (playerrightclickentitytrigger$listeners != null)
        {
            playerrightclickentitytrigger$listeners.trigger(player, entityIn);
        }
    }

	@Override
	public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		EntityPredicate entLivPred = EntityPredicate.deserialize(json.get("entity"));
		ItemPredicate itemPred = ItemPredicate.deserialize(json.get("item"));
		ItemEntityPredicate itemEnts = ItemEntityPredicate.deserialize(json.get("item_entities"));
		return new RightClickEntityTrigger.Instance(entLivPred, itemPred, itemEnts);
	}
	
	public static class Instance extends AbstractCriterionInstance {

		private final EntityPredicate entLiv;
		private final ItemPredicate item;
		private final ItemEntityPredicate itemEnts;
		
		public Instance(EntityPredicate entLiv, ItemPredicate item, ItemEntityPredicate itemEnts) {
			super(RightClickEntityTrigger.ID);
			this.entLiv = entLiv;
			this.item = item;
			this.itemEnts = itemEnts;
		}
		
		public boolean test(EntityLivingBase entLiv, EntityPlayerMP player) {
			if(this.item.test(player.getHeldItemMainhand()) && this.itemEnts.test(player.getHeldItemMainhand(), player, entLiv))
				return this.entLiv.test(player, entLiv);
			else
				return false;
		}
		
	}
	
	static class Listeners {
		
		 private final PlayerAdvancements playerAdvancements;
         private final Set<ICriterionTrigger.Listener<RightClickEntityTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<RightClickEntityTrigger.Instance>>newHashSet();
		
         public Listeners(PlayerAdvancements pAdv) {
        	 this.playerAdvancements = pAdv;
         }
         
         public boolean isEmpty()
         {
             return this.listeners.isEmpty();
         }

         public void add(ICriterionTrigger.Listener<RightClickEntityTrigger.Instance> listener)
         {
             this.listeners.add(listener);
         }

         public void remove(ICriterionTrigger.Listener<RightClickEntityTrigger.Instance> listener)
         {
             this.listeners.remove(listener);
         }

         public void trigger(EntityPlayerMP pMP, EntityLivingBase interactEnt)
         {
             List<ICriterionTrigger.Listener<RightClickEntityTrigger.Instance>> list = null;

             for (ICriterionTrigger.Listener<RightClickEntityTrigger.Instance> listener : this.listeners)
             {
                 if (((RightClickEntityTrigger.Instance)listener.getCriterionInstance()).test(interactEnt, pMP))
                 {
                     if (list == null)
                     {
                         list = Lists.<ICriterionTrigger.Listener<RightClickEntityTrigger.Instance>>newArrayList();
                     }

                     list.add(listener);
                 }
             }

             if (list != null)
             {
                 for (ICriterionTrigger.Listener<RightClickEntityTrigger.Instance> listener1 : list)
                 {
                     listener1.grantCriterion(this.playerAdvancements);
                 }
             }
         }
         
         
	}

}
