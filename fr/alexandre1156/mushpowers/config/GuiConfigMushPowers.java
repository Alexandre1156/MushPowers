package fr.alexandre1156.mushpowers.config;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.alexandre1156.mushpowers.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiConfigMushPowers extends GuiConfig {

	private String note;
	
	public GuiConfigMushPowers(GuiScreen parentScreen) {
		super(parentScreen,
	            //new ConfigElement(MushPowers.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				getConfigElements(),
	            Reference.MOD_ID,
	            false,
	            false,
	            "MushPowers Config");
		this.titleLine2 = GuiConfig.getAbridgedConfigPath(MushConfig.config.toString());
	}
	
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		//list.addAll(new ConfigElement(MushConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		list.add(new DummyCategoryElement("General", "mush.config.general", MushGeneral.class));
        list.add(new DummyCategoryElement("Mushpowers Cooldown", "mush.config.cooldown", MushCooldown.class));
        list.add(new DummyCategoryElement("Desactived Shrooms", "mush.config.desactived", MushDesactived.class));
        return list;
	}
	
	public static class MushCooldown extends CategoryEntry {

		public MushCooldown(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}
		
		
		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen, new ConfigElement(MushConfig.config.getCategory("MushCooldown")).getChildElements(), this.owningScreen.modID, Configuration.CATEGORY_GENERAL, false, false, 
					"MushPowers Config", ((this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2) + " > " + this.name));
		}
	}
	
	public static class MushDesactived extends CategoryEntry {

		public MushDesactived(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}
		
		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen, new ConfigElement(MushConfig.config.getCategory("MushDesactived")).getChildElements(), this.owningScreen.modID, Configuration.CATEGORY_GENERAL, false, false, 
					"MushPowers Config", ((this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2) + " > " + this.name));
		}
		
	}
	
	public static class MushGeneral extends CategoryEntry {

		public MushGeneral(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}
		
		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen, new ConfigElement(MushConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), this.owningScreen.modID, Configuration.CATEGORY_GENERAL, false, false, 
					"MushPowers Config", ((this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2) + " > " + this.name));
		}
	}

	@Override
	public void initGui() {
		this.note = (new TextComponentTranslation("mush.config.note", new Object[0])).getUnformattedText();
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		RenderHelper.disableStandardItemLighting();
		if(this.fontRenderer.getStringWidth(this.note) > this.width) {
			String text2[] = this.seperate(this.note);
			this.drawCenteredString(fontRenderer, text2[0], this.width / 2, 38 + (this.buttonList.size()-1) * 20, Color.white.getRGB());
			this.drawCenteredString(fontRenderer, text2[1], this.width / 2, 38 + (this.buttonList.size()-1) * 20 + 10, Color.white.getRGB());
		} else
			this.drawCenteredString(fontRenderer, this.note, this.width / 2, 38 + (this.buttonList.size()-1) * 20, Color.white.getRGB());
		RenderHelper.enableStandardItemLighting();
	}
	
	private String[] seperate(String text){
		String[] text2 = new String[2];
		text2[0] = "";
		text2[1] = "";
		int widthLimit = 0;
		String currentWord = "";
		boolean switchToNextLine = false;
		int count = 0;
		for(char c : text.toCharArray()){
			widthLimit += this.fontRenderer.getCharWidth(c);
			count++;
			if(widthLimit > this.width) {
				switchToNextLine = true;
				widthLimit = 0;
			}
			if(c == ' ') {
				if(switchToNextLine)
					text2[1] += currentWord+" ";
				else
					text2[0] += currentWord+" ";
				currentWord = "";
			} else if(count == text.length())
				text2[1] += currentWord+c;
			else
				currentWord += c;
			//System.out.println(currentWord+" - "+switchToNextLine+" - "+count);
		}
		return text2;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
	}
	
	@Override
	public void onGuiClosed() {
		MushConfig.syncConfig();
		super.onGuiClosed();
	}
	
}
