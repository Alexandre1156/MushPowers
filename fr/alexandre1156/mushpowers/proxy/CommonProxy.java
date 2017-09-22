package fr.alexandre1156.mushpowers.proxy;

import java.lang.reflect.Field;
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
import fr.alexandre1156.mushpowers.capabilities.eat.EatMushPowers;
import fr.alexandre1156.mushpowers.capabilities.eat.EatMushPowersStorage;
import fr.alexandre1156.mushpowers.capabilities.eat.IEatMushPower;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushStorage;
import fr.alexandre1156.mushpowers.capabilities.regen.IRegen;
import fr.alexandre1156.mushpowers.capabilities.regen.RegenCap;
import fr.alexandre1156.mushpowers.capabilities.regen.RegenStorage;
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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
		CapabilityManager.INSTANCE.register(IEatMushPower.class, new EatMushPowersStorage(), EatMushPowers.class);
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(MushConfig.class);
		MinecraftForge.EVENT_BUS.register(this);
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
		
		itemShroomRodStick = new ItemShroomRodStick();
		
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
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).getBoolean(Squidshroom.IS_SQUID) : false;
			}
			
		});
		eventHandler.addShroomEvent(new PizzashroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null).getInteger(Pizzashroom.PIZZA_COUNT) > 0 : false;
			}
			
		});
		eventHandler.addShroomEvent(new ZombieawayShroomEvent(), Predicates.alwaysTrue());
		eventHandler.addShroomEvent(new HostileshroomEvent(), Predicates.alwaysTrue());
		eventHandler.addShroomEvent(new SharedMushEvent(), Predicates.alwaysTrue());
		eventHandler.addShroomEvent(new ChickenshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null)
						.getBoolean(Chickenshroom.IS_CHICKEN) : false;
			}
			
		});
		eventHandler.addShroomEvent(new GhostshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null)
						.getBoolean(Ghostshroom.IS_GHOST) : false;
			}
			
		});
		eventHandler.addShroomEvent(new ElectricshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null)
						.getBoolean(Electricshroom.IS_ELECTRIC) : false;
			}
			
		});
		eventHandler.addShroomEvent(new ShieldshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null)
						.getInteger(Shieldshroom.DAMAGE_ABSORB) > 0 : false;
			}
			
		});
		eventHandler.addShroomEvent(new FlyshroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null)
						.getBoolean(Flyshroom.IS_FLY) : false;
			}
			
		});
		eventHandler.addShroomEvent(new LowershroomEvent(), new Predicate<Entity>() {

			@Override
			public boolean apply(Entity input) {
				return input instanceof EntityLivingBase ? ((EntityLivingBase) input).getCapability(PlayerMushProvider.MUSH_CAP, null)
						.getInteger(Lowershroom.LOWER_REPAIR) > 0 : false;
			}
			
		});
	}
	
	public void init(FMLInitializationEvent e){
		
	}

	public void postInit(FMLPostInitializationEvent e){
		
	}
	
	@SubscribeEvent
	public void registerItem(RegistryEvent.Register<Item> e) {
		try {
			for(Field field : this.getClass().getFields()) {
				if(field.get(this) instanceof Item) {
					Item item = (Item) field.get(this);
					e.getRegistry().register(item);
					this.itemsMod.add(item);
					if(item instanceof ItemMushPowers)
						this.mushPowersItems.add((ItemMushPowers) item);
				} else if(field.get(this) instanceof Block) {
					Block block = (Block) field.get(this);
					ItemBlock itemBlock = new ItemBlock(block) {
						@Override
						public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
							if(MushConfig.isMushPowersDesactived(this))
								tooltip.add(ChatFormatting.RED+"THIS SHROOM IS DESACTIVED");
							super.addInformation(stack, worldIn, tooltip, flagIn);
						}
					};
					itemBlock.setRegistryName(block.getRegistryName());
					e.getRegistry().register(itemBlock);
					this.itemsMod.add(itemBlock);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void registerBlock(RegistryEvent.Register<Block> e) {
		try {
			for(Field field : this.getClass().getFields()) {
				if(field.get(this) instanceof Block) {
					Block block = (Block) field.get(this);
					e.getRegistry().register(block);
					if(block instanceof BushMush)
						this.bushPowers.add((BushMush) block);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
	
	public enum Mushs {
		CHICKEN("chicken", true, (ItemMushPowers) CommonProxy.itemChickenshroom, (short) 3600), 
		CURSED("cursed", (ItemMushPowers) CommonProxy.itemCursedshroom), 
		ELECTRIC("electric", true, (ItemMushPowers) CommonProxy.itemElectricshroom, (short) 18000), 
		FLY("fly", true, (ItemMushPowers) CommonProxy.itemFlyshroom, (short) 1200), 
		GHOST("ghost", true, (ItemMushPowers) CommonProxy.itemGhostshroom, (short) 2700), 
		HOSTILE("hostile", true, (ItemMushPowers) CommonProxy.itemHostileshroom, (short) 6000), 
		LOWER("lower", (ItemMushPowers) CommonProxy.itemLowershroom), 
		PIZZA("pizza", (ItemMushPowers) CommonProxy.itemPizzashroom), 
		RANDOM("random", (ItemMushPowers) CommonProxy.itemRandomshroom), 
		REGEN("regen", (ItemMushPowers) CommonProxy.itemRegenshroom), 
		SHIELD("shield", (ItemMushPowers) CommonProxy.itemShieldshroom), 
		SQUID("squid", true, (ItemMushPowers) CommonProxy.itemSquidshroom, (short) 6000), 
		//THOR("thor", (ItemMushPowers) CommonProxy.itemThorshroom),
		ZOMBIEAWAY("zombieaway", true, (ItemMushPowers) CommonProxy.itemZombieawayShroom, (short) 6000);
		
		private String id;
		private boolean big;
		private ItemMushPowers powerInstance;
		private short timeLeft, defaultTimeLeft;
		
		private Mushs(String name, ItemMushPowers power) {
			this(name, false, power, (short) 0);
		}
		
		private Mushs(String name, boolean isBig, ItemMushPowers power, short defaultTimeLeftValue) {
			this.id = name;
			this.big = isBig;
			this.powerInstance = power;
			this.timeLeft = 0;
			this.defaultTimeLeft = defaultTimeLeftValue;
		}
		
		public String getId() {
			return id;
		}
		
		public boolean isBig() {
			return big;
		}
		
		public void setTimeLeft(short timeLeft){
			this.timeLeft = timeLeft;
		}
		
		public short getTimeLeft(){
			return this.timeLeft;
		}
		
		public ItemMushPowers getItemInstance() {
			return powerInstance;
		}
		
		public short getCooldownDefaultValue() {
			return defaultTimeLeft;
		}
		
		public static Mushs getMushsByID(String id) {
			for(Mushs mush : values()) {
				if(mush.getId().equals(id))
					return mush;
			}
			return null;
		}
		
	}
	
	public void spawnShroomParticle(Entity ent, ShroomParticle particleType){}
	
}
