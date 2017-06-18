package fr.alexandre1156.mushpowers.mppi;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import fr.alexandre1156.mushpowers.MushPowers;
import fr.alexandre1156.mushpowers.bushs.BushMush;
import fr.alexandre1156.mushpowers.config.MushConfig;
import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMushPowersPowerInjector extends TileEntity implements ICapabilityProvider, ITickable {

	private ItemStackHandler handler;
	private int cooldownUp;
	private boolean isConsumingUp;
	private boolean consumeFinishedUp;
	private int cooldownDown;
	private boolean isConsumingDown;
	private boolean consumeFinishedDown;
	private ItemStack[] smeltItems;
	private ItemStack[] smeltResult;
	private boolean desactivedItem;

	public TileEntityMushPowersPowerInjector() {
		this.cooldownUp = 0;
		this.cooldownDown = 0;
		this.handler = new ItemStackHandler(5);
		this.smeltItems = new ItemStack[CommonProxy.getBushs().size()];
		this.smeltResult = new ItemStack[CommonProxy.getBushs().size()];
		for(int i = 0; i < smeltItems.length; i++){
			BushMush bush = CommonProxy.getBushs().get(i);
			this.smeltItems[i] = new ItemStack(Item.getItemFromBlock(bush));
			this.smeltResult[i] = new ItemStack(bush.getMushPowerCorrespondence());
		}
//		this.smeltItems[0] = new ItemStack(Items.DYE, 1, 0);
//		this.smeltItems[1] = new ItemStack(Items.DYE, 1, 1);
//		this.smeltItems[2] = new ItemStack(Items.DYE, 1, 11);
//		this.smeltItems[3] = new ItemStack(Items.DYE, 1, 15);
	
//		this.smeltResult[0] = new ItemStack(CommonProxy.itemSquidshroom);
//		this.smeltResult[1] = new ItemStack(CommonProxy.itemRegenshroom);
//		this.smeltResult[2] = new ItemStack(CommonProxy.itemThorshroom);
//		this.smeltResult[3] = new ItemStack(CommonProxy.itemGhostshroom);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.cooldownUp = compound.getInteger("CooldownUp");
		this.isConsumingUp = compound.getBoolean("ConsumingUp");
		this.cooldownDown = compound.getInteger("CooldownDown");
		this.isConsumingDown= compound.getBoolean("ConsumingDown");
		this.handler.deserializeNBT(compound.getCompoundTag("ItemStackHandler"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("CooldownUp", this.cooldownUp);
		compound.setInteger("CooldownDown", this.cooldownDown);
		compound.setTag("ItemStackHandler", this.handler.serializeNBT());
		compound.setBoolean("ConsumingDown", this.isConsumingDown);
		compound.setBoolean("ConsumingUp", this.isConsumingUp);
		return super.writeToNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	public int getCooldownUp() {
		return cooldownUp;
	}
	
	public int getCooldownDown() {
		return cooldownDown;
	}
	
	public boolean isDesactivedItem() {
		return desactivedItem;
	}
	
	public boolean canBeSmelted(ItemStack is){
		for(int i = 0; i < this.smeltItems.length; i++){
			if(this.smeltItems[i].getItem() == is.getItem() && this.smeltItems[i].getMetadata() == is.getMetadata())
				return true;
		}
		return false;
	}
	
	public ItemStack smeltResult(ItemStack is){
		ItemStack falseIS = Items.AIR.getDefaultInstance();
		for(int i = 0; i < this.smeltResult.length; i++){
			if(this.smeltItems[i].getItem().equals(is.getItem()))
				return this.smeltResult[i];
		}
		return falseIS;
	}
	
	public ArrayList<ItemStack> getItems(){
		ArrayList<ItemStack> items = Lists.newArrayList();
		for(int i = 0; i < this.handler.getSlots(); i++){
			if(!this.handler.getStackInSlot(i).isEmpty())
				items.add(this.handler.getStackInSlot(i));
		}
		return items;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) this.handler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	public void setConsumingUp(boolean isConsuming) {
		this.isConsumingUp = isConsuming;
	}
	
	public void setConsumingDown(boolean isConsumingDown) {
		this.isConsumingDown = isConsumingDown;
	}

	public boolean isConsumingUp() {
		return isConsumingUp;
	}
	
	public boolean isConsumingDown() {
		return isConsumingDown;
	}

	public void setConsumingFinishedUp(boolean finish) {
		this.consumeFinishedUp = finish;
	}
	
	public void setConsumeFinishedDown(boolean consumeFinishedDown) {
		this.consumeFinishedDown = consumeFinishedDown;
	}

	public boolean isConsumeFinishedUp() {
		return this.consumeFinishedUp;
	}
	
	public boolean isConsumeFinishedDown() {
		return consumeFinishedDown;
	}

	public void setCooldownUp(int cooldown) {
		this.cooldownUp = cooldown;
	}
	
	public void setCooldownDown(int cooldownDown) {
		this.cooldownDown = cooldownDown;
	}

	public void smeltItemUp() {
		if (this.consumeFinishedUp) {
			this.consumeFinishedUp = false;
			if(!(this.handler.getStackInSlot(0).getCount() >= 2))
				this.isConsumingUp = false;
    		this.cooldownUp = 0;
			ItemStack itemstack = this.handler.getStackInSlot(0);
			ItemStack itemstack1 = this.handler.getStackInSlot(2);

			if (itemstack1.isEmpty()) 
				this.handler.setStackInSlot(2, new ItemStack(MushPowers.proxy.itemMushElexir));
			else
				itemstack1.grow(1);

			if(itemstack1.getCount() == 64)
				this.isConsumingUp = false;
			itemstack.shrink(1);
			this.markDirty();
		}
	}
	
	public void smeltItemDown() {
		if (this.consumeFinishedDown /*&& !this.world.isRemote*/){
			this.consumeFinishedDown = false;
			if(!(this.handler.getStackInSlot(1).getCount() >= 2 && this.handler.getStackInSlot(4).getCount() >= 2))
				this.isConsumingDown = false;
    		this.cooldownDown = 0;
			ItemStack inputSpecialItem = this.handler.getStackInSlot(1);
			ItemStack output = this.handler.getStackInSlot(3);
			ItemStack inputMushElixir = this.handler.getStackInSlot(4);
			ItemStack result = this.smeltResult(inputSpecialItem);
			if (output.isEmpty()) 
				this.handler.setStackInSlot(3, result.copy());
			else
				output.grow(1);

			if(output.getCount() == 64)
				this.isConsumingDown = false;
			inputSpecialItem.shrink(1);
			inputMushElixir.shrink(1);
			this.markDirty();
		}
	}

	@Override
	public void update() {
		//System.out.println(!this.handler.getStackInSlot(1).isEmpty()+" - "+(!MushConfig.isMushPowersDesactived(this.handler.getStackInSlot(1).getItem()))+" - "+(!this.handler.getStackInSlot(4).isEmpty())+" - "+(this.handler.getStackInSlot(3).getCount() < 64)+" - "+(!this.isConsumingDown)+" - ");
		if(!this.handler.getStackInSlot(1).isEmpty() && !MushConfig.isMushPowersDesactived(this.handler.getStackInSlot(1).getItem()) && !this.handler.getStackInSlot(4).isEmpty() && this.handler.getStackInSlot(3).getCount() < 64 && !this.isConsumingDown && (this.handler.getStackInSlot(3).isEmpty() || this.handler.getStackInSlot(3).isItemEqual(this.smeltResult(this.handler.getStackInSlot(1))))) {
			this.isConsumingDown = true;
		}else if(this.isConsumingDown && (this.handler.getStackInSlot(1).isEmpty() || this.handler.getStackInSlot(4).isEmpty() || this.handler.getStackInSlot(3).getCount() >= 64)) { 
			this.isConsumingDown = false;
			this.cooldownDown = 0;
		}
		//System.out.println(MushConfig.isMushPowersDesactived(this.handler.getStackInSlot(1).getItem())+" - "+this.handler.getStackInSlot(1).getItem().getUnlocalizedName());
		if(!this.handler.getStackInSlot(1).isEmpty() && MushConfig.isMushPowersDesactived(this.handler.getStackInSlot(1).getItem()))
			this.desactivedItem = true;
		else
			this.desactivedItem = false;
		if (this.isConsumingUp) {
			if (this.getCooldownUp() >= 200) {
				this.consumeFinishedUp = true;
				this.smeltItemUp();
			} else
				cooldownUp++;
		}
		if (this.isConsumingDown) {
			if (this.getCooldownDown() >= 1200) {
				this.consumeFinishedDown = true;
				this.smeltItemDown();
			} else
				cooldownDown++;
			}
	}

}
