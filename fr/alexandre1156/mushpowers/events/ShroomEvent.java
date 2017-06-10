package fr.alexandre1156.mushpowers.events;

import java.util.Random;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public abstract class ShroomEvent {
	
	protected Random rand = new Random();
	
	protected abstract void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death);
	
	protected boolean onRenderGameOverlayPre(ElementType type){return false;}
	
	protected void onLivingEntityUseItemTick(int duration, EntityLivingBase ent, ItemStack item){}
	
	protected void onPlayerIteractItemRightClickItem(EntityPlayer p, World world, ItemStack item){}
	
	protected void onRenderGameOverlayPost(ScaledResolution resolution, ElementType type){}
	
	protected boolean onRenderLivingSpecialsPre(EntityLivingBase ent){return false;}
	
	protected void onTickPlayer(EntityPlayer p, Phase phase, Side side){}
	
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv){}
	
	protected boolean onRenderPlayerPre(EntityPlayer ent, float partialTick, double x, double y, double z, RenderPlayer render){return false;}
	
	protected void onPlayerLoggedIn(EntityPlayer p){}
	
	protected void onClientDisconnectionFromServer(){}
	
	protected void onLivingEntityUseItemFinish(EntityLivingBase entLiv, ItemStack item, World world){}

	protected void onEntityJoinWorld(Entity ent, World world) {}

	protected boolean onLivingEntityFall(Entity ent, EntityLivingBase entLiv, float distance, float damageMultiplier){return false;}

	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {return 0f;}

	protected boolean onLivingDeath(EntityLivingBase entLiv, DamageSource source) {return false;}

	protected ITextComponent onServerChat(String message, EntityPlayerMP player, ITextComponent component) {return component;}

	protected ITextComponent onClientChatReceived(ITextComponent message, byte type) {return message;}

	protected void onDrawScreen(GuiScreen gui, int mouseX, int mouseY, float renderPartialTicks) {}

	protected void onAnvilRepair(float breakChance, EntityPlayer p, ItemStack firstInput, ItemStack secondInput, ItemStack output) {}

	protected void onPlayerOpenContainer(Container container, EntityPlayer p) {}

	protected int onEnchantementLevelSet(int enchantRow, ItemStack item, int level, World world, int power, int originalLevel) {return originalLevel;}

	
}
