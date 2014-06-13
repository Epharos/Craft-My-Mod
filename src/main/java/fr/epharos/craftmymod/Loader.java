package fr.epharos.craftmymod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.epharos.craftmymod.customs.BiomeCustom;
import fr.epharos.craftmymod.customs.BlockCustom;
import fr.epharos.craftmymod.customs.BlockOreCustom;
import fr.epharos.craftmymod.customs.BlockSlabCustom;
import fr.epharos.craftmymod.customs.BlockStairsCustom;
import fr.epharos.craftmymod.customs.ItemAxeCustom;
import fr.epharos.craftmymod.customs.ItemCustom;
import fr.epharos.craftmymod.customs.ItemFoodCustom;
import fr.epharos.craftmymod.customs.ItemHoeCustom;
import fr.epharos.craftmymod.customs.ItemPickaxeCustom;
import fr.epharos.craftmymod.customs.ItemShovelCustom;
import fr.epharos.craftmymod.customs.ItemSwordCustom;
import fr.epharos.craftmymod.customs.ToolMaterialCustom;
import fr.epharos.craftmymod.utils.CustomInformations;
import fr.epharos.craftmymod.utils.Util;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

public class Loader 
{
	public static final String craftMyModDirection = CraftMyMod.event.getSide().isClient() ? Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/craftmymod/" : "craftmymod/";
	
	public static List<ToolMaterialCustom> tools = new ArrayList<ToolMaterialCustom>();
	public static List<BlockOreCustom> ores = new ArrayList<BlockOreCustom>();
	
	public static List<CustomInformations> informations = new ArrayList<CustomInformations>();
	
	public void loadBlocks()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Block") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				BlockCustom block = new BlockCustom(this.setStringAttribute("material", values));
				
				block.setBlockName(this.setStringAttribute("name", values));
				block.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				block.setHardness(this.setFloatAttribute("hardness", values, 1));
				block.setResistance(this.setFloatAttribute("resistance", values, 1));
				block.setLightLevel(this.setFloatAttribute("lightvalue", values, 0));
				block.setQuantityDropped(this.setIntegerAttribute("quantitydropped", values, 1));
				block.setIDDropped(this.setIntegerAttribute("iddropped", values, -1));
				
