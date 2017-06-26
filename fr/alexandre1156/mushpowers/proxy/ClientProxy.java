package fr.alexandre1156.mushpowers.proxy;

import java.util.List;

import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.Reference;
import fr.alexandre1156.mushpowers.items.shrooms.ItemMushPowers;
import fr.alexandre1156.mushpowers.mppi.ContainerMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.mppi.GuiMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.mppi.TileEntityMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import fr.alexandre1156.mushpowers.render.GhostRender;
import fr.alexandre1156.mushpowers.render.SquidRender;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy{
	
	public static SquidRender squidRender;
	public static GhostRender ghostRender;
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		this.registerRenderBlock(blockMPPI);
		this.registerRenderBlock(bushSquidshroom);
		this.registerRenderBlock(bushChickenshroom);
		this.registerRenderBlock(bushCursedshroom);
		this.registerRenderBlock(bushFlyshroom);
		this.registerRenderBlock(bushGhostshroom);
		this.registerRenderBlock(bushHostileshroom);
		this.registerRenderBlock(bushElectricshroom);
		this.registerRenderBlock(bushHostileshroom);
		this.registerRenderBlock(bushLowershroom);
		this.registerRenderBlock(bushPizzashroom);
		this.registerRenderBlock(bushRegenshroom);
		this.registerRenderBlock(bushZombieawayShroom);
		this.registerRenderBlock(bushShieldshroom);
		this.registerRenderBlock(bushRandomshroom);
		this.registerRenderItem(itemMushElexir);
		this.registerRenderItem(itemRegenshroom);
		this.registerRenderItem(itemSquidshroom);
		//this.registerRenderItem(itemThorshroom);
		this.registerRenderItem(itemGhostshroom);
		this.registerRenderItem(itemPizzashroom);
		this.registerRenderItem(itemZombieawayShroom);
		this.registerRenderItem(itemHostileshroom);
		this.registerRenderItem(itemChickenshroom);
		this.registerRenderItem(itemElectricshroom);
		this.registerRenderItem(itemCursedshroom);
		this.registerRenderItem(itemShieldshroom);
		this.registerRenderItem(itemFlyshroom);
		this.registerRenderItem(itemLowershroom);
		this.registerRenderItem(itemRandomshroom);
		
		List<ResourceLocation> list = Lists.newArrayList();
		list.add(new ResourceLocation(Reference.MOD_ID, "shroomrodstick"));
		this.mushPowersCompatibleEntityItems.add(this.itemShroomRodStick);
		//mushPowersToMetadata.put("shroomrodstick", 0);
		this.registerRenderItemWithMetadata(this.itemShroomRodStick, 0, list.get(0));
		int metadata = 0;
		for(ItemMushPowers power : this.mushPowersItems) {
			if(power.isEntityLivingCompatible()) {
				metadata++;
				//System.out.println(power.getRegistryName().getResourcePath()+" - "+mushPowersToMetadata.size()+" - "+metadataToMushPowers.size()+" - "+list.get(list.size()-1));
				list.add(new ResourceLocation(Reference.MOD_ID, "shroomrodstick_"+power.getRegistryName().getResourcePath()));
				this.mushPowersCompatibleEntityItems.add(power);
				//mushPowersToMetadata.put(power.getRegistryName().getResourcePath(), mushPowersToMetadata.size());
				//metadataToMushPowers.put(metadataToMushPowers.size(), power);
				this.registerRenderItemWithMetadata(this.itemShroomRodStick, metadata, list.get(list.size()-1));
			}
		}
		ResourceLocation[] resLoc = new ResourceLocation[list.size()];
		resLoc = list.toArray(resLoc);
		ModelLoader.registerItemVariants(itemShroomRodStick, resLoc);

		NetworkRegistry.INSTANCE.registerGuiHandler(MushPowers.instance, new IGuiHandler() {
			
			private static final int MUSHPOWERS_POWER_INJECTOR = 0;
			
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
				return ID == MUSHPOWERS_POWER_INJECTOR ? new ContainerMushPowersPowerInjector(player.inventory, (TileEntityMushPowersPowerInjector) world.getTileEntity(new BlockPos(x, y, z))) : null;
			}
			
			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
				return ID == MUSHPOWERS_POWER_INJECTOR ? new GuiMushPowersPowerInjector(player.inventory, (TileEntityMushPowersPowerInjector) world.getTileEntity(new BlockPos(x, y, z))) : null;
			}
		});
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void registerRenderBlock(Block block){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public void registerRenderItem(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public void registerRenderItemWithMetadata(Item item, int metadata, ResourceLocation registry) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(registry, "inventory"));
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		squidRender = new SquidRender(Minecraft.getMinecraft().getRenderManager());
		ghostRender = new GhostRender(Minecraft.getMinecraft().getRenderManager());
	}
	
	
	@Override
	public void spawnShroomParticle(Entity ent, ShroomParticle particleType) {
		double motionX = ent.world.rand.nextGaussian() * 0.02D;
		double motionY = ent.world.rand.nextGaussian() * 0.02D;
		double motionZ = ent.world.rand.nextGaussian() * 0.02D;
		Particle particle = particleType.getFactory().createParticle(0, ent.world, ent.posX + ent.world.rand.nextFloat() * ent.width * 2.0F - ent.width, 
				ent.posY + 0.5D + ent.world.rand.nextFloat() * ent.height, ent.posZ + ent.world.rand.nextFloat() * ent.width  * 2.0F - ent.width, 
				motionX, motionY, motionZ, 0);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre e){
		e.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "particle/feather"));
	}
	
	
}