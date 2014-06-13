package fr.epharos.craftmymod.customs;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockSlabCustom extends BlockSlab 
{
	private int idDropped, quantityDropped = 1;
	private String textures[] = new String[6];
	private IIcon texturesInGame[] = new IIcon[6];
	
	public BlockSlabCustom(boolean isDouble, String material) 
	{
		super(isDouble, BlockCustom.getMaterialByString(material));
		
		if(!isDouble)
		{
			this.setLightOpacity(0);
		}
	}

	public String func_150002_b(int var1) 
	{
		return this.getUnlocalizedName();
	}
	
	public Block setTextures(String ... textures)
	{
		this.textures = textures;
		return this;
	}
	
	public int quantityDropped(Random p_149745_1_)
    {
        return field_150004_a ? 2 : 1;
    }

	//TODO
    public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_) 
    {
        return Item.getItemFromBlock(this);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
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
    	    
    	return this.texturesInGame[3];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
    	for (int i = 0; i < this.textures.length; i++) 
    	{
    		this.texturesInGame[i] = register.registerIcon(this.textures[i]);
    	}
      
    	this.blockIcon = register.registerIcon(this.textures[3]);
    }
}