				if(!this.setStringAttribute("texture", values).contains(","))
				{
					block.setBlockTextureName(this.setStringAttribute("texture", values));
				}
				else if(this.setStringAttribute("texture", values).contains(",") && this.setStringAttribute("texture", values).startsWith("{") && this.setStringAttribute("texture", values).endsWith("}"))
				{
					String s = this.setStringAttribute("texture", values);
					s = deleteChar(s, "\\{");
					s = deleteChar(s, "\\}");
					
					String[] textures = getStringsForMultiValues(s, ",");
					
					block.setTextures(textures);
				}
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerBlock(block, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.type = "Block";
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemFromBlock(block), "iconString", "field_111218_cA");
				informations.add(info);
			}
		}
	}
	
	public void loadItems()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Item") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemCustom item = new ItemCustom();
				
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 64));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "iconString", "field_111218_cA");
				info.type = "Item";
				informations.add(info);
			}
		}
	}
	
	public void loadStairs()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Stair") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				BlockStairsCustom block = new BlockStairsCustom(Block.getBlockById(this.setIntegerAttribute("modelID", values)), this.setIntegerAttribute("modelmetadata", values));
				
				block.setBlockName(this.setStringAttribute("name", values));
				block.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				block.setHardness(this.setFloatAttribute("hardness", values, 1));
				block.setResistance(this.setFloatAttribute("resistance", values, 1));
				block.setLightLevel(this.setFloatAttribute("lightvalue", values, 0));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerBlock(block, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemFromBlock(block), "iconString", "field_111218_cA");
				info.type = "Stair";
				informations.add(info);
			}
		}
	}
	
	public void loadSlabs()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Slab") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				BlockSlabCustom blockSimple = new BlockSlabCustom(false, this.setStringAttribute("material", values));
				BlockSlabCustom blockDouble = new BlockSlabCustom(true, this.setStringAttribute("material", values));
				
				blockSimple.setBlockName(this.setStringAttribute("name", values) + "_simple");
				blockSimple.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				blockSimple.setHardness(this.setFloatAttribute("hardness", values, 1));
				blockSimple.setResistance(this.setFloatAttribute("resistance", values, 1));
				blockSimple.setLightLevel(this.setFloatAttribute("lightvalue", values, 0));
				
				if(!this.setStringAttribute("texture", values).contains(","))
				{
					blockSimple.setBlockTextureName(this.setStringAttribute("texture", values));
				}
				else if(this.setStringAttribute("texture", values).contains(",") && this.setStringAttribute("texture", values).startsWith("{") && this.setStringAttribute("texture", values).endsWith("}"))
				{
					String s = this.setStringAttribute("texture", values);
					s = deleteChar(s, "\\{");
					s = deleteChar(s, "\\}");
					
					String[] textures = getStringsForMultiValues(s, ",");
					
					blockSimple.setTextures(textures);
				}
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerBlock(blockSimple, this.setStringAttribute("name", values) + "_simple");
				
				blockDouble.setBlockName(this.setStringAttribute("name", values) + "_double");
				blockDouble.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				blockDouble.setHardness(this.setFloatAttribute("hardness", values, 1));
				blockDouble.setResistance(this.setFloatAttribute("resistance", values, 1));
				blockDouble.setLightLevel(this.setFloatAttribute("lightvalue", values, 0));
				
				if(!this.setStringAttribute("texture", values).contains(","))
				{
					blockDouble.setBlockTextureName(this.setStringAttribute("texture", values));
				}
				else if(this.setStringAttribute("texture", values).contains(",") && this.setStringAttribute("texture", values).startsWith("{") && this.setStringAttribute("texture", values).endsWith("}"))
				{
					String s = this.setStringAttribute("texture", values);
					s = deleteChar(s, "\\{");
					s = deleteChar(s, "\\}");
					
					String[] textures = getStringsForMultiValues(s, ",");
					
					blockDouble.setTextures(textures);
				}
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerBlock(blockDouble, this.setStringAttribute("name", values) + "_double");
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemFromBlock(blockSimple), "iconString", "field_111218_cA");
				info.type = "Slab";
				informations.add(info);
			}
		}
	}
	
	public void loadFood()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Food") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemFoodCustom item = new ItemFoodCustom(this.setIntegerAttribute("restore", values), this.setFloatAttribute("saturation", values), this.setBooleanAttribute("wolf", values));
				item.setEat(this.setBooleanAttribute("eat", values));
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 64));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("texture", values)), "iconString", "field_111218_cA");
				info.type = "Food";
				informations.add(info);
			}
		}
	}
	
	public void loadSwords()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Sword") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemSwordCustom item = new ItemSwordCustom(this.getToolMaterialCustom(this.setStringAttribute("tool", values)).tool);
				
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "combat")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 1));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("texture", values)), "iconString", "field_111218_cA");
				info.type = "Sword";
				informations.add(info);
			}
		}
	}
	
	public void loadPickaxes()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Pickaxe") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemPickaxeCustom item = new ItemPickaxeCustom(this.getToolMaterialCustom(this.setStringAttribute("tool", values)).tool);
				
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "tools")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 1));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("texture", values)), "iconString", "field_111218_cA");
				info.type = "Pickaxe";
				informations.add(info);
			}
		}
	}
	
	public void loadAxes()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Axe") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemAxeCustom item = new ItemAxeCustom(this.getToolMaterialCustom(this.setStringAttribute("tool", values)).tool);
				
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "tools")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 1));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("texture", values)), "iconString", "field_111218_cA");
				info.type = "Axes";
				informations.add(info);
			}
		}
	}
	
	public void loadHoes()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Hoe") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemHoeCustom item = new ItemHoeCustom(this.getToolMaterialCustom(this.setStringAttribute("tool", values)).tool);
				
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "tools")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 1));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("texture", values)), "iconString", "field_111218_cA");
				info.type = "Hoe";
				informations.add(info);
			}
		}
	}
	
	public void loadShovels()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Shovel") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ItemShovelCustom item = new ItemShovelCustom(this.getToolMaterialCustom(this.setStringAttribute("tool", values)).tool);
				
				item.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "tools")));
				item.setUnlocalizedName(this.setStringAttribute("name", values));
				item.setMaxStackSize(this.setIntegerAttribute("maxstacksize", values, 1));
				item.setTextureName(this.setStringAttribute("texture", values));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerItem(item, this.setStringAttribute("name", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("texture", values)), "iconString", "field_111218_cA");
				info.type = "Shovel";
				informations.add(info);
			}
		}
	}
	
	public void loadTileEntities()
	{
		
	}
	
	public void loadToolEnums()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Tool") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				ToolMaterialCustom tool = new ToolMaterialCustom(this.setStringAttribute("name", values), EnumHelper.addToolMaterial(this.setStringAttribute("name", values), this.setIntegerAttribute("harvestlevel", values), this.setIntegerAttribute("maxuses", values) - 1, this.setIntegerAttribute("efficiency", values), this.setIntegerAttribute("damage", values), this.setIntegerAttribute("enchantibility", values)));
				tools.add(tool);
			}
		}
	}
	
	public void loadEntities()
	{
		
	}
	
	public void loadGUIs()
	{
		
	}
	
	public void loadSpecialRenders()
	{
		
	}
	
	public void loadBiomes()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Biome") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				BiomeCustom biome = (BiomeCustom) new BiomeCustom(this.setIntegerAttribute("biomeID", values)).setBiomeName(this.setStringAttribute("biomename", values)).setTemperatureRainfall(this.setFloatAttribute("mintemperature", values, 0), this.setFloatAttribute("maxtemperature", values, 1));
				
				if(this.setBooleanAttribute("snow", values, false))
				{
					biome.setEnableSnow();
				}
				
				if(!this.setBooleanAttribute("rain", values, true))
				{
					biome.setDisableRain();
				}
				
				biome.setTopAndFillerBlocks(Block.getBlockById(this.setIntegerAttribute("topblockID", values)), Block.getBlockById(this.setIntegerAttribute("fillerblockID", values)));
				
				if(this.setBooleanAttribute("enable", values, true))
					BiomeManager.addSpawnBiome(biome);
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemFromBlock(Block.getBlockById(this.setIntegerAttribute("topblockID", values))), "iconString", "field_111218_cA");
				info.type = "Biome";
				informations.add(info);
			}
		}
	}
	
	public void loadCraftingTableRecipes()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Craft") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				boolean shapelessRecipe = this.setBooleanAttribute("shapelessrecipe", values, false);
				
				if(shapelessRecipe)
				{
					if(this.setBooleanAttribute("enable", values, true))
						GameRegistry.addRecipe(new ItemStack(Item.getItemById(this.setIntegerAttribute("outputID", values)), this.setIntegerAttribute("outputquantity", values)), new Object[]
						{
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item1", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item2", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item3", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item4", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item5", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item6", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item7", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item8", values))),
							new ItemStack(Item.getItemById(this.setIntegerAttribute("item9", values)))
						});
					
					CustomInformations info = new CustomInformations();
					info.active = this.setBooleanAttribute("enable", values, true);
					info.fileName = files[i].getName();
					info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
					info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("outputID", values)), "iconString", "field_111218_cA");
					info.type = "Shapeless Recipe";
					informations.add(info);
				}
				else
				{
					String s = this.setStringAttribute("firstline", values);
					s = deleteChar(s, "\\{");
					s = deleteChar(s, "\\}");
					
					String s1 = this.setStringAttribute("secondline", values);
					s1 = deleteChar(s1, "\\{");
					s1 = deleteChar(s1, "\\}");
					
					String s2 = this.setStringAttribute("thirdline", values);
					s2 = deleteChar(s2, "\\{");
					s2 = deleteChar(s2, "\\}");
						
					String[] firstline = getStringsForMultiValues(s, ",");
					String[] secondline = getStringsForMultiValues(s1, ",");
					String[] thirdline = getStringsForMultiValues(s2, ",");
					
					String firstLine = "";
					String secondLine = "";
					String thirdLine = "";
					
					for(int a = 0 ; a < 3 ; a++)
					{
						if(Integer.valueOf(firstline[a]) != 0)
						{
							firstLine = firstLine.concat(String.valueOf(a + 1));
						}
						else
						{
							firstLine = firstLine.concat(" ");
						}
						
						if(Integer.valueOf(secondline[a]) != 0)
						{
							secondLine = secondLine.concat(String.valueOf(a + 4));
						}
						else
						{
							secondLine = secondLine.concat(" ");
						}
						
						if(Integer.valueOf(thirdline[a]) != 0)
						{
							thirdLine = thirdLine.concat(String.valueOf(a + 7));
						}
						else
						{
							thirdLine = thirdLine.concat(" ");
						}
					}
					
					if(this.setBooleanAttribute("enable", values, true))
						GameRegistry.addRecipe(new ItemStack(Item.getItemById(this.setIntegerAttribute("outputID", values)), this.setIntegerAttribute("outputquantity", values)), new Object[]
						{
							firstLine, secondLine, thirdLine,
							'1', Item.getItemById(Integer.valueOf(firstline[0])),
							'2', Item.getItemById(Integer.valueOf(firstline[1])),
							'3', Item.getItemById(Integer.valueOf(firstline[2])),
							'4', Item.getItemById(Integer.valueOf(secondline[0])),
							'5', Item.getItemById(Integer.valueOf(secondline[1])),
							'6', Item.getItemById(Integer.valueOf(secondline[2])),
							'7', Item.getItemById(Integer.valueOf(thirdline[0])),
							'8', Item.getItemById(Integer.valueOf(thirdline[1])),
							'9', Item.getItemById(Integer.valueOf(thirdline[2]))
						});
					
					CustomInformations info = new CustomInformations();
					info.active = this.setBooleanAttribute("enable", values, true);
					info.fileName = files[i].getName();
					info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
					info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("outputID", values)), "iconString", "field_111218_cA");
					info.type = "Crafting Recipe";
					informations.add(info);
				}
			}
		}
	}
	
	public void loadFurnaceRecipes()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Furnace") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.addSmelting(new ItemStack(Item.getItemById(this.setIntegerAttribute("inputID", values)), 0, this.setIntegerAttribute("inputmetadata", values)), new ItemStack(Item.getItemById(this.setIntegerAttribute("outputID", values)), 0, this.setIntegerAttribute("outputmetadata", values)), this.setFloatAttribute("xp", values));
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				info.texture = ObfuscationReflectionHelper.getPrivateValue(Item.class, Item.getItemById(this.setIntegerAttribute("outputID", values)), "iconString", "field_111218_cA");
				info.type = "Furnace Recipe";
				informations.add(info);
			}
		}
	}
	
	public void loadDimensions()
	{
		
	}
	
	public void loadStructures()
	{
		
	}
	
	public void loadActions()
	{
		
	}
	
	public void loadOres()
	{
		File file = new File(craftMyModDirection);
		file.mkdirs();
		File[] files = file.listFiles();
		
		for(int i = 0 ; i < files.length ; i++)
		{
			if(files[i].getName().startsWith("Ore") && files[i].getName().endsWith(".cmm"))
			{
				String[] values = getFileValues(files[i]);
				
				for(int a = 0 ; a < values.length ; a++)
				{
					if(values[a] != null)
						values[a] = deleteSpace(values[a]);
				}
				
				BlockOreCustom block = new BlockOreCustom(this.setStringAttribute("material", values));
				
				block.setBlockName(this.setStringAttribute("name", values));
				block.setCreativeTab(Util.getCreativeTabByString(this.setStringAttribute("tab", values, "misc")));
				block.setHardness(this.setFloatAttribute("hardness", values, 1));
				block.setResistance(this.setFloatAttribute("resistance", values, 1));
				block.setLightLevel(this.setFloatAttribute("lightvalue", values, 0));
				block.setQuantityDropped(this.setIntegerAttribute("quantitydropped", values, 1));
				block.setIDDropped(this.setIntegerAttribute("iddropped", values, -1));
				
				if(!this.setStringAttribute("texture", values).contains(","))
				{
					block.setBlockTextureName(this.setStringAttribute("texture", values));
				}
				else if(this.setStringAttribute("texture", values).contains(",") && this.setStringAttribute("texture", values).startsWith("{") && this.setStringAttribute("texture", values).endsWith("}"))
				{
					String s = this.setStringAttribute("texture", values);
					s = deleteChar(s, "\\{");
					s = deleteChar(s, "\\}");
					
					String[] textures = getStringsForMultiValues(s, ",");
					
					block.setTextures(textures);
				}
				
				block.setInformations(this.setIntegerAttribute("probability", values), 
						this.setIntegerAttribute("maxheight", values, 64),
						this.setIntegerAttribute("maxore", values), 
						this.setStringAttribute("dimension", values, "surface").equals("surface") ? 0 : this.setStringAttribute("dimension", values).equals("nether") ? -1 : 1, 
						this.setIntegerAttribute("replaceID", values, 1));
				
				if(this.setBooleanAttribute("enable", values, true))
					GameRegistry.registerBlock(block, this.setStringAttribute("name", values));
				
				ores.add(block);
				
				CustomInformations info = new CustomInformations();
				info.active = this.setBooleanAttribute("enable", values, true);
				info.fileName = files[i].getName();
				info.name = files[i].getName().substring(0, files[i].getName().length() - 4);
				
				if(!this.setStringAttribute("texture", values).contains(","))
				{
					info.texture = this.setStringAttribute("texture", values);
				}
				else if(this.setStringAttribute("texture", values).contains(",") && this.setStringAttribute("texture", values).startsWith("{") && this.setStringAttribute("texture", values).endsWith("}"))
				{
					String s = this.setStringAttribute("texture", values);
					s = deleteChar(s, "\\{");
					s = deleteChar(s, "\\}");
					
					String[] textures = getStringsForMultiValues(s, ",");
					
					info.texture = textures[3];
				}
				
				info.type = "Ore";
				informations.add(info);
			}
		}
	}
	
	private ToolMaterialCustom getToolMaterialCustom(String name)
	{
		if(this.tools.isEmpty())
		{
			return null;
		}
		
		for(int i = 0 ; i < this.tools.size() ; i++)
		{
			if(this.tools.get(i).name.equals(name))
			{
				return this.tools.get(i);
			}
		}
		
		return null;
	}
	
	public static String[] getFileValues(File file)
	{
		BufferedReader reader = null;
		String[] lines = new String[128];
		
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String currentLine;
			
			int counter = 0;
		
			while((currentLine = reader.readLine()) != null)
			{
				lines[counter] = currentLine;
				++counter;
			}
			
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		
		return lines;
	}
	
	public static String[] getStringsForMultiValues(String string, String regex)
	{
		return string.split(regex);
	}
	
	public static String deleteSpace(String string)
	{
		return string.replaceAll(" ", "");
	}
	
	public static String deleteChar(String string, String character)
	{
		return string.replaceAll(character, "");
	}
	
	public String setStringAttribute(String attribute, String[] values)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return s.split(":")[1];
			}
		}
		
		return null;
	}
	
	public Integer setIntegerAttribute(String attribute, String[] values)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Integer.valueOf(s.split(":")[1]);
			}
		}
		
		return null;
	}
	
	public Float setFloatAttribute(String attribute, String[] values)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Float.valueOf(s.split(":")[1]);
			}
		}
		
		return null;
	}
	
	public Boolean setBooleanAttribute(String attribute, String[] values)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Boolean.valueOf(s.split(":")[1]);
			}
		}
		
		return null;
	}
	
	public Double setDoubleAttribute(String attribute, String[] values)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Double.valueOf(s.split(":")[1]);
			}
		}
		
		return null;
	}
	
	public String setStringAttribute(String attribute, String[] values, String defaut)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return s.split(":")[1];
			}
		}
		
		return defaut;
	}
	
	public Integer setIntegerAttribute(String attribute, String[] values, int defaut)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Integer.valueOf(s.split(":")[1]);
			}
		}
		
		return defaut;
	}
	
	public Float setFloatAttribute(String attribute, String[] values, float defaut)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Float.valueOf(s.split(":")[1]);
			}
		}
		
		return defaut;
	}
	
	public Boolean setBooleanAttribute(String attribute, String[] values, boolean defaut)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Boolean.valueOf(s.split(":")[1]);
			}
		}
		
		return defaut;
	}
	
	public Double setDoubleAttribute(String attribute, String[] values, double defaut)
	{
		for(String s : values)
		{
			if(s.startsWith(attribute))
			{
				return Double.valueOf(s.split(":")[1]);
			}
		}
		
		return defaut;
	}
}
