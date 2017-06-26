package fr.alexandre1156.mushpowers;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.network.PacketCapabilitiesMushPowers;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayer;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayersList;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayer;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayersList;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import fr.alexandre1156.mushpowers.structure.StructureMushPowerGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, updateJSON = "https://raw.githubusercontent.com/Alexandre1156/MushPowers/master/Update.json")
public class MushPowers {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper network;
	public static ArrayList<String> cursedShroomViewers;
	private HashMap<String, Boolean> squidPlayers;
	private HashMap<String, Boolean> ghostPlayers;

	@Instance(Reference.MOD_ID)
	public static MushPowers instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		MushConfig.config = new Configuration(event.getSuggestedConfigurationFile());
		MushConfig.config.load();
		//MushConfig.syncConfig();
		this.squidPlayers = Maps.newHashMap();
		this.ghostPlayers = Maps.newHashMap();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		this.network = new SimpleNetworkWrapper("mush");
		this.network.registerMessage(PacketCapabilitiesMushPowers.ClientHandler.class, PacketCapabilitiesMushPowers.class, 4, Side.CLIENT);
		this.network.registerMessage(PacketSquidPlayer.Handler.class, PacketSquidPlayer.class, 3, Side.CLIENT);
		this.network.registerMessage(PacketSquidPlayersList.Handler.class, PacketSquidPlayersList.class, 2, Side.CLIENT);
		this.network.registerMessage(PacketGhostPlayersList.Handler.class, PacketGhostPlayersList.class, 1, Side.CLIENT);
		this.network.registerMessage(PacketGhostPlayer.Handler.class, PacketGhostPlayer.class, 0, Side.CLIENT);
		GameRegistry.addRecipe(new ItemStack(CommonProxy.blockMPPI), new Object[] {
				" G ", "RBR", " F ", 'G', Items.GLASS_BOTTLE, 'R', Blocks.RED_MUSHROOM, 'B', Items.BREWING_STAND, 'F', Items.BLAZE_ROD
		});
		RecipeSorter.register("mush:craftshroomrodstick", ShroomRodStickRecipe.class, Category.SHAPELESS, "");
		GameRegistry.addRecipe(new ShroomRodStickRecipe());
		GameRegistry.registerWorldGenerator(new StructureMushPowerGenerator(), 100);
		MushConfig.syncConfig();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		//TODO:Random shroom -> bush
	}
	
	public void addSquidPlayer(String username, boolean value){
		this.squidPlayers.put(username, value);
	}
	
	public void addGhostPlayer(String username, boolean value){
		this.ghostPlayers.put(username, value);
	}
	
	public void setSquidPlayer(String username, boolean value){
		if(this.squidPlayers.containsKey(username))
			this.squidPlayers.replace(username, value);
	}
	
	public HashMap<String, Boolean> getSquidPlayers() {
		return squidPlayers;
	}
	
	public HashMap<String, Boolean> getGhostPlayers(){
		return this.ghostPlayers;
	}
	
	public boolean isSquid(String username){
		return this.squidPlayers.containsKey(username) ? this.squidPlayers.get(username) : false;
	}
	
	public boolean isGhost(String username){
		return this.ghostPlayers.containsKey(username) ? this.ghostPlayers.get(username) : false;
	}

	public static MushPowers getInstance() {
		return instance;
	}
	
	public static void syncConfig() {
		try {
			//config.load();
			//Property viewers = config.get(Configuration.CATEGORY_GENERAL, "Can See Cursed Shroom", new String[]{"PlayerName"}, "Players who are warned if they have a fake Regenshroom in their hands.");
			//Property biomeMushID = config.get(Configuration.CATEGORY_GENERAL, "BiomeMushPowersID", 255, "ID of the biome MushPowers. Change it if it have a conflit with an other biome ID.");
			//mushBiomeID = biomeMushID.getInt();
			cursedShroomViewers = Lists.newArrayList(); 
//			String[] viewersSeparate = viewers.getString().split(",");
//			for(int i = 0; i < viewers.getStringList().length; i++)
//				cursedShroomViewers.add(viewers.getStringList()[i]);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
