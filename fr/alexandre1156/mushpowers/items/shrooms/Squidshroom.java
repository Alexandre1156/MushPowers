package fr.alexandre1156.mushpowers.items.shrooms;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.alexandre1156.mushpowers.MushUtils;
import fr.alexandre1156.mushpowers.capabilities.CapabilityUtils;
import fr.alexandre1156.mushpowers.capabilities.player.IPlayerMush;
import fr.alexandre1156.mushpowers.capabilities.player.PlayerMushProvider;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.particle.ShroomParticle;
import fr.alexandre1156.mushpowers.proxy.CommonProxy.Mushs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Squidshroom extends ItemMushPowers {

	public static final String IS_SQUID = "squid";
	public static final String SQUID_COOLDOWN = "squidcooldown";
	public static final String AIR = "air";
	
	public Squidshroom() {
		super(1, 0.0f, "squidshroom");
		this.registerData("squid", Types.BOOLEAN);
		this.registerData("squidcooldown", Types.SHORT);
		this.registerData("air", Types.INTEGER);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
		if(!worldIn.isRemote && !mush.getBoolean(this.IS_SQUID)){
			mush.setBoolean(this.IS_SQUID, true);
			mush.setInteger(this.AIR, 300);
			mush.setShort(this.SQUID_COOLDOWN, MushConfig.getCooldown(Mushs.SQUID));
			CapabilityUtils.resetOtherMainMushPower(player, Mushs.SQUID);
			CapabilityUtils.sendSquidPacket(player, true);
			super.onFoodEaten(stack, worldIn, player);
		}
//		for(int i = 0; i < worldIn.countEntities(EntitySquid.class); i++){
//			AxisAlignedBB box = worldIn.getEntities(EntitySquid.class, EntitySelectors.IS_ALIVE).get(i).getEntityBoundingBox();
//			EntitySquid ent = worldIn.getEntities(EntitySquid.class, EntitySelectors.IS_ALIVE).get(i);
//			System.out.println(box.maxX+" "+box.maxY+" "+box.maxZ+" "+box.minX+" "+box.minY+" "+box.minZ+" "+ent.width+" "+ent.height);
//		}
//		if(player instanceof EntityPlayerMP)
//			((EntityPlayerMP) player).connection.sendPacket(new SPacketPlayerAbilities(player.capabilities));
	}
	
//	@Override
//	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
//		IPlayerMush mush = player.getCapability(PlayerMushProvider.MUSH_CAP, null);
//		mush.setSquid(false);
//		PlayerMushProvider.syncCapabilities(player);
//		return super.onDroppedByPlayer(item, player);
//	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ChatFormatting.WHITE+"The bubble bar will appear while on ground and dissapear while in water.\n You could also move faster in water, however players may fish you on online servers.\n To fish a player, you must be in a 3x3x3 radius of the end of the fishing rod.\n If you are fished, you will die, your items will be vanished.\n Your nametag will be invisible in water and you will disguise as a squid.");
		tooltip.add(ChatFormatting.GREEN+""+ChatFormatting.BOLD+"Lasts in "+MushUtils.correctCooldownMessage(MushConfig.getCooldown(Mushs.SQUID)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public TextFormatting getColorName() {
		return TextFormatting.BLUE;
		
	}

	@Override
	public boolean onUsedOnLivingEntity(World world, EntityLivingBase entLiv, EntityPlayer player) {return false;}

	@Override
	public ShroomParticle getParticleOnLivingEntity() {
		return null;
		
	}

	@Override
	public boolean isEntityLivingCompatible() {
		return false;
	}

	@Override
	protected Mushs getMushType() {
		return Mushs.SQUID;
	}
	
}
