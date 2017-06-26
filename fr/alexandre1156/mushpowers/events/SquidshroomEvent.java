package fr.alexandre1156.mushpowers.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.network.PacketSquidPlayersList;
import fr.alexandre1156.mushpowers.proxy.ClientProxy;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;


public class SquidshroomEvent extends ShroomEvent {
	
	//private Random rand = new Random();
	private ResourceLocation BUBBLE_BAR_TEXTURE = new ResourceLocation("textures/gui/icons.png");
	//private boolean isSquid;
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if (death) {
			MushPowers.getInstance().getSquidPlayers().replace(p.getName(), false);
			PlayerMushProvider.sendSquidPacket(p, false);
		} else {
			mush.setSquid(mush2.isSquid());
			mush.setSquidAir(mush2.getSquidAir());
			mush.setCooldown(MainMushPowers.SQUID, mush2.getCooldown(MainMushPowers.SQUID));
		}
	}

	@Override
	public boolean onRenderGameOverlayPre(ElementType type) {
		if (type == ElementType.AIR)
			return true;
		return false;
	}
	
	@Override
	protected void onPlayerIteractItemRightClickItem(EntityPlayer p, World world, ItemStack item) {
		if (p.fishEntity != null && item.getItem() == Items.FISHING_ROD && !world.isRemote) {
			EntityFishHook hook = p.fishEntity;
			List<Entity> ents = world.getEntitiesWithinAABBExcludingEntity(hook, hook.getEntityBoundingBox().expand(2D, 2D, 2D));
			Iterator<Entity> iter = ents.iterator();
			while (iter.hasNext()) {
				Entity ent = iter.next();
				if (ent instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) ent;
					if (!player.getName().equals(p.getName()) && player.getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid()) {
						player.inventory.clear();
						player.setHealth(0f);
						p.sendMessage(new TextComponentTranslation("squid.hooked.message", p.getName()));
					}
				}
			}
		}
	}

	@Override
	protected void onRenderGameOverlayPost(ScaledResolution resolution, ElementType type) {
		if (type == ElementType.ALL) {
			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().getRenderViewEntity();
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if (!player.isCreative()) {
				Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
				int right_height = 39;
				Minecraft.getMinecraft().mcProfiler.startSection("air");
				GlStateManager.enableBlend();
				int left = resolution.getScaledWidth() / 2 + 91;
				int top = resolution.getScaledHeight() - right_height;

				if (!player.isInsideOfMaterial(Material.WATER)) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(BUBBLE_BAR_TEXTURE);
					int air = mush.getSquidAir();
					int full = MathHelper.ceil((double) (air - 2) * 10.0D / 300.0D);
					int partial = MathHelper.ceil((double) air * 10.0D / 300.0D) - full;

					for (int i = 0; i < full + partial; ++i) 
						Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(left - i * 8 - 9, top - 10, (i < full ? 16 : 25), 18, 9, 9);
					right_height += 10;
				}

				GlStateManager.disableBlend();
				Minecraft.getMinecraft().mcProfiler.endSection();
			}
		}
	}
	
	@Override
	protected boolean onRenderLivingSpecialsPre(EntityLivingBase ent) {
		if (ent instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) ent;
			if (MushPowers.getInstance().isSquid(p.getDisplayNameString()))
				 return true;
		}
		return super.onRenderLivingSpecialsPre(ent);
	}

	@Override
	protected void onTickPlayer(EntityPlayer p, Phase phase, Side side) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(phase == TickEvent.Phase.END){
			if(side.isClient() && mush.isSquid() && p.getName().equals(Minecraft.getMinecraft().player.getName())) 
				this.setSquidBoundingBoxToPlayer(p, 0.8f, 0.8f, 0.4f);
			else if(side.isClient() && MushPowers.getInstance().isSquid(p.getName()))
				this.setSquidBoundingBoxToPlayer(p, 0.8f, 0.8f, 0.4f);
			else if(side.isServer() && p.getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid())
				this.setSquidBoundingBoxToPlayer(p, 0.8f, 0.8f, 0.4f);
			else if(p.eyeHeight != p.getDefaultEyeHeight())
				p.eyeHeight = p.getDefaultEyeHeight();
		}
		
		if(phase == TickEvent.Phase.END && mush.isSquid() && side.isClient() && p.isInsideOfMaterial(Material.WATER)){
			p.motionX *= 1.1D;
			p.motionY *= 1.1D;
			p.motionZ *= 1.1D;
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if (entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if (!p.world.isRemote) {
				if(mush.getCooldown(MainMushPowers.SQUID) % 1200 == 0)
					p.sendStatusMessage(new TextComponentTranslation("squid.time.left.min", (mush.getCooldown(MainMushPowers.SQUID)/1200)).setStyle(new Style().setColor(TextFormatting.AQUA)), true);
				else if(mush.getCooldown(MainMushPowers.SQUID) == 600 || mush.getCooldown(MainMushPowers.SQUID) == 200 || mush.getCooldown(MainMushPowers.SQUID) == 180 || mush.getCooldown(MainMushPowers.SQUID) == 160 || mush.getCooldown(MainMushPowers.SQUID) == 140 || mush.getCooldown(MainMushPowers.SQUID) == 120 || mush.getCooldown(MainMushPowers.SQUID) == 100 || mush.getCooldown(MainMushPowers.SQUID) == 80 || mush.getCooldown(MainMushPowers.SQUID) == 60 || mush.getCooldown(MainMushPowers.SQUID) == 40 || mush.getCooldown(MainMushPowers.SQUID) == 20)
					p.sendStatusMessage(new TextComponentTranslation("squid.time.left.sec", (mush.getCooldown(MainMushPowers.SQUID)/20)).setStyle(new Style().setColor(TextFormatting.DARK_AQUA)), true);
				else if(mush.getCooldown(MainMushPowers.SQUID) <= 0){
					mush.setCooldown(MainMushPowers.SQUID, (short) 0);
					mush.setSquid(false);
					mush.setSquidAir(300);
					MushPowers.getInstance().setSquidPlayer(Minecraft.getMinecraft().player.getName(), false);
					PlayerMushProvider.sendSquidPacket(p, false);
					PlayerMushProvider.syncCapabilities(p);
				}
				mush.setCooldown(MainMushPowers.SQUID, (short) (mush.getCooldown(MainMushPowers.SQUID) - 1));
				PlayerMushProvider.syncCapabilities(p);
				p.setAir(300);
				if (!p.isInsideOfMaterial(Material.WATER) && !p.canBreatheUnderwater()
						&& !p.isPotionActive(MobEffects.WATER_BREATHING) && !p.capabilities.disableDamage) {
					mush.setSquidAir(this.decreasePlayerAirSupply(p, mush.getSquidAir()));
					if (mush.getSquidAir() == -20) {
						mush.setSquidAir(0);

						for (int i = 0; i < 8; ++i) {
							float f2 = this.rand.nextFloat() - this.rand.nextFloat();
							float f = this.rand.nextFloat() - this.rand.nextFloat();
							float f1 = this.rand.nextFloat() - this.rand.nextFloat();
							p.getEntityWorld().spawnParticle(EnumParticleTypes.WATER_BUBBLE, p.posX + (double) f2,
									p.posY + (double) f, p.posZ + (double) f1, p.motionX, p.motionY, p.motionZ,
									new int[0]);
						}

						p.attackEntityFrom(DamageSource.DROWN, 2.0F);
					}
				} else
					mush.setSquidAir(300);
			}
		}
	}

	@Override
	protected boolean onRenderPlayerPre(EntityPlayer p, float partialTick, double x, double y, double z, RenderPlayer render) {
		if (MushPowers.getInstance().isSquid(p.getDisplayNameString()) && !p.getDisplayNameString().equals(Minecraft.getMinecraft().player.getDisplayNameString())) {
			ClientProxy.squidRender.doRender((AbstractClientPlayer) p, x, y, z, p.rotationYaw, partialTick);
			return true;
		}
		if (Minecraft.getMinecraft().player.getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid() && p.getDisplayNameString().equals(Minecraft.getMinecraft().player.getDisplayNameString())) {
			ClientProxy.squidRender.doRender((AbstractClientPlayer) p, x, y, z, p.rotationYaw, partialTick);
			return true;
		}
		return super.onRenderPlayerPre(p, partialTick, x, y, z, render);
	}
	
	@Override
	protected void onPlayerLoggedIn(EntityPlayer p) {
		if (p.world.playerEntities.size() > 1) {
			Iterator<EntityPlayer> playersList = p.world.playerEntities.iterator();
			HashMap<String, Boolean> squidList = Maps.newHashMap();
			while (playersList.hasNext()) {
				EntityPlayer player = playersList.next();
				if (player.getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid())
					squidList.put(player.getDisplayNameString(), true);
				else
					squidList.put(player.getDisplayNameString(), false);
			}
			MushPowers.network.sendTo(new PacketSquidPlayersList(squidList), (EntityPlayerMP) p);
		}
	}

	@Override
	protected void onClientDisconnectionFromServer() {
		MushPowers.getInstance().getSquidPlayers().clear();
	}

	private int decreasePlayerAirSupply(EntityPlayer p, int air) {
		int i = EnchantmentHelper.getRespirationModifier(p);
		return i > 0 && this.rand.nextInt(i + 1) > 0 ? air : air - 1;
	}
	
	private void setSquidBoundingBoxToPlayer(EntityPlayer player, float width, float height, float eyeHeight){
		if(width != player.width || height != player.height) {
            player.eyeHeight = eyeHeight;
            float f = player.width;
            player.width = width;
            player.height = height;
            
            double d0 = width / 2.0D;
            player.setEntityBoundingBox(new AxisAlignedBB(player.posX - d0, player.posY, player.posZ - d0, player.posX + d0, player.posY + player.height, player.posZ + d0));

            if (player.width < f)
                return;
            
            if(!player.world.isRemote && player.width > f) {
                boolean firstUpdate = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, 52);
                if(firstUpdate)
                    player.move(MoverType.SELF, (f - player.width) / 2, 0.0D, (f - player.width) / 2);
            }
        }
	}
}
