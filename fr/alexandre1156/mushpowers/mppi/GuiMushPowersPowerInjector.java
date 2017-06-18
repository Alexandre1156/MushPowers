package fr.alexandre1156.mushpowers.mppi;

import java.awt.Color;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiMushPowersPowerInjector extends GuiContainer {

	private TileEntityMushPowersPowerInjector te;
	private IInventory playerInv;
	private static final ResourceLocation MPPI_GUI_TEXTURE = new ResourceLocation("mush",
			"textures/container/mppi.png");

	public GuiMushPowersPowerInjector(IInventory playerInv, TileEntityMushPowersPowerInjector tileEntity) {
		super(new ContainerMushPowersPowerInjector((InventoryPlayer) playerInv, tileEntity));
		this.te = tileEntity;
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(MPPI_GUI_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format("container.mppi", new Object[0]);
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6,
				4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2,
				4210752);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if (this.te.isConsumingUp()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(MPPI_GUI_TEXTURE);
			int width = this.te.getCooldownUp() / 2 / 4;
			this.drawTexturedModalRect(79, 17, 176, 14, width, 17);
		}
		if(this.te.isConsumingDown()){
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(MPPI_GUI_TEXTURE);
			int width = this.te.getCooldownDown() / 2 / 24;
			this.drawTexturedModalRect(79, 54, 176, 14, width, 17);
		}
		if(mouseX >= (this.guiLeft+80) && mouseX < (this.guiLeft+102) && mouseY > (this.guiTop+50) && mouseY < (this.guiTop+70))
			this.drawHoveringText(Lists.newArrayList(this.te.getCooldownDown()*100/1200+"%"), 69, 69);
		if(mouseX >= (this.guiLeft+80) && mouseX < (this.guiLeft+102) && mouseY > (this.guiTop+15) && mouseY < (this.guiTop+35))
			this.drawHoveringText(Lists.newArrayList(this.te.getCooldownUp()*100/200+"%"), 69, 33);
		if(this.te.isDesactivedItem()){
			RenderHelper.disableStandardItemLighting();
			this.drawString(fontRendererObj, "DESACTIVED SHROOM", 20, 40, Color.red.getRGB());
			RenderHelper.enableStandardItemLighting();
		}
	}

}
