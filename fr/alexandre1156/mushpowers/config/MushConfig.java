package fr.alexandre1156.mushpowers.config;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MushConfig {

	public static Configuration config;
	//General
	public static ArrayList<String> playersCanSeeFakeRegenshroom;
	public static int repairCostLowershroom;
	public static int percentLowershroom;
	public static int foodRegenPizzashroom;
	public static int foodCountPizzashroom;
	public static int hearthRegenshroom;
	public static int damageAbsordPercentShieldshroom;
	public static int maxDamageAbsorbShieldshroom;
	//Cooldown
	public static int cooldownSquid;
	public static int cooldownChicken;
	public static int cooldownElectric;
	public static int cooldownFly;
	public static int cooldownGhost;
	public static int cooldownHostile;
	public static int cooldownZombieAway;
	//Desactive
	public static boolean desactiveSquid;
	public static boolean desactiveChicken;
	public static boolean desactiveCursed;
	public static boolean desactiveElectric;
	public static boolean desactiveFly;
	public static boolean desactiveGhost;
	public static boolean desactiveHostile;
	public static boolean desactiveLower;
	public static boolean desactivePizza;
	public static boolean desactiveRegen;
	public static boolean desactiveShield;
	public static boolean desactiveZombieAway;
	
	public static void syncConfig(){
		try {
			if(config != null) {
				//General
				playersCanSeeFakeRegenshroom = Lists.newArrayList(config.get(Configuration.CATEGORY_GENERAL, "Cursedshroom:PlayersCanSeeFakeRegenshroom", new String[]{"PlayerName"}, "Players who are warned if they have a fake Regenshroom in their hands.").getStringList());
				repairCostLowershroom = config.getInt("Lowershroom:RepairCost", Configuration.CATEGORY_GENERAL, 20, 1, 999, "The maximum saved level number");
				percentLowershroom = config.getInt("Lowershroom:PercentSaved", Configuration.CATEGORY_GENERAL, 25, 1, 99, "Purcent of level reduce");
				foodRegenPizzashroom = config.getInt("Pizzashroom:FoodRegen", Configuration.CATEGORY_GENERAL, 2, 1, 20, "Number of half-food added each time you eat");
				foodCountPizzashroom = config.getInt("Pizzashroom:FoodCount", Configuration.CATEGORY_GENERAL, 3, 1, 99, "Number of food you need to eat in order to desactive Lowershroom power");
				hearthRegenshroom = config.getInt("Regenshroom:HearthRegen", Configuration.CATEGORY_GENERAL, 6, 1, 20, "Number of half-hearth the Regenshroom give");
				maxDamageAbsorbShieldshroom = config.getInt("Shieldshroom:DamageAbsord", Configuration.CATEGORY_GENERAL, 10, 1, 999, "Number of half-hearth you need to lose to desactive Shieldshroom");
				damageAbsordPercentShieldshroom = config.getInt("Shieldshroom:PercentDamagedAbsorb", Configuration.CATEGORY_GENERAL, 25, 1, 99, "Percent of damage taken absorbed");
				//Cooldown
				cooldownSquid = config.getInt("Squidshroom", "MushCooldown", 6000, 10, 72000, "The Squidshroom cooldown in tick");
				cooldownChicken = config.getInt("Chickenshroom", "MushCooldown", 3600, 10, 72000, "The Chicekenshroom cooldown in tick");
				cooldownElectric = config.getInt("Eletricshroom", "MushCooldown", 18000, 10, 72000, "The Eletricshroom cooldown in tick");
				cooldownFly = config.getInt("Flyshroom", "MushCooldown", 1200, 10, 72000, "The Flyshroom cooldown in tick");
				cooldownGhost = config.getInt("Ghostshroom", "MushCooldown", 2700, 10, 72000, "The Ghostshroom cooldown in tick");
				cooldownHostile = config.getInt("Hostileshroom", "MushCooldown", 6000, 10, 72000, "The Hostileshroom cooldown in tick");
				cooldownZombieAway = config.getInt("ZombieAway Shroom", "MushCooldown", 6000, 10, 72000, "The ZombieAway Shroom cooldown in tick");
				//Desactive
				desactiveSquid = config.getBoolean("Squidshroom", "MushDesactived", false, "Choice if you want to desactive Squidshroom");
				desactiveChicken = config.getBoolean("Chickenshroom", "MushDesactived", false, "Choice if you want to desactive Chickenshroom");
				desactiveCursed = config.getBoolean("Cursedshroom", "MushDesactived", false, "Choice if you want to desactive Cursedshroom");
				desactiveElectric = config.getBoolean("Electricshroom", "MushDesactived", false, "Choice if you want to desactive Electricshroom");
				desactiveFly = config.getBoolean("Flyshroom", "MushDesactived", false, "Choice if you want to desactive Flyshroom");
				desactiveGhost = config.getBoolean("Ghostshroom", "MushDesactived", false, "Choice if you want to desactive Ghostshroom");
				desactiveHostile = config.getBoolean("Hostileshroom", "MushDesactived", false, "Choice if you want to desactive Hostileshroom");
				desactiveLower = config.getBoolean("Lowershroom", "MushDesactived", false, "Choice if you want to desactive Lowershroom");
				desactivePizza = config.getBoolean("Pizzashroom", "MushDesactived", false, "Choice if you want to desactive Pizzashroom");
				desactiveRegen = config.getBoolean("Regenshroom", "MushDesactived", false, "Choice if you want to desactive Regenshroom");
				desactiveShield = config.getBoolean("Shieldshroom", "MushDesactived", false, "Choice if you want to desactive Shieldshroom");
				desactiveZombieAway = config.getBoolean("ZombieAwayshroom", "MushDesactived", false, "Choice if you want to desactive ZombieAwayshroom");
				
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
		if(item.equals(CommonProxy.itemChickenshroom) || Item.getItemFromBlock(CommonProxy.bushChickenshroom).equals(item))
			return desactiveChicken;
		if(item.equals(CommonProxy.itemCursedshroom) || Item.getItemFromBlock(CommonProxy.bushCursedshroom).equals(item))
			return desactiveCursed;
		if(item.equals(CommonProxy.itemElectricshroom) || Item.getItemFromBlock(CommonProxy.bushElectricshroom).equals(item))
			return desactiveElectric;
		if(item.equals(CommonProxy.itemFlyshroom) || Item.getItemFromBlock(CommonProxy.bushFlyshroom).equals(item))
			return desactiveFly;
		if(item.equals(CommonProxy.itemGhostshroom) || Item.getItemFromBlock(CommonProxy.bushGhostshroom).equals(item))
			return desactiveGhost;
		if(item.equals(CommonProxy.itemHostileshroom) || Item.getItemFromBlock(CommonProxy.bushHostileshroom).equals(item))
			return desactiveHostile;
		if(item.equals(CommonProxy.itemLowershroom) || Item.getItemFromBlock(CommonProxy.bushLowershroom).equals(item))
			return desactiveLower;
		if(item.equals(CommonProxy.itemPizzashroom) || Item.getItemFromBlock(CommonProxy.bushPizzashroom).equals(item))
			return desactivePizza;
		if(item.equals(CommonProxy.itemRegenshroom) || Item.getItemFromBlock(CommonProxy.bushRegenshroom).equals(item))
			return desactiveRegen;
		if(item.equals(CommonProxy.itemShieldshroom) || Item.getItemFromBlock(CommonProxy.bushShieldshroom).equals(item))
			return desactiveShield;
		if(item.equals(CommonProxy.itemSquidshroom) || Item.getItemFromBlock(CommonProxy.bushSquidshroom).equals(item))
			return desactiveSquid;
		if(item.equals(CommonProxy.itemZombieawayShroom) || Item.getItemFromBlock(CommonProxy.bushZombieawayShroom).equals(item))
			return desactiveZombieAway;
		return false;
	}
	
	
}
