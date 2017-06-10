package fr.alexandre1156.mushpowers.render;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SquidRender extends RenderPlayer {

	private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation("textures/entity/squid.png");
	private float lastTentacleAngle;
	private float tentacleAngle;
	private float rotation;
	private boolean renverse;

	public SquidRender(RenderManager renderManager) {
		super(renderManager);
		this.field_77045_g = new ModelSquid();
	}

	@Override
	protected ResourceLocation func_110775_a(AbstractClientPlayer entity) {
		return SQUID_TEXTURES;
	}

	@Override
	public void func_76986_a(AbstractClientPlayer entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		if (!entity.func_175144_cb() || this.field_76990_c.field_78734_h == entity) {
			double d0 = y;

			if (entity.func_70093_af() && !(entity instanceof EntityPlayerSP)) {
				d0 = y - 0.125D;
			}

			GlStateManager.func_187408_a(GlStateManager.Profile.PLAYER_SKIN);
			this.doRender2(entity, x, d0, z, entityYaw, partialTicks);
			GlStateManager.func_187440_b(GlStateManager.Profile.PLAYER_SKIN);
		}

		this.lastTentacleAngle = this.tentacleAngle;
//		if (entity.isInWater()) {
//			if (entity.rotationPitch < (float) Math.PI && false) {
//				float f = entity.rotationPitch / (float) Math.PI;
//				this.tentacleAngle = MathHelper.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;
//			} else {
//				this.tentacleAngle = 1.0F;
//			}
//		} else {
//			this.tentacleAngle = MathHelper.abs(MathHelper.sin(entity.rotationPitch)) * (float) Math.PI * 0.25F;
//		}
		if(entity.func_70090_H()){
			if(this.renverse)
				this.tentacleAngle += 0.01f;
			else
				this.tentacleAngle -= 0.01f;
		} else {
			if(this.renverse)
				this.tentacleAngle += 0.005f;
			else
				this.tentacleAngle -= 0.005f;
		}
		if(this.tentacleAngle >= 1.0f)
			this.renverse = false;
		else if(this.tentacleAngle <= 0.0f)
			this.renverse = true;
//		GlStateManager.scale(0.5f, 0.5f, 0.5f);
//		this.bindTexture(SQUID_TEXTURES);
//		this.mainModel.render(entity, 1f, 1f, entity.getAge(), entity.cameraYaw, entity.cameraPitch, 0.1f);
		// float f = this.interpolateRotation(entity.prevRenderYawOffset,
		// entity.renderYawOffset, partialTicks);
		// float f8 = this.handleRotationFloat(entity, partialTicks);
		// this.applyRotations(entity, f8, f, partialTicks);
		// GlStateManager.enableAlpha();
		// this.mainModel.setRotationAngles(0, 0, f8, 0, 0, 0, entity);
	}

	@Override
	protected void func_77043_a(AbstractClientPlayer entityLiving, float p_77043_2_, float p_77043_3_,
			float partialTicks) {
		float f = entityLiving.field_70127_C
				+ (entityLiving.field_70125_A - entityLiving.field_70127_C) * partialTicks;
		float f1 = entityLiving.field_70126_B
				+ (entityLiving.field_70177_z - entityLiving.field_70126_B) * partialTicks;
		GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F);
		GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
		GlStateManager.func_179114_b(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.func_179114_b(f1, 0.0F, 1.0F, 0.0F);
		GlStateManager.func_179109_b(0.0F, -1.2F, 0.0F);
	}

	@Override
	protected float func_77044_a(AbstractClientPlayer livingBase, float partialTicks) {
		return this.lastTentacleAngle + (this.tentacleAngle - this.lastTentacleAngle) * partialTicks;
	}

	public void doRender2(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.func_179094_E();
		GlStateManager.func_179129_p();
		this.field_77045_g.field_78095_p = this.func_77040_d(entity, partialTicks);
		boolean shouldSit = entity.func_184218_aH()
				&& (entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit());
		this.field_77045_g.field_78093_q = shouldSit;
		this.field_77045_g.field_78091_s = entity.func_70631_g_();

		try {
			float f = this.func_77034_a(entity.field_70760_ar, entity.field_70761_aq, partialTicks);
			float f1 = this.func_77034_a(entity.field_70758_at, entity.field_70759_as, partialTicks);
			float f2 = f1 - f;

			if (shouldSit && entity.func_184187_bx() instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity.func_184187_bx();
				f = this.func_77034_a(entitylivingbase.field_70760_ar, entitylivingbase.field_70761_aq,
						partialTicks);
				f2 = f1 - f;
				float f3 = MathHelper.func_76142_g(f2);

				if (f3 < -85.0F) {
					f3 = -85.0F;
				}

				if (f3 >= 85.0F) {
					f3 = 85.0F;
				}

				f = f1 - f3;

				if (f3 * f3 > 2500.0F) {
					f += f3 * 0.2F;
				}
			}

			float f7 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
			this.func_77039_a(entity, x, y, z);
			float f8 = this.func_77044_a(entity, partialTicks);
			this.func_77043_a(entity, f8, f, partialTicks);
			float f4 = this.func_188322_c(entity, partialTicks);
			float f5 = 0.0F;
			float f6 = 0.0F;

			if (!entity.func_184218_aH()) {
				f5 = entity.field_184618_aE + (entity.field_70721_aZ - entity.field_184618_aE) * partialTicks;
				f6 = entity.field_184619_aG - entity.field_70721_aZ * (1.0F - partialTicks);

				if (entity.func_70631_g_()) {
					f6 *= 3.0F;
				}

				if (f5 > 1.0F) {
					f5 = 1.0F;
				}
			}

			GlStateManager.func_179141_d();
			this.field_77045_g.func_78086_a(entity, f6, f5, partialTicks);
			this.field_77045_g.func_78087_a(f6, f5, f8, f2, f7, f4, entity);

			if (this.field_188301_f) {
				boolean flag1 = this.func_177088_c(entity);
				GlStateManager.func_179142_g();
				GlStateManager.func_187431_e(this.func_188298_c(entity));

				if (!this.field_188323_j) {
					this.func_77036_a(entity, f6, f5, f8, f2, f7, f4);
				}

				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).func_175149_v()) {
					this.func_177093_a(entity, f6, f5, partialTicks, f8, f2, f7, f4);
				}

				GlStateManager.func_187417_n();
				GlStateManager.func_179119_h();

				if (flag1) {
					this.func_180565_e();
				}
			} else {
				boolean flag = this.func_177090_c(entity, partialTicks);
				this.func_77036_a(entity, f6, f5, f8, f2, f7, f4);

				if (flag) {
					this.func_177091_f();
				}

				GlStateManager.func_179132_a(true);

//				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
//					this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
//				}
			}

			GlStateManager.func_179101_C();
		} catch (Exception exception) {
			LogManager.getLogger().error((String) "Couldn\'t render entity", (Throwable) exception);
		}

		GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
		GlStateManager.func_179098_w();
		GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
		GlStateManager.func_179089_o();
		GlStateManager.func_179121_F();
		if (!this.field_188301_f)
        {
            this.func_177067_a(entity, x, y, z);
        }
	}

}
