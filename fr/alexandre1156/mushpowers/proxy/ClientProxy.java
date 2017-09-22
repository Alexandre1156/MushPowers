package fr.alexandre1156.mushpowers.proxy;

import java.lang.reflect.Field;
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
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
	
	public static SquidRender squidRender;
	public static GhostRender ghostRender;
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		List<ResourceLocation> list = Lists.newArrayList();
		list.add(new ResourceLocation(Reference.MOD_ID, "shroomrodstick"));
		this.mushPowersCompatibleEntityItems.add(this.itemShroomRodStick);
		int metadata = 0;
		try {
			for(Field field : this.getClass().getFields()) {
				if(field.get(this) instanceof ItemMushPowers) {
					ItemMushPowers power = (ItemMushPowers) field.get(this);
					if(power.isEntityLivingCompatible()) {
						metadata++;
						list.add(new ResourceLocation(Reference.MOD_ID, "shroomrodstick_"+power.getRegistryName().getResourcePath()));
						this.mushPowersCompatibleEntityItems.add(power);
						this.registerRenderItemWithMetadata(this.itemShroomRodStick, metadata, list.get(list.size()-1));
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
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
	
//	public void registerRenderBlock(Block block){
//		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
//	}
//	
//	public void registerRenderItem(Item item){
//		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
//	}
//	
	public void registerRenderItemWithMetadata(Item item, int metadata, ResourceLocation registry) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(registry, "inventory"));
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		squidRender = new SquidRender(Minecraft.getMinecraft().getRenderManager());
		ghostRender = new GhostRender(Minecraft.getMinecraft().getRenderManager());
	}
	
	@SubscribeEvent
	public void registerModel(ModelRegistryEvent e) {
		try {
			for(Field field : this.getClass().getFields()) {
				if(field.get(this) instanceof Item) {
					Item item = (Item) field.get(this);
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
				} else if(field.get(this) instanceof Block) {
					Block block = (Block) field.get(this);
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
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