package fr.epharos.craftmymod.customs;

import java.util.Random;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockCustom extends Block
{
	private int idDropped, quantityDropped = 1;
	private String textures[] = new String[6];
	private IIcon texturesInGame[] = new IIcon[6];
	private boolean multiTextured = false;
	
	public BlockCustom(String material) 
	{
		super(getMaterialByString(material));
	}
	
	public static Material getMaterialByString(String s)
	{
		Material m = null;
	    
	    if (s.equals("rock")) 
	    {
	    	m = Material.rock;
	    } 
	    else if (s.equals("ground")) 
	    {
	    	m = Material.ground;
	    } 
	    else if (s.equals("grass")) 
	    {
	    	m = Material.grass;
	    } 
	    else if (s.equals("wood")) 
	    {
	    	m = Material.wood;
	    } 
	    else if (s.equals("iron")) 
	    {
	    	m = Material.iron;
	    } 
	    else if (s.equals("anvil")) 
	    {
	    	m = Material.anvil;
	    } 
	    else if (s.equals("water")) 
	    {
	    	m = Material.water;
	    } 
	    else if (s.equals("lava")) 
	    {
	    	m = Material.lava;
	    } 
	    else if (s.equals("leaves")) 
	    {
	    	m = Material.leaves;
	    } 
	    else if (s.equals("plants")) 
	    {
	    	m = Material.plants;
	    } 
	    else if (s.equals("vine")) 
	    {
	    	m = Material.vine;
	    } 
	    else if (s.equals("sponge")) 
	    {
	    	m = Material.sponge;
	    } 
	    else if (s.equals("cloth")) 
	    {
	    	m = Material.cloth;
	    }  
	    else if (s.equals("fire")) 
	    {
	    	m = Material.fire;
		} 
	    else if (s.equals("sand")) 
	    {
	    	m = Material.sand;
	    } 
	    else if (s.equals("circuits")) 
	    {
	    	m = Material.circuits;
	    } 
	    else if (s.equals("carpet")) 
	    {
	    	m = Material.carpet;
	    } 
	    else if (s.equals("glass")) 
	    {
	    	m = Material.glass;
	    } 
	    else if (s.equals("redstoneLight")) 
	    {
	    	m = Material.redstoneLight;
	    } 
	    else if (s.equals("tnt")) 
	    {
	    	m = Material.tnt;
	    }
	    else if (s.equals("coral")) 
	    {
	    	m = Material.coral;
	    }
	    else if (s.equals("ice")) 
	    {
	    	m = Material.ice;
	    }
	    else if (s.equals("packedIce")) 
	    {
	    	m = Material.packedIce;
	    }
	    else if (s.equals("snow")) 
	    {
	    	m = Material.snow;
	    }
	    else if (s.equals("craftedSnow")) 
	    {
	    	m = Material.craftedSnow;
	    }
	    else if (s.equals("cactus")) 
	    {
	    	m = Material.cactus;
	    }
	    else if (s.equals("clay")) 
	    {
	    	m = Material.clay;
	    }
	    else if (s.equals("gourd")) 
	    {
	    	m = Material.gourd;
	    }
	    else if (s.equals("dragonEgg")) 
	    {
	    	m = Material.dragonEgg;
	    }
	    else if (s.equals("portal")) 
	    {
	    	m = Material.portal;
	    }
	    else if (s.equals("cake")) 
	    {
	    	m = Material.cake;
	    }
	    else if (s.equals("web")) 
	    {
	    	m = Material.web;
	    }
	    else 
	    {
	    	m = null;
	    }
	    
	    return m;
	}
	
	public Block setTextures(String ... textures)
	{
		this.textures = textures;
		this.multiTextured = true;
		return this;
	}

	public Block setQuantityDropped(int quantity)
	{
		this.quantityDropped = quantity;
		return this;
	}
	
	public Block setIDDropped(int id)
	{
		this.idDropped = id;
		return this;
	}
	
	public int quantityDropped(Random p_149745_1_)
    {
        return quantityDropped;
    }

    public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_) 
    {
        return this.idDropped != -1 ? Item.getItemById(this.idDropped) : Item.getItemFromBlock(this);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
    	if(this.multiTextured)
    	{
	    	if (side == 0) 
	    	{
	    		return this.texturesInGame[5];
	    	}
	    	
	    	if (side == 1) 
	    	{
	    		return this.texturesInGame[4];
	    	}
	    	
	    	if (side == 2) 
	    	{
	    	    return this.texturesInGame[0];
	    	}
	    	    
	    	if (side == 3) 
	    	{
	    		return this.texturesInGame[1];
	    	}
	    	    
	    	if (side == 4) 
	    	{
	    	    return this.texturesInGame[2];
	    	}
	    	    
	    	if (side == 5) 
	    	{
	    	    return this.texturesInGame[3];
	    	}
    	}
    	    
    	return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
    	if(this.multiTextured)
    	{
	    	for (int i = 0; i < this.textures.length; i++) 
	    	{
	    		this.texturesInGame[i] = register.registerIcon(this.textures[i]);
	    	}
	      
	    	this.blockIcon = register.registerIcon(this.textures[3]);
    	}
    }
}
