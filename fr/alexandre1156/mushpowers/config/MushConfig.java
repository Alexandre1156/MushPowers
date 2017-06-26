package fr.alexandre1156.mushpowers.config;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.bushs.BushMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MushConfig {

	public static Configuration config;
	//General
	public static ArrayList<String> playersCanSeeFakeRegenshroom;
	public static byte repairCostLowershroom;
	public static int percentLowershroom;
	public static int foodRegenPizzashroom;
	public static byte foodCountPizzashroom;
	public static int hearthRegenshroom;
	public static int damageAbsordPercentShieldshroom;
	public static byte maxDamageAbsorbShieldshroom;
	//Cooldown
	private static EnumMap<MainMushPowers, Short> cooldowns;
//	public static short cooldownSquid;
//	public static short cooldownChicken;
//	public static short cooldownElectric;
//	public static short cooldownFly;
//	public static short cooldownGhost;
//	public static short cooldownHostile;
//	public static short cooldownZombieAway;
	//Desactive
	private static HashMap<ItemMushPowers, Boolean> desactives;
//	public static boolean desactiveSquid;
//	public static boolean desactiveChicken;
//	public static boolean desactiveCursed;
//	public static boolean desactiveElectric;
//	public static boolean desactiveFly;
//	public static boolean desactiveGhost;
//	public static boolean desactiveHostile;
//	public static boolean desactiveLower;
//	public static boolean desactivePizza;
//	public static boolean desactiveRegen;
//	public static boolean desactiveShield;
//	public static boolean desactiveZombieAway;
	
	public static void syncConfig(){
		try {
			if(cooldowns == null) cooldowns = Maps.newEnumMap(MainMushPowers.class);
			if(desactives == null) desactives = Maps.newHashMap();
			if(config != null) {
				//General
				playersCanSeeFakeRegenshroom = Lists.newArrayList(config.get(Configuration.CATEGORY_GENERAL, "Cursedshroom:PlayersCanSeeFakeRegenshroom", new String[]{"PlayerName"}, "Players who are warned if they have a fake Regenshroom in their hands.").getStringList());
				repairCostLowershroom = (byte) config.getInt("Lowershroom:RepairCost", Configuration.CATEGORY_GENERAL, 20, 1, 127, "The maximum saved level number");
				percentLowershroom = config.getInt("Lowershroom:PercentSaved", Configuration.CATEGORY_GENERAL, 25, 1, 99, "Purcent of level reduce");
				foodRegenPizzashroom = config.getInt("Pizzashroom:FoodRegen", Configuration.CATEGORY_GENERAL, 2, 1, 20, "Number of half-food added each time you eat");
				foodCountPizzashroom = (byte) config.getInt("Pizzashroom:FoodCount", Configuration.CATEGORY_GENERAL, 3, 1, 99, "Number of food you need to eat in order to desactive Lowershroom power");
				hearthRegenshroom = config.getInt("Regenshroom:HearthRegen", Configuration.CATEGORY_GENERAL, 6, 1, 20, "Number of half-hearth the Regenshroom give");
				maxDamageAbsorbShieldshroom = (byte) config.getInt("Shieldshroom:DamageAbsord", Configuration.CATEGORY_GENERAL, 10, 1, 127, "Number of half-hearth you need to lose to desactive Shieldshroom");
				damageAbsordPercentShieldshroom = config.getInt("Shieldshroom:PercentDamagedAbsorb", Configuration.CATEGORY_GENERAL, 25, 1, 99, "Percent of damage taken absorbed");
				//Cooldown
				for(MainMushPowers mainMushPowers : MainMushPowers.values())
					cooldowns.put(mainMushPowers, (short) config.getInt(MushUtils.startStringWithCapital(mainMushPowers.getItemInstance().getRegistryName().getResourcePath()), 
							"MushCooldown", mainMushPowers.getCooldownDefaultValue(), 10, 32767, 
							"The "+mainMushPowers.getItemInstance().getRegistryName().getResourcePath()+" cooldown in tick"));
//				cooldownSquid = (short) config.getInt("Squidshroom", "MushCooldown", 6000, 10, 32767, "The Squidshroom cooldown in tick");
//				cooldownChicken = (short) config.getInt("Chickenshroom", "MushCooldown", 3600, 10, 32767, "The Chicekenshroom cooldown in tick");
//				cooldownElectric = (short) config.getInt("Eletricshroom", "MushCooldown", 18000, 10, 32767, "The Eletricshroom cooldown in tick");
//				cooldownFly = (short) config.getInt("Flyshroom", "MushCooldown", 1200, 10, 32767, "The Flyshroom cooldown in tick");
//				cooldownGhost = (short) config.getInt("Ghostshroom", "MushCooldown", 2700, 10, 32767, "The Ghostshroom cooldown in tick");
//				cooldownHostile = (short) config.getInt("Hostileshroom", "MushCooldown", 6000, 10, 32767, "The Hostileshroom cooldown in tick");
//				cooldownZombieAway = (short) config.getInt("ZombieAway Shroom", "MushCooldown", 6000, 10, 32767, "The ZombieAway Shroom cooldown in tick");
				//Desactive
				for(ItemMushPowers mushPowers : CommonProxy.getMushPowers()){
					desactives.put(mushPowers, config.getBoolean(MushUtils.startStringWithCapital(mushPowers.getRegistryName().getResourcePath()), 
							"MushDesactived", false, "Choice if want to desactive "+MushUtils.startStringWithCapital(mushPowers.getRegistryName().getResourcePath())));
				}
//				desactiveSquid = config.getBoolean("Squidshroom", "MushDesactived", false, "Choice if you want to desactive Squidshroom");
//				desactiveChicken = config.getBoolean("Chickenshroom", "MushDesactived", false, "Choice if you want to desactive Chickenshroom");
//				desactiveCursed = config.getBoolean("Cursedshroom", "MushDesactived", false, "Choice if you want to desactive Cursedshroom");
//				desactiveElectric = config.getBoolean("Electricshroom", "MushDesactived", false, "Choice if you want to desactive Electricshroom");
//				desactiveFly = config.getBoolean("Flyshroom", "MushDesactived", false, "Choice if you want to desactive Flyshroom");
//				desactiveGhost = config.getBoolean("Ghostshroom", "MushDesactived", false, "Choice if you want to desactive Ghostshroom");
//				desactiveHostile = config.getBoolean("Hostileshroom", "MushDesactived", false, "Choice if you want to desactive Hostileshroom");
//				desactiveLower = config.getBoolean("Lowershroom", "MushDesactived", false, "Choice if you want to desactive Lowershroom");
//				desactivePizza = config.getBoolean("Pizzashroom", "MushDesactived", false, "Choice if you want to desactive Pizzashroom");
//				desactiveRegen = config.getBoolean("Regenshroom", "MushDesactived", false, "Choice if you want to desactive Regenshroom");
//				desactiveShield = config.getBoolean("Shieldshroom", "MushDesactived", false, "Choice if you want to desactive Shieldshroom");
//				desactiveZombieAway = config.getBoolean("ZombieAwayshroom", "MushDesactived", false, "Choice if you want to desactive ZombieAwayshroom");
				
				config.save();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(Reference.MOD_ID))
			syncConfig();
	}
	
	public static boolean isMushPowersDesactived(Item item){
		if(item instanceof ItemMushPowers) 
			return desactives.get(item);
		else {
			for(BushMush bushmush : CommonProxy.getBushs()){
				if(Item.getItemFromBlock(bushmush).equals(item))
					return desactives.get(bushmush.getMushPowerCorrespondence());
			}
			return false;
		}
	}
	
	public static short getCooldown(MainMushPowers mushpowers) {
		return cooldowns.get(mushpowers);
	}
	
	
}
