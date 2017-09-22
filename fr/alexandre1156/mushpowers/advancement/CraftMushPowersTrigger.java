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
import fr.alexandre1156.mushpowers.mppi.ContainerMushPowersPowerInjector;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CraftMushPowersTrigger implements ICriterionTrigger<CraftMushPowersTrigger.Instance> {

	private static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "craft_mushpowers");
	private final Map<PlayerAdvancements, CraftMushPowersTrigger.Listeners> listeners = Maps.<PlayerAdvancements, CraftMushPowersTrigger.Listeners>newHashMap();
	
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
		CraftMushPowersTrigger.Listeners craftMushPowersTrigger$listeners = this.listeners.get(playerAdvancementsIn);
	    if (craftMushPowersTrigger$listeners == null) {
	        craftMushPowersTrigger$listeners = new CraftMushPowersTrigger.Listeners(playerAdvancementsIn);
	        this.listeners.put(playerAdvancementsIn, craftMushPowersTrigger$listeners);
	    }
	    craftMushPowersTrigger$listeners.add(listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
		CraftMushPowersTrigger.Listeners craftMushPowersTrigger$listeners = this.listeners.get(playerAdvancementsIn);
        if (craftMushPowersTrigger$listeners != null) {
            craftMushPowersTrigger$listeners.remove(listener);
            if (craftMushPowersTrigger$listeners.isEmpty())
                this.listeners.remove(playerAdvancementsIn);
        }
	}

	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
		this.listeners.remove(playerAdvancementsIn);
	}
	
	public void trigger(EntityPlayerMP player, ItemStack mushpowers, Container container) {
		CraftMushPowersTrigger.Listeners craftMushPowersTrigger$listeners = this.listeners.get(player.getAdvancements());
        if (craftMushPowersTrigger$listeners != null)
        {
        	craftMushPowersTrigger$listeners.trigger(mushpowers, container);
        }
    }

	@Override
	public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		ItemPredicate item = ItemPredicate.deserialize(json.get("item"));
		return new CraftMushPowersTrigger.Instance(item);
	}
	
	public static class Instance extends AbstractCriterionInstance {
		
		private final ItemPredicate item;
		
		public Instance(ItemPredicate item) {
			super(CraftMushPowersTrigger.ID);
			this.item = item;
		}
		
		public boolean test(ItemStack is, Container container) {
			if(container instanceof ContainerMushPowersPowerInjector)
				return this.item.test(is);
			else
				return false;
		}
		
	}
	
	static class Listeners {
		
		 private final PlayerAdvancements playerAdvancements;
         private final Set<ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance>>newHashSet();
		
         public Listeners(PlayerAdvancements pAdv) {
        	 this.playerAdvancements = pAdv;
         }
         
         public boolean isEmpty()
         {
             return this.listeners.isEmpty();
         }

         public void add(ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance> listener)
         {
             this.listeners.add(listener);
         }

         public void remove(ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance> listener)
         {
             this.listeners.remove(listener);
         }

         public void trigger(ItemStack is, Container container)
         {
             List<ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance>> list = null;

             for (ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance> listener : this.listeners)
             {
                 if (((CraftMushPowersTrigger.Instance)listener.getCriterionInstance()).test(is, container))
                 {
                     if (list == null)
                     {
                         list = Lists.<ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance>>newArrayList();
                     }

                     list.add(listener);
                 }
             }

             if (list != null)
             {
                 for (ICriterionTrigger.Listener<CraftMushPowersTrigger.Instance> listener1 : list)
                 {
                     listener1.grantCriterion(this.playerAdvancements);
                 }
             }
         }
         
         
	}
	
}
