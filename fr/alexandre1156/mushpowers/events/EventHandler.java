package fr.alexandre1156.mushpowers.events;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {

	private ArrayList<ShroomEvent> shrooms;
	
	public EventHandler() {this.shrooms = Lists.newArrayList();}
	
	@SubscribeEvent
	public void playerClone(PlayerEvent.Clone e){
		for(ShroomEvent se : shrooms)
			se.onPlayerCloned(e.getEntityPlayer(), e.getOriginal(), e.isWasDeath());
		if (e.isWasDeath())
			PlayerMushProvider.resetPlayer(e.getEntityPlayer(), false);
		PlayerMushProvider.syncCapabilities(e.getEntityPlayer());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderGameOverlayPre(RenderGameOverlayEvent.Pre e){
		for(ShroomEvent se : shrooms){
			boolean canceled = se.onRenderGameOverlayPre(e.getType());
			if(canceled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void livingEntityUseItemTick(LivingEntityUseItemEvent.Tick e){
		for(ShroomEvent se : shrooms)
			se.onLivingEntityUseItemTick(e.getDuration(), e.getEntityLiving(), e.getItem());
	}
	
	@SubscribeEvent
	public void playerIteractItemRightClickItem(PlayerInteractEvent.RightClickItem e){
		for(ShroomEvent se : shrooms)
			se.onPlayerIteractItemRightClickItem(e.getEntityPlayer(), e.getWorld(), e.getItemStack());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderGameOverlayEventPost(RenderGameOverlayEvent.Post e){
		for(ShroomEvent se : shrooms)
			se.onRenderGameOverlayPost(e.getResolution(), e.getType());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderLivingSpecialsPre(RenderLivingEvent.Specials.Pre<EntityPlayer> e){
		for(ShroomEvent se : shrooms){
			boolean canceled = se.onRenderLivingSpecialsPre(e.getEntity());
			if(canceled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void tickPlayer(TickEvent.PlayerTickEvent e){
		for(ShroomEvent se : shrooms)
			se.onTickPlayer(e.player, e.phase, e.side);
	}
	
	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent e){
		for(ShroomEvent se : shrooms)
			se.onLivingUpdate(e.getEntity(), e.getEntityLiving());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderPlayerPre(RenderPlayerEvent.Pre e){
		for(ShroomEvent se : shrooms){
			boolean canceled = se.onRenderPlayerPre(e.getEntityPlayer(), e.getPartialRenderTick(), e.getX(), e.getY(), e.getZ(), e.getRenderer());
			if(canceled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent e){
		for(ShroomEvent se : this.shrooms)
			se.onPlayerLoggedIn(e.player);
	}
	
	@SubscribeEvent
	public void clientDisconnectionFromServer(ClientDisconnectionFromServerEvent e){
		for(ShroomEvent se : this.shrooms)
			se.onClientDisconnectionFromServer();
	}
	
	@SubscribeEvent
	public void livingEntityUseItemFinish(LivingEntityUseItemEvent.Finish e){
		for(ShroomEvent se : this.shrooms)
			se.onLivingEntityUseItemFinish(e.getEntityLiving(), e.getItem(), e.getEntityLiving().func_130014_f_());
	}
	
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent e){
		for(ShroomEvent se : this.shrooms)
			se.onEntityJoinWorld(e.getEntity(), e.getWorld());
	}

	
	@SubscribeEvent
	public void livingFall(LivingFallEvent e){
		for(ShroomEvent se : this.shrooms){
			boolean cancelled = se.onLivingEntityFall(e.getEntity(), e.getEntityLiving(), e.getDistance(), e.getDamageMultiplier());
			if(cancelled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void livingHurt(LivingHurtEvent e){
		for(ShroomEvent se : this.shrooms) {
			float newAmount = se.onLivingHurt(e.getEntityLiving(), e.getSource(), e.getAmount());
			if(newAmount > 0) e.setAmount(newAmount);
		}
	}
	
	@SubscribeEvent
	public void livingDeath(LivingDeathEvent e){
		for(ShroomEvent se : this.shrooms) {
			boolean cancelled = se.onLivingDeath(e.getEntityLiving(), e.getSource());
			if(cancelled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void serverChat(ServerChatEvent e){
		for(ShroomEvent se : this.shrooms) {
			ITextComponent message = se.onServerChat(e.getMessage(), e.getPlayer(), e.getComponent());
			if(message != null) e.setComponent(message);
		}
	}
	
	@SubscribeEvent
	public void clientChatReceived(ClientChatReceivedEvent e){
		for(ShroomEvent se : this.shrooms){
			ITextComponent message = se.onClientChatReceived(e.getMessage(), e.getType());
			if(message != null) e.setMessage(message);
		}
	}
	
	@SubscribeEvent
	public void enchantementLevelSet(EnchantmentLevelSetEvent e){
		for(ShroomEvent se : this.shrooms) {
			int level = se.onEnchantementLevelSet(e.getEnchantRow(), e.getItem(), e.getLevel(), e.getWorld(), e.getPower(), e.getOriginalLevel());
			e.setLevel(level);
		}
//		System.out.println("ENCHANTEMENT : "+e.getEnchantRow()+" - "+e.getLevel()+" - "+e.getOriginalLevel()+" - "+e.getPower()+" - "+e.getItem());
//		e.setLevel(e.getLevel()/4);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawGuiScreen(GuiScreenEvent.DrawScreenEvent e){
		for(ShroomEvent se : this.shrooms)
			se.onDrawScreen(e.getGui(), e.getMouseX(), e.getMouseY(), e.getRenderPartialTicks());
	}
	
	@SubscribeEvent
	public void anvilRepair(AnvilRepairEvent e){
		for(ShroomEvent se : this.shrooms)
			se.onAnvilRepair(e.getBreakChance(), e.getEntityPlayer(), e.getItemInput(), e.getIngredientInput(), e.getItemResult());
	}
	
	@SubscribeEvent
	public void playerContainerOpen(PlayerContainerEvent.Open e){
		if(e.getContainer() instanceof ContainerPlayer) 
			e.getEntityPlayer().field_71070_bA = new ContainerEnchantment(e.getEntityPlayer().field_71071_by, e.getEntityPlayer().field_70170_p);
		
		for(ShroomEvent se : this.shrooms)
			se.onPlayerOpenContainer(e.getContainer(), e.getEntityPlayer());
	}
	
	public void addShroomEvent(ShroomEvent instance){
		this.shrooms.add(instance);
	}


}
