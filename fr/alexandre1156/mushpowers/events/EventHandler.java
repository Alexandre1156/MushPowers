package fr.alexandre1156.mushpowers.events;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
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
	private ArrayList<Predicate> predicates;
	
	public EventHandler() {this.shrooms = Lists.newArrayList(); this.predicates = Lists.newArrayList();}
	
	@SubscribeEvent
	public void playerClone(PlayerEvent.Clone e){
		for(int i = 0; i < shrooms.size(); i++) 
			shrooms.get(i).onPlayerCloned(e.getEntityPlayer(), e.getOriginal(), e.isWasDeath());
		if (e.isWasDeath()) {
			PlayerMushProvider.resetPlayer(e.getEntityPlayer(), false);
			PlayerMushProvider.syncCapabilities(e.getEntityPlayer());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderGameOverlayPre(RenderGameOverlayEvent.Pre e){
		for(int i = 0; i < shrooms.size(); i++){
			if(predicates.get(i).apply(Minecraft.getMinecraft().player)) {
				boolean canceled = shrooms.get(i).onRenderGameOverlayPre(e.getType());
				if(canceled) e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void livingEntityUseItemTick(LivingEntityUseItemEvent.Tick e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(e.getEntityLiving()))
				shrooms.get(i).onLivingEntityUseItemTick(e.getDuration(), e.getEntityLiving(), e.getItem());
		}
	}
	
	@SubscribeEvent
	public void playerIteractItemRightClickItem(PlayerInteractEvent.RightClickItem e){
		for(int i = 0; i < shrooms.size(); i++) 
			shrooms.get(i).onPlayerIteractItemRightClickItem(e.getEntityPlayer(), e.getWorld(), e.getItemStack());
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderGameOverlayEventPost(RenderGameOverlayEvent.Post e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(Minecraft.getMinecraft().player))
				shrooms.get(i).onRenderGameOverlayPost(e.getResolution(), e.getType());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderLivingSpecialsPre(RenderLivingEvent.Specials.Pre<EntityPlayer> e){
		for(int i = 0; i < shrooms.size(); i++){
			boolean canceled = shrooms.get(i).onRenderLivingSpecialsPre(e.getEntity());
			if(canceled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void tickPlayer(TickEvent.PlayerTickEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			shrooms.get(i).onTickPlayer(e.player, e.phase, e.side);
		}
	}
	
	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(e.getEntityLiving())) 
				shrooms.get(i).onLivingUpdate(e.getEntity(), e.getEntityLiving());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderPlayerPre(RenderPlayerEvent.Pre e){
		for(int i = 0; i < shrooms.size(); i++){
			boolean canceled = shrooms.get(i).onRenderPlayerPre(e.getEntityPlayer(), e.getPartialRenderTick(), e.getX(), e.getY(), e.getZ(), e.getRenderer());
			if(canceled) e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent e){
		for(int i = 0; i < shrooms.size(); i++)
			shrooms.get(i).onPlayerLoggedIn(e.player);
	}
	
	@SubscribeEvent
	public void clientDisconnectionFromServer(ClientDisconnectionFromServerEvent e){
		for(int i = 0; i < shrooms.size(); i++)
			shrooms.get(i).onClientDisconnectionFromServer();
	}
	
	@SubscribeEvent
	public void livingEntityUseItemFinish(LivingEntityUseItemEvent.Finish e){
		for(int i = 0; i < shrooms.size(); i++)
			shrooms.get(i).onLivingEntityUseItemFinish(e.getEntityLiving(), e.getItem(), e.getEntityLiving().getEntityWorld());
	}
	
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent e){
		for(int i = 0; i < shrooms.size(); i++)
			shrooms.get(i).onEntityJoinWorld(e.getEntity(), e.getWorld());
	}

	
	@SubscribeEvent
	public void livingFall(LivingFallEvent e){
		for(int i = 0; i < shrooms.size(); i++){
			if(predicates.get(i).apply(e.getEntityLiving())) {
				boolean cancelled = shrooms.get(i).onLivingEntityFall(e.getEntity(), e.getEntityLiving(), e.getDistance(), e.getDamageMultiplier());
				if(cancelled) e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void livingHurt(LivingHurtEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(e.getEntityLiving())) {
				float newAmount = shrooms.get(i).onLivingHurt(e.getEntityLiving(), e.getSource(), e.getAmount());
				if(newAmount >= 0) e.setAmount(newAmount);
			}
		}
	}
	
	@SubscribeEvent
	public void livingDeath(LivingDeathEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(e.getEntityLiving())) {
				boolean cancelled = shrooms.get(i).onLivingDeath(e.getEntityLiving(), e.getSource());
				if(cancelled) e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void serverChat(ServerChatEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			ITextComponent message = shrooms.get(i).onServerChat(e.getMessage(), e.getPlayer(), e.getComponent());
			if(message != null) e.setComponent(message);
		}
	}
	
	@SubscribeEvent
	public void clientChatReceived(ClientChatReceivedEvent e){
		for(int i = 0; i < shrooms.size(); i++){
			ITextComponent message = shrooms.get(i).onClientChatReceived(e.getMessage(), e.getType());
			if(message != null) e.setMessage(message);
		}
	}
	
//	@SubscribeEvent
//	public void enchantementLevelSet(EnchantmentLevelSetEvent e){
//		for(int i = 0; i < shrooms.size(); i++) {
//			int level = shrooms.get(i).onEnchantementLevelSet(e.getEnchantRow(), e.getItem(), e.getLevel(), e.getWorld(), e.getPower(), e.getOriginalLevel());
//			e.setLevel(level);
//		}
////		System.out.println("ENCHANTEMENT : "+e.getEnchantRow()+" - "+e.getLevel()+" - "+e.getOriginalLevel()+" - "+e.getPower()+" - "+e.getItem());
////		e.setLevel(e.getLevel()/4);
//	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawGuiScreen(GuiScreenEvent.DrawScreenEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(Minecraft.getMinecraft().player))
				shrooms.get(i).onDrawScreen(e.getGui(), e.getMouseX(), e.getMouseY(), e.getRenderPartialTicks());
		}
	}
	
	@SubscribeEvent
	public void anvilRepair(AnvilRepairEvent e){
		for(int i = 0; i < shrooms.size(); i++) {
			if(predicates.get(i).apply(e.getEntityPlayer()))
				shrooms.get(i).onAnvilRepair(e.getBreakChance(), e.getEntityPlayer(), e.getItemInput(), e.getIngredientInput(), e.getItemResult());
		}
	}
	
//	@SubscribeEvent
//	public void living(TickEvent.WorldTickEvent e) {
//		for(int i = 0; i < shrooms.size(); i++){
//			if(predicates.get(i).apply(e.)){
//				
//			}
//		}
//	}
	
//	@SubscribeEvent
//	public void playerContainerOpen(PlayerContainerEvent.Open e){
//		if(e.getContainer() instanceof ContainerPlayer) 
//			e.getEntityPlayer().openContainer = new ContainerEnchantment(e.getEntityPlayer().inventory, e.getEntityPlayer().world);
//		
//		for(int i = 0; i < shrooms.size(); i++)
//			shrooms.get(i).onPlayerOpenContainer(e.getContainer(), e.getEntityPlayer());
//	}
	
	public void addShroomEvent(ShroomEvent instance, Predicate predicate){
		this.shrooms.add(instance);
		this.predicates.add(predicate);
	}


}
