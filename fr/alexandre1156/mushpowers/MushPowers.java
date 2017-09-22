package fr.alexandre1156.mushpowers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.advancement.AllMushPowersTrigger;
import fr.alexandre1156.mushpowers.advancement.CraftMushPowersTrigger;
import fr.alexandre1156.mushpowers.advancement.RightClickEntityTrigger;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.network.PacketCapabilitiesMushPowers;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayer;
import fr.alexandre1156.mushpowers.network.PacketGhostPlayersList;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayer;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayersList;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import fr.alexandre1156.mushpowers.structure.StructureMushPowerGenerator;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, acceptedMinecraftVersions = "1.12", updateJSON = "https://raw.githubusercontent.com/Alexandre1156/MushPowers/master/Update.json")
public class MushPowers {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	public static final RightClickEntityTrigger PLAYER_RIGHT_CLICK_ENTITY = new RightClickEntityTrigger();
	public static final CraftMushPowersTrigger CRAFT_MUSHPOWERS = new CraftMushPowersTrigger();
	public static final AllMushPowersTrigger ALL_MUSH_POWERS = new AllMushPowersTrigger();
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
		this.registerCriteria(this.PLAYER_RIGHT_CLICK_ENTITY);
		this.registerCriteria(this.CRAFT_MUSHPOWERS);
		this.registerCriteria(this.ALL_MUSH_POWERS);
		MinecraftForge.EVENT_BUS.register(this);
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
		GameRegistry.registerWorldGenerator(new StructureMushPowerGenerator(), 100);
		MushConfig.syncConfig();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
	@SideOnly(Side.CLIENT)
	public void addSquidPlayer(String username, boolean value){
		if(!username.equals(Minecraft.getMinecraft().player.getName())) 
			this.squidPlayers.put(username, value);
	}
	
	@SideOnly(Side.CLIENT)
	public void addGhostPlayer(String username, boolean value){
		if(!username.equals(Minecraft.getMinecraft().player.getName()))
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
		
	private void registerCriteria(ICriterionTrigger criteria) {
		try {
			Method[] methods = CriteriaTriggers.class.getDeclaredMethods();
			for(Method method : methods) {
				if("register".equals(method.getName()) || "func_192118_a".equals(method.getName())) {
					method.setAccessible(true);
					method.invoke(null, criteria);
					System.out.println("REGISTER CRITTERIA "+criteria.getId().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
