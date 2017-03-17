package ru.liahim.saltmod.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.init.AchievSalt;

public class SaltDirt extends Block {
	
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", SaltDirt.EnumType.class);

	public SaltDirt(CreativeTabs tab) {
		super(Material.GROUND);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SaltDirt.EnumType.DEFAULT));
		this.setTickRandomly(true);
		this.setSoundType(SoundType.GROUND);
		this.setCreativeTab(tab);
		this.setHardness(0.5F);
		this.setResistance(1F);
		this.setHarvestLevel("shovel", 0);
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		if (meta == SaltDirt.EnumType.LAKE.getMetadata())
        {return this.getDefaultState().withProperty(VARIANT, SaltDirt.EnumType.LAKE);}
		else {return this.getDefaultState().withProperty(VARIANT, SaltDirt.EnumType.DEFAULT);}
    }

	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	list.add(new ItemStack(item, 1, SaltDirt.EnumType.LAKE.getMetadata()));
    	list.add(new ItemStack(item, 1, SaltDirt.EnumType.DEFAULT.getMetadata()));
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, SaltDirt.EnumType.byMetadata(meta));
    }

    @Override
	public int getMetaFromState(IBlockState state)
    {
        return ((SaltDirt.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    @Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
    
    @Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
    	if (worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos)) == 1)
    	{
    		this.saltDamage(worldIn, entityIn);
    		super.onEntityWalk(worldIn, pos, entityIn);
        }
    }
    
    private void saltDamage(World worldIn, Entity entity)
    {
   		if (entity instanceof EntityLivingBase && EntityList.getEntityString(entity) != null &&
   	   	  ((EntityList.getEntityString(entity).toLowerCase().contains("slime") && !EntityList.getEntityString(entity).toLowerCase().contains("lava")) ||
   	   		EntityList.getEntityString(entity).toLowerCase().contains("witch")))
   	   		{entity.attackEntityFrom(DamageSource.cactus, 1.0F);}
   		if (entity instanceof EntityPlayer){((EntityPlayer)entity).addStat(AchievSalt.saltLake, 1);}
    }
    
    @Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			if (world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) == 1)
			{
				//Таяние льда
				if (world.getBlockState(pos.up()).getMaterial() == Material.CRAFTED_SNOW ||
    				world.getBlockState(pos.up()).getMaterial() == Material.ICE)
				{
					world.setBlockState(pos.up(), Blocks.WATER.getDefaultState(), 3);
				}
			}
    	
			//Таяние снега
			if (world.getBlockState(pos.up()).getMaterial() == Material.SNOW)
			{
				world.setBlockToAir(pos.up());
			}
        }
	}
    
    @Override
	public MapColor getMapColor(IBlockState state)
    {
    	MapColor color = MapColor.DIRT;
    	if (state.getBlock().getMetaFromState(state) == 1) {color = MapColor.QUARTZ;}
        return color;
    }
    
    public enum EnumType implements IStringSerializable
	{
		DEFAULT(0, "default"),
		LAKE(1, "lake");

        private static final SaltDirt.EnumType[] META_LOOKUP = new SaltDirt.EnumType[values().length];
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
		
		public static SaltDirt.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
			return META_LOOKUP[meta];
		}
		
        static
        {
            for (SaltDirt.EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
	}
}