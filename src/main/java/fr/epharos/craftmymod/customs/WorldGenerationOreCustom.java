package fr.epharos.craftmymod.customs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import fr.epharos.craftmymod.Loader;

public class WorldGenerationOreCustom implements IWorldGenerator 
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.dimensionId)
		{
			case -1:
				this.generateNether(world, chunkX * 16, chunkZ * 16, random);
			case 0:
				this.generateSurface(world, chunkX * 16, chunkZ * 16, random);
			case 1:
				this.generateEnd(world, chunkX * 16, chunkZ * 16, random);
		}
	}

	private void generateEnd(World world, int x, int z, Random random) 
	{
		for(int i = 0 ; i < Loader.ores.size() ; i++)
		{
			if(Loader.ores.get(i).dimensionID == 1)
			{
				for(int a = 0 ; a < Loader.ores.get(i).probability ; a++)
				{
					new WorldGenMinable(Loader.ores.get(i), 0, Loader.ores.get(i).maxOre, Block.getBlockById(Loader.ores.get(i).replaceID)).generate(world, random, x + random.nextInt(16), random.nextInt(Loader.ores.get(i).maxHeight), z + random.nextInt(16));
				}
			}
		}
	}

	private void generateSurface(World world, int x, int z, Random random) 
	{
		for(int i = 0 ; i < Loader.ores.size() ; i++)
		{
			if(Loader.ores.get(i).dimensionID == 0)
			{
				for(int a = 0 ; a < Loader.ores.get(i).probability ; a++)
				{
					new WorldGenMinable(Loader.ores.get(i), 0, Loader.ores.get(i).maxOre, Block.getBlockById(Loader.ores.get(i).replaceID)).generate(world, random, x + random.nextInt(16), random.nextInt(Loader.ores.get(i).maxHeight), z + random.nextInt(16));
				}
			}
		}
	}

	private void generateNether(World world, int x, int z, Random random) 
	{
		for(int i = 0 ; i < Loader.ores.size() ; i++)
		{
			if(Loader.ores.get(i).dimensionID == -1)
			{
				for(int a = 0 ; a < Loader.ores.get(i).probability ; a++)
				{
					new WorldGenMinable(Loader.ores.get(i), 0, Loader.ores.get(i).maxOre, Block.getBlockById(Loader.ores.get(i).replaceID)).generate(world, random, x + random.nextInt(16), random.nextInt(Loader.ores.get(i).maxHeight), z + random.nextInt(16));
				}
			}
		}
	}
}
