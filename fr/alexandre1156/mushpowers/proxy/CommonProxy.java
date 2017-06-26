package fr.alexandre1156.mushpowers.proxy;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.bushs.BlockBushChicken;
import fr.alexandre1156.mushpowers.bushs.BlockBushCursed;
import fr.alexandre1156.mushpowers.bushs.BlockBushElectric;
import fr.alexandre1156.mushpowers.bushs.BlockBushFly;
import fr.alexandre1156.mushpowers.bushs.BlockBushGhost;
import fr.alexandre1156.mushpowers.bushs.BlockBushHostile;
import fr.alexandre1156.mushpowers.bushs.BlockBushLower;
import fr.alexandre1156.mushpowers.bushs.BlockBushPizza;
import fr.alexandre1156.mushpowers.bushs.BlockBushRandom;
import fr.alexandre1156.mushpowers.bushs.BlockBushRegen;
import fr.alexandre1156.mushpowers.bushs.BlockBushShield;
import fr.alexandre1156.mushpowers.bushs.BlockBushSquid;
import fr.alexandre1156.mushpowers.bushs.BlockBushZombieAway;
import fr.alexandre1156.mushpowers.bushs.BushMush;
import fr.alexandre1156.mushpowers.capabilities.CapabilityHandler;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.IRegen;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushStorage;
import fr.alexandre1156.mushpowers.capabilities.RegenCap;
import fr.alexandre1156.mushpowers.capabilities.RegenStorage;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.events.ChickenshroomEvent;
import fr.alexandre1156.mushpowers.events.ElectricshroomEvent;
import fr.alexandre1156.mushpowers.events.EventHandler;
import fr.alexandre1156.mushpowers.events.FlyshroomEvent;
import fr.alexandre1156.mushpowers.events.GhostshroomEvent;
import fr.alexandre1156.mushpowers.events.HostileshroomEvent;
import fr.alexandre1156.mushpowers.events.LowershroomEvent;
import fr.alexandre1156.mushpowers.events.PizzashroomEvent;
import fr.alexandre1156.mushpowers.events.SharedMushEvent;
import fr.alexandre1156.mushpowers.events.ShieldshroomEvent;
import fr.alexandre1156.mushpowers.events.SquidshroomEvent;
import fr.alexandre1156.mushpowers.events.ZombieawayShroomEvent;
import fr.alexandre1156.mushpowers.items.ItemMushroomElixir;
import fr.alexandre1156.mushpowers.items.ItemShroomRodStick;
import fr.alexandre1156.mushpowers.items.shrooms.Chickenshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Cursedshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Electricshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Flyshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Ghostshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Hostileshroom;
import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.items.shrooms.Lowershroom;
import fr.alexandre1156.mushpowers.items.shrooms.Pizzashroom;
import fr.alexandre1156.mushpowers.items.shrooms.Randomshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Regenshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Shieldshroom;
import fr.alexandre1156.mushpowers.items.shrooms.Squidshroom;
import fr.alexandre1156.mushpowers.items.shrooms.ZombieawayShroom;
import fr.alexandre1156.mushpowers.mppi.BlockMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.mppi.TileEntityMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static Block blockMPPI;
	public static BushMush bushSquidshroom;
	public static BushMush bushChickenshroom;
	public static BushMush bushCursedshroom;
	public static BushMush bushFlyshroom;
	public static BushMush bushGhostshroom;
	public static BushMush bushHostileshroom;
	public static BushMush bushElectricshroom;
	public static BushMush bushLowershroom;
	public static BushMush bushPizzashroom;
	public static BushMush bushRegenshroom;
	public static BushMush bushShieldshroom;
	public static BushMush bushZombieawayShroom;
	public static BushMush bushRandomshroom;
	public static Item itemMushElexir;
	public static Item itemShroomRodStick;
	public static ItemFood itemRegenshroom;
	public static ItemFood itemSquidshroom;
	//public static ItemFood itemThorshroom;
	public static ItemFood itemGhostshroom;
	public static ItemFood itemPizzashroom;
	public static ItemFood itemZombieawayShroom;
	public static ItemFood itemHostileshroom;
	public static ItemFood itemChickenshroom;
	public static ItemFood itemElectricshroom;
	public static ItemFood itemCursedshroom;
	public static ItemFood itemShieldshroom;
	public static ItemFood itemFlyshroom;
	public static ItemFood itemLowershroom;
	public static ItemFood itemRandomshroom;
	//NE PAS OUBLIER D'ENRGISTRER L'ITEM DANS COMMONPROXY ET SON RENDER DANS CLIENTPROXY
	public static EventHandler eventHandler;
	/**All items in the mod*/
	protected static ArrayList<Item> itemsMod;
	/**All bush (mushsroom block) in the mod*/
	protected static ArrayList<BushMush> bushPowers;
	/**All items which can give power for player and entity*/
	protected static ArrayList<ItemMushPowers> mushPowersItems;
	/**All items which can give power for entity*/
	protected static ArrayList<Item> mushPowersCompatibleEntityItems;
	//protected static HashMap<String, Integer> mushPowersToMetadata; 
	//protected static HashMap<Integer, ItemMushPowers> metadataToMushPowers;
	
	public void preInit(FMLPreInitializationEvent e){
		itemsMod = Lists.newArrayList();
		bushPowers = Lists.newArrayList();
		mushPowersItems = Lists.newArrayList();
		mushPowersCompatibleEntityItems = Lists.newArrayList();
		//mushPowersToMetadata = Maps.newHashMapWithExpectedSize(32767);
		//metadataToMushPowers = Maps.newHashMapWithExpectedSize(32767);
		GameRegistry.registerTileEntity(TileEntityMushPowersPowerInjector.class, Reference.MOD_ID+":mppi");
		CapabilityManager.INSTANCE.register(IPlayerMush.class, new PlayerMushStorage(), PlayerMush.class);
		CapabilityManager.INSTANCE.register(IRegen.class, new RegenStorage(), RegenCap.class);
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(MushConfig.class);
		MinecraftForge.EVENT_BUS.register(eventHandler = new EventHandler());
		
		itemMushElexir = new ItemMushroomElixir();
		itemRegenshroom = new Regenshroom();
		itemSquidshroom = new Squidshroom();
		//itemThorshroom = new Thorshroom();
		itemGhostshroom = new Ghostshroom();
		itemPizzashroom = new Pizzashroom();
		itemZombieawayShroom = new ZombieawayShroom();
		itemHostileshroom = new Hostileshroom();
		itemChickenshroom = new Chickenshroom();
		itemElectricshroom = new Electricshroom();
		itemCursedshroom = new Cursedshroom();
		itemShieldshroom = new Shieldshroom();
		itemFlyshroom = new Flyshroom();
		itemLowershroom = new Lowershroom();
		itemRandomshroom = new Randomshroom();
		
		blockMPPI = new BlockMushPowersPowerInjector();
		bushSquidshroom = new BlockBushSquid();
		bushChickenshroom = new BlockBushChicken();
		bushCursedshroom = new BlockBushCursed();
		bushFlyshroom = new BlockBushFly();
		bushGhostshroom = new BlockBushGhost();
		bushHostileshroom = new BlockBushHostile();
		bushElectricshroom = new BlockBushElectric();
		bushPizzashroom = new BlockBushPizza();
		bushLowershroom = new BlockBushLower();
		bushShieldshroom = new BlockBushShield();
		bushRegenshroom = new BlockBushRegen();
		bushZombieawayShroom = new BlockBushZombieAway();
		bushRandomshroom = new BlockBushRandom();
		eventHandler.addShroomEvent(new SquidshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid() : false;
			}
			
		});
		eventHandler.addShroomEvent(new PizzashroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isPizzaEaten() : false;
			}
			
		});
		eventHandler.addShroomEvent(new ZombieawayShroomEvent(), Predicates.alwaysTrue());
		eventHandler.addShroomEvent(new HostileshroomEvent(), Predicates.alwaysTrue());
		eventHandler.addShroomEvent(new SharedMushEvent(), Predicates.alwaysTrue());
		eventHandler.addShroomEvent(new ChickenshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isChicken() : false;
			}
			
		});
		eventHandler.addShroomEvent(new GhostshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isGhost() : false;
			}
			
		});
		eventHandler.addShroomEvent(new ElectricshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isElectric() : false;
			}
			
		});
		eventHandler.addShroomEvent(new ShieldshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isShieldActive() : false;
			}
			
		});
		eventHandler.addShroomEvent(new FlyshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isFlying() : false;
			}
			
		});
		eventHandler.addShroomEvent(new LowershroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).isRepairCostLower() : false;
			}
			
		});
		this.register();
	}
	
	public void init(FMLInitializationEvent e){
		
	}

	public void postInit(FMLPostInitializationEvent e){
		
	}
	
	public void register(){
		this.registerBlock(blockMPPI);
		this.registerBlock(bushSquidshroom);
		this.registerBlock(bushChickenshroom);
		this.registerBlock(bushCursedshroom);
		this.registerBlock(bushFlyshroom);
		this.registerBlock(bushGhostshroom);
		this.registerBlock(bushElectricshroom);
		this.registerBlock(bushHostileshroom);
		this.registerBlock(bushLowershroom);
		this.registerBlock(bushPizzashroom);
		this.registerBlock(bushRegenshroom);
		this.registerBlock(bushShieldshroom);
		this.registerBlock(bushZombieawayShroom);
		this.registerBlock(bushRandomshroom);
		this.registerItem(itemMushElexir);
		this.registerItem(itemRegenshroom);
		this.registerItem(itemSquidshroom);
		//this.registerItem(itemThorshroom);
		this.registerItem(itemGhostshroom);
		this.registerItem(itemPizzashroom);
		this.registerItem(itemZombieawayShroom);
		this.registerItem(itemHostileshroom);
		this.registerItem(itemChickenshroom);
		this.registerItem(itemElectricshroom);
		this.registerItem(itemCursedshroom);
		this.registerItem(itemShieldshroom);
		this.registerItem(itemFlyshroom);
		this.registerItem(itemLowershroom);
		this.registerItem(itemRandomshroom);
		
		itemShroomRodStick = new ItemShroomRodStick();
		this.registerItem(itemShroomRodStick);
	}
	
	private void registerBlock(Block block){
		GameRegistry.register(block);
		Item itemBlock = new ItemBlock(block){
			@Override
			public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
				if(MushConfig.isMushPowersDesactived(this))
					tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
			}
		};
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
		this.itemsMod.add(itemBlock);
		if(block instanceof BushMush)
			this.bushPowers.add((BushMush) block);
	}
	
	private void registerItem(Item item){
		GameRegistry.register(item);
		this.itemsMod.add(item);
		if(item instanceof ItemMushPowers)
			this.mushPowersItems.add((ItemMushPowers) item);
	}
	
	public static EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public static boolean isModItem(Item itemToCheck){
		if(itemsMod.contains(itemToCheck))
			return true;
		return false;
	}
	
	public static ItemMushPowers getMushPowersByRegistryName(String registryName){
		for(ItemMushPowers item : mushPowersItems){
			if(item.getRegistryName().getResourcePath().equals(registryName))
				return item;
		}
		return null;
	}
	
	public static Item getMushPowersRodStickByMetadata(int metadata){
		return mushPowersCompatibleEntityItems.get(metadata);
	}
	
	public static int getRodStickMetadataWithMushPowersRodStickRegsistry(String registryName){
		int metadata = 0;
		for(Item item : mushPowersCompatibleEntityItems){
			if(item.getRegistryName().getResourcePath().equals(registryName))
				return metadata;
			metadata++;
		}
		return 0;
	}
	
	public static ArrayList<Item> getMushPowersCompatibleEntity(){
		return mushPowersCompatibleEntityItems;
	}
	
	public static ArrayList<ItemMushPowers> getMushPowers(){
		return mushPowersItems;
	}
	
	public static ArrayList<BushMush> getBushs(){
		return bushPowers;
	}
	
	public void spawnShroomParticle(Entity ent, ShroomParticle particleType){}
	
}
