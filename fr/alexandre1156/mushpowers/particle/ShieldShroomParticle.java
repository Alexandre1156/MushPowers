package fr.alexandre1156.mushpowers.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ShieldShroomParticle extends Particle {

	float oSize;
	
	public ShieldShroomParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		// this.motionX = this.motionX * 0.009999999776482582D + xSpeedIn;
		// this.motionY = this.motionY * 0.009999999776482582D + ySpeedIn;
		// this.motionZ = this.motionZ * 0.009999999776482582D + zSpeedIn;
		// this.particleScale *= 0.75F;
		// this.particleMaxAge = 60 + this.rand.nextInt(12);
		switch(rand.nextInt(3)) {
		case 0:
			this.setRBGColorF(1.0f, 0.0f, 0.0f);
			break;
		case 1:
			this.setRBGColorF(0.0f, 0.0f, 1.0f);
			break;
		case 2:
			this.setRBGColorF(1.0f, 1.0f, 0.0f);
			break;
		}
		float f = 2.5F;
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += xSpeedIn;
		this.motionY += ySpeedIn;
		this.motionZ += zSpeedIn;
//		float f1 = 1.0F - (float) (Math.random() * 0.30000001192092896D);
//		this.particleRed = f1;
//		this.particleGreen = f1;
//		this.particleBlue = f1;
		this.particleScale *= 0.75F;
		this.particleScale *= 2.5F;
		this.oSize = this.particleScale;
		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.3D));
		this.particleMaxAge = (int) ((float) this.particleMaxAge * 2.5F);
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		this.particleScale = this.oSize * f;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;
		EntityPlayer entityplayer = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 2.0D, false);

		if (entityplayer != null) {
			AxisAlignedBB axisalignedbb = entityplayer.getEntityBoundingBox();

			if (this.posY > axisalignedbb.minY) {
				this.posY += (axisalignedbb.minY - this.posY) * 0.2D;
				this.motionY += (entityplayer.motionY - this.motionY) * 0.2D;
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	@SideOnly(Side.CLIENT)
	public static class Factory implements IParticleFactory {
		public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return new ShieldShroomParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		}
	}

	// @Override
	// public int getFXLayer() {
	// return 1;
	// }

}
