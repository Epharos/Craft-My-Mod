package fr.epharos.craftmymod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLStateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.epharos.craftmymod.customs.WorldGenerationOreCustom;
import fr.epharos.craftmymod.proxy.CommonProxy;
import fr.epharos.craftmymod.utils.GuiOptions;

@Mod(modid = "craftmymod", name = "Craft My Mod", version = "1.1")
public class CraftMyMod 
{
	@Instance("craftmymod")
	public static CraftMyMod instance;
	
	@SidedProxy(clientSide = "fr.epharos.craftmymod.proxy.ClientProxy", serverSide = "fr.epharos.craftmymod.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static FMLStateEvent event;
	
	public static final boolean debug = true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		this.event = event;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRender();
		
		Loader loader = new Loader();
		loader.loadBlocks();
		loader.loadItems();
		loader.loadFood();
		loader.loadToolEnums();
		loader.loadShovels();
		loader.loadAxes();
		loader.loadPickaxes();
		loader.loadHoes();
		loader.loadSwords();
		loader.loadCraftingTableRecipes();
		loader.loadFurnaceRecipes();
		loader.loadOres();
		GameRegistry.registerWorldGenerator(new WorldGenerationOreCustom(), 1);
		loader.loadBiomes();
		loader.loadStairs();
		loader.loadSlabs();
		
		FMLCommonHandler.instance().bus().register(this);
		
		this.event = event;
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		this.event = event;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTickClient(TickEvent.ClientTickEvent event)
	{
		LogManager.getLogger().debug("TICK !");
		
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen currentScreen = mc.currentScreen;
		GuiOptions customMenu = new GuiOptions(currentScreen, mc.gameSettings);

		if(currentScreen instanceof net.minecraft.client.gui.GuiOptions && !currentScreen.equals(customMenu))
		{
			customMenu = new GuiOptions((GuiScreen) ObfuscationReflectionHelper.getPrivateValue(net.minecraft.client.gui.GuiOptions.class, ((net.minecraft.client.gui.GuiOptions) currentScreen), CraftMyMod.debug ? "field_146441_g" : "field_146441_g"), mc.gameSettings);
			mc.displayGuiScreen(customMenu);
		}
	}
}
