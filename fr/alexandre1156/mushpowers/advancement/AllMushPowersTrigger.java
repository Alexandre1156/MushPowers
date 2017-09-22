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
import fr.alexandre1156.mushpowers.capabilities.eat.EatMushPowersProvider;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class AllMushPowersTrigger implements ICriterionTrigger<AllMushPowersTrigger.Instance> {

	private static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "all_mush_powers");
	private final Map<PlayerAdvancements, AllMushPowersTrigger.Listeners> listeners = Maps.<PlayerAdvancements, AllMushPowersTrigger.Listeners>newHashMap();
	
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
		AllMushPowersTrigger.Listeners craftMushPowersTrigger$listeners = this.listeners.get(playerAdvancementsIn);
	    if (craftMushPowersTrigger$listeners == null) {
	        craftMushPowersTrigger$listeners = new AllMushPowersTrigger.Listeners(playerAdvancementsIn);
	        this.listeners.put(playerAdvancementsIn, craftMushPowersTrigger$listeners);
	    }
	    craftMushPowersTrigger$listeners.add(listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {
		AllMushPowersTrigger.Listeners craftMushPowersTrigger$listeners = this.listeners.get(playerAdvancementsIn);
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
	
	public void trigger(EntityPlayerMP player) {
		AllMushPowersTrigger.Listeners craftMushPowersTrigger$listeners = this.listeners.get(player.getAdvancements());
        if (craftMushPowersTrigger$listeners != null)
        {
        	craftMushPowersTrigger$listeners.trigger(player);
        }
    }

	@Override
	public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		return new AllMushPowersTrigger.Instance();
	}
	
	public static class Instance extends AbstractCriterionInstance {
		
		public Instance() {
			super(AllMushPowersTrigger.ID);
		}
		
		public boolean test(EntityPlayerMP player) {
			return player.getCapability(EatMushPowersProvider.EAT_MUSHS_CAP, null).hasEatenAllMush();
		}
		
	}
	
	static class Listeners {
		
		 private final PlayerAdvancements playerAdvancements;
         private final Set<ICriterionTrigger.Listener<AllMushPowersTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<AllMushPowersTrigger.Instance>>newHashSet();
		
         public Listeners(PlayerAdvancements pAdv) {
        	 this.playerAdvancements = pAdv;
         }
         
         public boolean isEmpty()
         {
             return this.listeners.isEmpty();
         }

         public void add(ICriterionTrigger.Listener<AllMushPowersTrigger.Instance> listener)
         {
             this.listeners.add(listener);
         }

         public void remove(ICriterionTrigger.Listener<AllMushPowersTrigger.Instance> listener)
         {
             this.listeners.remove(listener);
         }

         public void trigger(EntityPlayerMP player)
         {
             List<ICriterionTrigger.Listener<AllMushPowersTrigger.Instance>> list = null;

             for (ICriterionTrigger.Listener<AllMushPowersTrigger.Instance> listener : this.listeners)
             {
                 if (((AllMushPowersTrigger.Instance)listener.getCriterionInstance()).test(player))
                 {
                     if (list == null)
                     {
                         list = Lists.<ICriterionTrigger.Listener<AllMushPowersTrigger.Instance>>newArrayList();
                     }

                     list.add(listener);
                 }
             }

             if (list != null)
             {
                 for (ICriterionTrigger.Listener<AllMushPowersTrigger.Instance> listener1 : list)
                 {
                     listener1.grantCriterion(this.playerAdvancements);
                 }
             }
         }
         
         
	}
	
}
