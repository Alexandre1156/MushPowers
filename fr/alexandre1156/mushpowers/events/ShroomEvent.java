package fr.alexandre1156.mushpowers.events;

import java.util.Random;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public abstract class ShroomEvent {
	
	protected Random rand = new Random();
	
	/**No predicate*/
	protected abstract void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death);
	/**With predicate*/
	protected boolean onRenderGameOverlayPre(ElementType type){return false;}
	/**With predicate*/
	protected void onLivingEntityUseItemTick(int duration, EntityLivingBase ent, ItemStack item){}
	/**No predicate*/
	protected void onPlayerIteractItemRightClickItem(EntityPlayer p, World world, ItemStack item){}
	/**With predicate*/
	protected void onRenderGameOverlayPost(ScaledResolution resolution, ElementType type){}
	/**No predicate*/
	protected boolean onRenderLivingSpecialsPre(EntityLivingBase ent){return false;}
	/**No predicate*/
	protected void onTickPlayer(EntityPlayer p, Phase phase, Side side){}
	/**With predicate*/
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv){}
	/**No predicate*/
	protected boolean onRenderPlayerPre(EntityPlayer ent, float partialTick, double x, double y, double z, RenderPlayer render){return false;}
	/**No predicate*/
	protected void onPlayerLoggedIn(EntityPlayer p){}
	/**No predicate*/
	protected void onClientDisconnectionFromServer(){}
	/**No predicate*/
	protected void onLivingEntityUseItemFinish(EntityLivingBase entLiv, ItemStack item, World world){}
	/**No predicate*/
	protected void onEntityJoinWorld(Entity ent, World world) {}
	/**With predicate*/
	protected boolean onLivingEntityFall(Entity ent, EntityLivingBase entLiv, float distance, float damageMultiplier){return false;}
	/**With predicate*/
	protected float onLivingHurt(EntityLivingBase entLiv, DamageSource source, float amount) {return -1f;}
	/**With predicate*/
	protected boolean onLivingDeath(EntityLivingBase entLiv, DamageSource source) {return false;}
	/**No predicate*/
	protected ITextComponent onServerChat(String message, EntityPlayerMP player, ITextComponent component) {return component;}
	/**No predicate*/
	protected ITextComponent onClientChatReceived(ITextComponent message, byte type) {return message;}
	/**With predicate*/
	protected void onDrawScreen(GuiScreen gui, int mouseX, int mouseY, float renderPartialTicks) {}
	/**With predicate*/
	protected void onAnvilRepair(float breakChance, EntityPlayer p, ItemStack firstInput, ItemStack secondInput, ItemStack output) {}

	//protected void onPlayerOpenContainer(Container container, EntityPlayer p) {}
	
	//protected int onEnchantementLevelSet(int enchantRow, ItemStack item, int level, World world, int power, int originalLevel) {return originalLevel;}

	
}
