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
		this.mainModel = new ModelSquid();
	}

	@Override
	protected ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
		return SQUID_TEXTURES;
	}

	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
			double d0 = y;

			if (entity.isSneaking() && !(entity instanceof EntityPlayerSP)) {
				d0 = y - 0.125D;
			}

			GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
			this.doRender2(entity, x, d0, z, entityYaw, partialTicks);
			GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
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
		if(entity.isInWater()){
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
	protected void applyRotations(AbstractClientPlayer entityLiving, float p_77043_2_, float p_77043_3_,
			float partialTicks) {
		float f = entityLiving.prevRotationPitch
				+ (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * partialTicks;
		float f1 = entityLiving.prevRotationYaw
				+ (entityLiving.rotationYaw - entityLiving.prevRotationYaw) * partialTicks;
		GlStateManager.translate(0.0F, 0.5F, 0.0F);
		GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.2F, 0.0F);
	}

	@Override
	protected float handleRotationFloat(AbstractClientPlayer livingBase, float partialTicks) {
		return this.lastTentacleAngle + (this.tentacleAngle - this.lastTentacleAngle) * partialTicks;
	}

	public void doRender2(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
		boolean shouldSit = entity.isRiding()
				&& (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
		this.mainModel.isRiding = shouldSit;
		this.mainModel.isChild = entity.isChild();

		try {
			float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
			float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
			float f2 = f1 - f;

			if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
				f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset,
						partialTicks);
				f2 = f1 - f;
				float f3 = MathHelper.wrapDegrees(f2);

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

			float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
			this.renderLivingAt(entity, x, y, z);
			float f8 = this.handleRotationFloat(entity, partialTicks);
			this.applyRotations(entity, f8, f, partialTicks);
			float f4 = this.prepareScale(entity, partialTicks);
			float f5 = 0.0F;
			float f6 = 0.0F;

			if (!entity.isRiding()) {
				f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
				f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

				if (entity.isChild()) {
					f6 *= 3.0F;
				}

				if (f5 > 1.0F) {
					f5 = 1.0F;
				}
			}

			GlStateManager.enableAlpha();
			this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
			this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

			if (this.renderOutlines) {
				boolean flag1 = this.setScoreTeamColor(entity);
				GlStateManager.enableColorMaterial();
				GlStateManager.enableOutlineMode(this.getTeamColor(entity));

				if (!this.renderMarker) {
					this.renderModel(entity, f6, f5, f8, f2, f7, f4);
				}

				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
					this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
				}

				GlStateManager.disableOutlineMode();
				GlStateManager.disableColorMaterial();

				if (flag1) {
					this.unsetScoreTeamColor();
				}
			} else {
				boolean flag = this.setDoRenderBrightness(entity, partialTicks);
				this.renderModel(entity, f6, f5, f8, f2, f7, f4);

				if (flag) {
					this.unsetBrightness();
				}

				GlStateManager.depthMask(true);

//				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
//					this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
//				}
			}

			GlStateManager.disableRescaleNormal();
		} catch (Exception exception) {
			LogManager.getLogger().error((String) "Couldn\'t render entity", (Throwable) exception);
		}

		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
		if (!this.renderOutlines)
        {
            this.renderName(entity, x, y, z);
        }
	}

}
