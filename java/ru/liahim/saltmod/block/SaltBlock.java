package ru.liahim.saltmod.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.SaltConfig;

public class SaltBlock extends Block {
	
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", SaltBlock.EnumType.class);
	protected boolean crystal = true;
	
	public SaltBlock(CreativeTabs tab) {
		super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SaltBlock.EnumType.DEFAULT));
		this.setTickRandomly(true);
		this.setCreativeTab(tab);
		this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 1);
	}
	
    @Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (meta == SaltBlock.EnumType.LINES_Y.getMetadata())
        {
            switch (facing.getAxis())
            {
                case Z:
                    return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.LINES_Z);
                case X:
                    return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.LINES_X);
                case Y:
                default:
                    return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.LINES_Y);
            }
        }
        
        else if (meta == SaltBlock.EnumType.BLOCKCHISELED.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.BLOCKCHISELED);}
        
        else if (meta == SaltBlock.EnumType.BRICK.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.BRICK);}
        
        else if (meta == SaltBlock.EnumType.BLOCKCRACKED.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.BLOCKCRACKED);}
        
        else if (meta == SaltBlock.EnumType.BRICKCRACKED.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.BRICKCRACKED);}
        
        else if (meta == SaltBlock.EnumType.BRICKCHISELED.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.BRICKCHISELED);}
        
        else if (meta == SaltBlock.EnumType.CHAPITER.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.CHAPITER);}
        
        else {return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.DEFAULT);}
    }
	
    @Override
	public int damageDropped(IBlockState state)
    {
    	SaltBlock.EnumType type = (SaltBlock.EnumType)state.getValue(VARIANT);
        return type != SaltBlock.EnumType.LINES_X && type != SaltBlock.EnumType.LINES_Z ? type.getMetadata() : SaltBlock.EnumType.LINES_Y.getMetadata();
    }
    
    @Override
	protected ItemStack createStackedBlock(IBlockState state)
    {
    	SaltBlock.EnumType type = (SaltBlock.EnumType)state.getValue(VARIANT);
        return type != SaltBlock.EnumType.LINES_X && type != SaltBlock.EnumType.LINES_Z ? super.createStackedBlock(state) : new ItemStack(Item.getItemFromBlock(this), 1, SaltBlock.EnumType.LINES_Y.getMetadata());
    }
	 
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, SaltBlock.EnumType.DEFAULT.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.BLOCKCHISELED.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.LINES_Y.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.BRICK.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.BLOCKCRACKED.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.BRICKCRACKED.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.BRICKCHISELED.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltBlock.EnumType.CHAPITER.getMetadata()));
    }
    
    @Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, SaltBlock.EnumType.byMetadata(meta));
    }

    @Override
	public int getMetaFromState(IBlockState state)
    {
        return ((SaltBlock.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    @Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
    
    @Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((EnumType)state.getValue(VARIANT))
                {
                    case LINES_X:
                        return state.withProperty(VARIANT, EnumType.LINES_Z);
                    case LINES_Z:
                        return state.withProperty(VARIANT, EnumType.LINES_X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        this.saltDamage(worldIn, entityIn);
        super.onEntityWalk(worldIn, pos, entityIn);
    }
    
    private void saltDamage(World worldIn, Entity entity)
    {
   		if (entity instanceof EntityLivingBase && EntityList.getEntityString(entity) != null &&
   	   	  ((EntityList.getEntityString(entity).toLowerCase().contains("slime") && !EntityList.getEntityString(entity).toLowerCase().contains("lava")) ||
   	   		EntityList.getEntityString(entity).toLowerCase().contains("witch")))
   	   		{entity.attackEntityFrom(DamageSource.cactus, 1.0F);}
    }
	
    @Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			//Crystal Growth
			
			BlockPos posUp = new BlockPos(pos.up());
	
			if ((world.getBlockState(posUp.east()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.east()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.west()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.west()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.north()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.north()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.south()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.south()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.add(1, 0, 1)).getMaterial() == Material.WATER) &&
				(world.getBlockState(posUp.add(1, 0, -1)).getMaterial() == Material.WATER) &&
				(world.getBlockState(posUp.add(-1, 0, 1)).getMaterial() == Material.WATER) &&
				(world.getBlockState(posUp.add(-1, 0, -1)).getMaterial() == Material.WATER) &&
				(world.getLightFromNeighbors(posUp) < 15))
			{
				if (rand.nextInt(SaltConfig.saltCrystalGrowSpeed) == 0 && crystal)
				{
					if(world.isAirBlock(posUp))
					{
						world.setBlockState(posUp, ModBlocks.saltCrystal.getDefaultState().withProperty(SaltCrystal.STAGE, SaltCrystal.EnumType.SMALL), 3);
					}
				
					else if (world.getBlockState(posUp).getBlock() == ModBlocks.saltCrystal &&
							 world.getBlockState(posUp).getBlock().getMetaFromState(world.getBlockState(posUp)) == 2)
					{
						world.setBlockState(posUp, ModBlocks.saltCrystal.getDefaultState().withProperty(SaltCrystal.STAGE, SaltCrystal.EnumType.MEDIUM), 3);
					}
				
					else if (world.getBlockState(posUp).getBlock() == ModBlocks.saltCrystal &&
							 world.getBlockState(posUp).getBlock().getMetaFromState(world.getBlockState(posUp)) == 1)
					{
						world.setBlockState(posUp, ModBlocks.saltCrystal.getDefaultState(), 3);
					}
				}
			
				crystal = true;
			}
	
			//Melting Ice
		
			for (int x2 = x - 1; x2 < x + 2; x2++) {
			for (int y2 = y - 1; y2 < y + 2; y2++) {
			for (int z2 = z - 1; z2 < z + 2; z2++) {
				BlockPos pos2 = new BlockPos(x2, y2, z2);
				Block block = world.getBlockState(pos2).getBlock();
				if ((block == Blocks.ICE || block == Blocks.SNOW || (block == Blocks.SNOW_LAYER && y2 != y-1)) &&
					((x2-1 == x && (world.getBlockState(pos2.west()).getBlock() == this || world.getBlockState(pos2.west()).getMaterial() == Material.WATER)) ||
					(x2+1 == x && (world.getBlockState(pos2.east()).getBlock() == this || world.getBlockState(pos2.east()).getMaterial() == Material.WATER)) ||
					(y2-1 == y && (world.getBlockState(pos2.down()).getBlock() == this || world.getBlockState(pos2.down()).getMaterial() == Material.WATER)) ||
					(y2+1 == y && (world.getBlockState(pos2.up()).getBlock() == this || world.getBlockState(pos2.up()).getMaterial() == Material.WATER)) ||
					(z2-1 == z && (world.getBlockState(pos2.north()).getBlock() == this || world.getBlockState(pos2.north()).getMaterial() == Material.WATER)) ||
					(z2+1 == z && (world.getBlockState(pos2.south()).getBlock() == this || world.getBlockState(pos2.south()).getMaterial() == Material.WATER))))
						
				{crystal = false; world.scheduleUpdate(pos, this, 5);
						
					if (rand.nextInt(20) == 0)
					{
						if(block == Blocks.ICE || block == Blocks.SNOW)
						{world.setBlockState(pos2, Blocks.WATER.getDefaultState(), 3); crystal = true;}
						if(block == Blocks.SNOW_LAYER && y2 != y-1)
						{world.setBlockToAir(pos2); crystal = true;}
					}
				}
			}
			}
			}	
		}
	}
	
    @Override
	public MapColor getMapColor(IBlockState state)
    {
        return MapColor.QUARTZ;
    }
    
    public enum EnumType implements IStringSerializable
	{
		DEFAULT(0, "default"),
		BLOCKCHISELED(1, "block_chiseled"),
		LINES_Y(2, "lines_y"),
		LINES_X(3, "lines_x"),
		LINES_Z(4, "lines_z"),
		BRICK(5, "brick"),		
		BLOCKCRACKED(6, "block_cracked"),		
		BRICKCRACKED(7, "brick_cracked"),
		BRICKCHISELED(8, "brick_chiseled"),
		CHAPITER(9, "chapiter");

        private static final SaltBlock.EnumType[] META_LOOKUP = new SaltBlock.EnumType[values().length];
	    private final int meta;
	    private final String name;

	    private EnumType(int meta, String name)
	    {
	        this.meta = meta;
	        this.name = name;
	    }
	    
        public int getMetadata()
        {
            return this.meta;
        }

		@Override
		public String getName()
		{
			return name;
		}
		
		public static SaltBlock.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
			return META_LOOKUP[meta];
		}
		
        static
        {
            for (SaltBlock.EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
	}
}