package fr.alexandre1156.mushpowers.proxy;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.mppi.ContainerMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.mppi.GuiMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.mppi.TileEntityMushPowersPowerInjector;
import fr.alexandre1156.mushpowers.render.GhostRender;
import fr.alexandre1156.mushpowers.render.SquidRender;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy{
	
	public static SquidRender squidRender;
	public static GhostRender ghostRender;
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		this.registerRenderBlock(blockMPPI);
		this.registerRenderBlock(bushSquidMush);
		this.registerRenderBlock(bushChickenMush);
		this.registerRenderBlock(bushCursedMush);
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
		NetworkRegistry.INSTANCE.registerGuiHandler(MushPowers.instance, new IGuiHandler() {
			
			private static final int MUSHPOWERS_POWER_INJECTOR = 0;
			
			@Override
			public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
				return ID == MUSHPOWERS_POWER_INJECTOR ? new ContainerMushPowersPowerInjector(player.field_71071_by, (TileEntityMushPowersPowerInjector) world.func_175625_s(new BlockPos(x, y, z))) : null;
			}
			
			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
				return ID == MUSHPOWERS_POWER_INJECTOR ? new GuiMushPowersPowerInjector(player.field_71071_by, (TileEntityMushPowersPowerInjector) world.func_175625_s(new BlockPos(x, y, z))) : null;
			}
		});
	}
	
	public void registerRenderBlock(Block block){
		ModelLoader.setCustomModelResourceLocation(Item.func_150898_a(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public void registerRenderItem(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		squidRender = new SquidRender(Minecraft.func_71410_x().func_175598_ae());
		ghostRender = new GhostRender(Minecraft.func_71410_x().func_175598_ae());
	}
	
}
