package fr.alexandre1156.mushpowers.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Maps;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.capabilities.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.PlayerMush.MainMushPowers;
import fr.alexandre1156.mushpowers.capabilities.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
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
	
	private Random rand = new Random();
	private ResourceLocation BUBBLE_BAR_TEXTURE = new ResourceLocation("textures/gui/icons.png");
	private boolean isSquid;
	
	@Override
	protected void onPlayerCloned(EntityPlayer p, EntityPlayer pOriginal, boolean death) {
		IPlayerMush mush = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
		IPlayerMush mush2 = pOriginal.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if (death) {
			MushPowers.getInstance().getSquidPlayers().replace(p.func_70005_c_(), false);
			PlayerMushProvider.sendSquidPacket(p, false);
		} else {
			mush.setSquid(mush2.isSquid());
			mush.setSquidAir(mush2.getSquidAir());
			mush.setCooldown(MainMushPowers.SQUID, mush2.getCooldown(MainMushPowers.SQUID));
		}
	}

	@Override
	public boolean onRenderGameOverlayPre(ElementType type) {
		if (type == ElementType.AIR && isSquid)
			return true;
		return false;
	}
	
	@Override
	protected void onPlayerIteractItemRightClickItem(EntityPlayer p, World world, ItemStack item) {
		if (p.field_71104_cf != null && item.func_77973_b() == Items.field_151112_aM && !world.field_72995_K) {
			EntityFishHook hook = p.field_71104_cf;
			List<Entity> ents = world.func_72839_b(hook, hook.func_174813_aQ().func_72314_b(2D, 2D, 2D));
			Iterator<Entity> iter = ents.iterator();
			while (iter.hasNext()) {
				Entity ent = iter.next();
				if (ent instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) ent;
					if (!player.func_70005_c_().equals(p.func_70005_c_()) && player.getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid()) {
						player.field_71071_by.func_174888_l();
						player.func_70606_j(0f);
						p.func_145747_a(new TextComponentTranslation("squid.hooked.message", p.func_70005_c_()));
					}
				}
			}
		}
	}

	@Override
	protected void onRenderGameOverlayPost(ScaledResolution resolution, ElementType type) {
		if (type == ElementType.ALL) {
			EntityPlayer player = (EntityPlayer) Minecraft.func_71410_x().func_175606_aa();
			IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if (mush.isSquid() && !player.func_184812_l_()) {
				this.isSquid = true;
				Minecraft.func_71410_x().field_71460_t.func_78478_c();
				int right_height = 39;
				Minecraft.func_71410_x().field_71424_I.func_76320_a("air");
				GlStateManager.func_179147_l();
				int left = resolution.func_78326_a() / 2 + 91;
				int top = resolution.func_78328_b() - right_height;

				if (!player.func_70055_a(Material.field_151586_h)) {
					Minecraft.func_71410_x().func_110434_K().func_110577_a(BUBBLE_BAR_TEXTURE);
					int air = mush.getSquidAir();
					int full = MathHelper.func_76143_f((double) (air - 2) * 10.0D / 300.0D);
					int partial = MathHelper.func_76143_f((double) air * 10.0D / 300.0D) - full;

					for (int i = 0; i < full + partial; ++i) {
						//System.out.println((left - i * 8 - 9)+" - "+left+" - "+i);
						Minecraft.func_71410_x().field_71456_v.func_73729_b(left - i * 8 - 9, top - 10, (i < full ? 16 : 25), 18, 9, 9);
						//this.drawTexturedModalRect(left - i * 8 - 9, top - 10, (i < full ? 16 : 25), 18, 9, 9);
					}
					right_height += 10;
				}

				GlStateManager.func_179084_k();
				Minecraft.func_71410_x().field_71424_I.func_76319_b();
			} else
				this.isSquid = false;
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
			if(side.isClient() && this.isSquid && p.func_70005_c_().equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) 
				this.setSquidBoundingBoxToPlayer(p, 0.8f, 0.8f, 0.4f);
			else if(side.isClient() && MushPowers.getInstance().isSquid(p.func_70005_c_()))
				this.setSquidBoundingBoxToPlayer(p, 0.8f, 0.8f, 0.4f);
			else if(side.isServer() && p.getCapability(PlayerMushProvider.MUSH_CAP, null).isSquid())
				this.setSquidBoundingBoxToPlayer(p, 0.8f, 0.8f, 0.4f);
			else if(p.eyeHeight != p.getDefaultEyeHeight())
				p.eyeHeight = p.getDefaultEyeHeight();
		}
		
		if(phase == TickEvent.Phase.END && isSquid && side.isClient() && p.func_70055_a(Material.field_151586_h)){
			p.field_70159_w *= 1.1D;
			p.field_70181_x *= 1.1D;
			p.field_70179_y *= 1.1D;
		}
	}
	
	@Override
	protected void onLivingUpdate(Entity ent, EntityLivingBase entLiv) {
		if (entLiv instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) entLiv;
			IPlayerMush cap = p.getCapability(PlayerMushProvider.MUSH_CAP, null);
			if (cap.isSquid() && !p.field_70170_p.field_72995_K) {
				if(cap.isSquid()){
					if(cap.getCooldown(MainMushPowers.SQUID) % 1200 == 0)
						p.func_146105_b(new TextComponentTranslation("squid.time.left.min", (cap.getCooldown(MainMushPowers.SQUID)/1200)).func_150255_a(new Style().func_150238_a(TextFormatting.AQUA)), true);
					else if(cap.getCooldown(MainMushPowers.SQUID) == 600 || cap.getCooldown(MainMushPowers.SQUID) == 200 || cap.getCooldown(MainMushPowers.SQUID) == 180 || cap.getCooldown(MainMushPowers.SQUID) == 160 || cap.getCooldown(MainMushPowers.SQUID) == 140 || cap.getCooldown(MainMushPowers.SQUID) == 120 || cap.getCooldown(MainMushPowers.SQUID) == 100 || cap.getCooldown(MainMushPowers.SQUID) == 80 || cap.getCooldown(MainMushPowers.SQUID) == 60 || cap.getCooldown(MainMushPowers.SQUID) == 40 || cap.getCooldown(MainMushPowers.SQUID) == 20)
						p.func_146105_b(new TextComponentTranslation("squid.time.left.sec", (cap.getCooldown(MainMushPowers.SQUID)/20)).func_150255_a(new Style().func_150238_a(TextFormatting.DARK_AQUA)), true);
					else if(cap.getCooldown(MainMushPowers.SQUID) <= 0){
						cap.setCooldown(MainMushPowers.SQUID, (short) MushConfig.cooldownSquid);
						cap.setSquid(false);
						cap.setSquidAir(300);
						MushPowers.getInstance().setSquidPlayer(Minecraft.func_71410_x().field_71439_g.func_70005_c_(), false);
						PlayerMushProvider.sendSquidPacket(p, false);
						PlayerMushProvider.syncCapabilities(p);
					}
					cap.setCooldown(MainMushPowers.SQUID, (short) (cap.getCooldown(MainMushPowers.SQUID) - 1));
					PlayerMushProvider.syncCapabilities(p);
				}
				p.func_70050_g(300);
				if (!p.func_70055_a(Material.field_151586_h) && !p.func_70648_aU()
						&& !p.func_70644_a(MobEffects.field_76427_o) && !p.field_71075_bZ.field_75102_a) {
					cap.setSquidAir(this.decreasePlayerAirSupply(p, cap.getSquidAir()));
					if (cap.getSquidAir() == -20) {
						cap.setSquidAir(0);

						for (int i = 0; i < 8; ++i) {
							float f2 = this.rand.nextFloat() - this.rand.nextFloat();
							float f = this.rand.nextFloat() - this.rand.nextFloat();
							float f1 = this.rand.nextFloat() - this.rand.nextFloat();
							p.func_130014_f_().func_175688_a(EnumParticleTypes.WATER_BUBBLE, p.field_70165_t + (double) f2,
									p.field_70163_u + (double) f, p.field_70161_v + (double) f1, p.field_70159_w, p.field_70181_x, p.field_70179_y,
									new int[0]);
						}

						p.func_70097_a(DamageSource.field_76369_e, 2.0F);
					}
				} else
					cap.setSquidAir(300);
			}
		}
	}

	@Override
	protected boolean onRenderPlayerPre(EntityPlayer p, float partialTick, double x, double y, double z, RenderPlayer render) {
		if (MushPowers.getInstance().isSquid(p.getDisplayNameString()) && !p.getDisplayNameString().equals(Minecraft.func_71410_x().field_71439_g.getDisplayNameString())) {
			ClientProxy.squidRender.func_76986_a((AbstractClientPlayer) p, x, y, z, p.field_70177_z, partialTick);
			return true;
		}
		if (isSquid && p.getDisplayNameString().equals(Minecraft.func_71410_x().field_71439_g.getDisplayNameString())) {
			ClientProxy.squidRender.func_76986_a((AbstractClientPlayer) p, x, y, z, p.field_70177_z, partialTick);
			return true;
		}
		return super.onRenderPlayerPre(p, partialTick, x, y, z, render);
	}
	
	@Override
	protected void onPlayerLoggedIn(EntityPlayer p) {
		if (p.field_70170_p.field_73010_i.size() > 1) {
			Iterator<EntityPlayer> playersList = p.field_70170_p.field_73010_i.iterator();
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
		int i = EnchantmentHelper.func_185292_c(p);
		return i > 0 && this.rand.nextInt(i + 1) > 0 ? air : air - 1;
	}
	
	private void setSquidBoundingBoxToPlayer(EntityPlayer player, float width, float height, float eyeHeight){
//		if (0.8f != p.width || 0.8f != p.height) {
//            p.width = 0.8f;
//            p.height = 0.8f;
//            p.eyeHeight = 0.4f;
//            AxisAlignedBB axisalignedbb = p.getEntityBoundingBox();
//            p.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, 
//            		axisalignedbb.minX + (double)p.width, axisalignedbb.minY + (double)p.height, axisalignedbb.minZ + (double)p.width));
//       }
		if(width != player.field_70130_N || height != player.field_70131_O) {
            player.eyeHeight = eyeHeight;
            float f = player.field_70130_N;
            player.field_70130_N = width;
            player.field_70131_O = height;
            
            double d0 = width / 2.0D;
            player.func_174826_a(new AxisAlignedBB(player.field_70165_t - d0, player.field_70163_u, player.field_70161_v - d0, player.field_70165_t + d0, player.field_70163_u + player.field_70131_O, player.field_70161_v + d0));

            if (player.field_70130_N < f)
                return;
            
            if(!player.field_70170_p.field_72995_K && player.field_70130_N > f) {
                boolean firstUpdate = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, "firstUpdate", "field_70148_d");
                if(firstUpdate)
                    player.func_70091_d(MoverType.SELF, (f - player.field_70130_N) / 2, 0.0D, (f - player.field_70130_N) / 2);
            }
        }
	}
}
