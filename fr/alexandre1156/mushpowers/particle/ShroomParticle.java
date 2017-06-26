package fr.alexandre1156.mushpowers.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.client.particle.ParticleHeart;

public enum ShroomParticle {
	CHICKEN(new ChickenShroomParticle.Factory()), FLY(new ParticleCloud.Factory()), HEARTH(new ParticleHeart.Factory()), SHIELD(new ShieldShroomParticle.Factory());
	
	private IParticleFactory factory;
	
	private ShroomParticle(IParticleFactory factory){
		this.factory = factory;
	}
	
	public IParticleFactory getFactory() {
		return factory;
	}
}
