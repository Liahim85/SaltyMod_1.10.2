package ru.liahim.saltmod.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.ModItems;
import ru.liahim.saltmod.init.SaltConfig;

public class SaltWort extends BlockBush implements IGrowable {
	
	public static final PropertyEnum STAGE = PropertyEnum.create("stage", SaltWort.EnumType.class);
	
	public SaltWort(String name, CreativeTabs tab) {
		this.setUnlocalizedName(name);
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(tab);
		this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, SaltWort.EnumType.STAGE_0));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, SaltWort.EnumType.byMetadata(meta));
	}
    
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((SaltWort.EnumType)state.getValue(STAGE)).getMetadata();
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {STAGE});
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		float g = 0.375F;
		float v = 0.625F;
		if (meta == 1) {g = 0.375F; v = 0.5F;}
		if (meta == 2) {g = 0.3125F; v = 0.375F;}
		if (meta == 3) {g = 0.25F; v = 0.25F;}
		if (meta == 4) {g = 0.1875F; v = 0.125F;}
		if (meta == 5) {g = 0.3125F; v = 0.5625F;}
        return new AxisAlignedBB(0.0F + g, 0.0F, 0.0F + g, 1.0F - g, 1.0F - v, 1.0F - g);
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
		return this.canBlockStay(world, pos, world.getBlockState(pos));
    }
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
		IBlockState state2 = world.getBlockState(pos.down());
		Block B = state2.getBlock();
		Material M = state2.getMaterial();
		if ((M == Material.GRASS || (M == Material.GROUND && B != ModBlocks.saltDirt) || M == Material.SAND || M == Material.CLAY ||
		    (B == ModBlocks.saltDirt && B.getMetaFromState(state2) == 0)) && state2.isSideSolid(world, pos.down(), EnumFacing.UP))
		{
			return true;
		}
		
		else
        {
			return false;
        }
	}

	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> drop = new ArrayList<ItemStack>();
		Item seed = ModItems.saltWortSeed;
		Random rand = new Random();
		int meta = state.getBlock().getMetaFromState(state);
		int i;
		
		if (meta <= 1)
		{
			drop.add(new ItemStack(seed));
		}
		if (meta == 2)
		{
			i = rand.nextInt(2) + 1;
			drop.add(new ItemStack(seed, i));
		}
		if (meta == 3)
		{
			i = rand.nextInt(3) + 1;
			drop.add(new ItemStack(seed, i));
		}
		if (meta == 4)
		{
			i = rand.nextInt(4) + 2;
			drop.add(new ItemStack(seed, i));
		}
		
		return drop;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			BlockPos pos2 = pos.down();
			IBlockState state2 = world.getBlockState(pos2);
			Block soil = world.getBlockState(pos2).getBlock();
			int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
			int meta2 = soil.getMetaFromState(state2);
		
			if ((soil == ModBlocks.saltDirt && meta2 == 0) || (soil == ModBlocks.saltDirtLite && (meta2 == 1 || meta2 == 2)))
			{
				if (rand.nextInt(SaltConfig.saltWortGrowSpeed) == 0)
				{
					if (meta == 0) {world.setBlockState(pos, this.getStateFromMeta(1), 3);}
					
					else if (meta == 1 && world.getLightFromNeighbors(pos) >= 12)
					{
						world.setBlockState(pos, this.getStateFromMeta(2), 3);

						if (soil == ModBlocks.saltDirt)
						{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3);}
						else if (soil == ModBlocks.saltDirtLite && meta2 == 2)
						{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3);}
						else if (soil == ModBlocks.saltDirtLite && meta2 == 1)
						{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState(), 3);}
					}
					
					else if (meta >= 2 && meta <= 4 && world.getLightFromNeighbors(pos) >= 12)
					{
						if (meta == 2 || meta == 3)
						{
							if (meta == 2)
							{world.setBlockState(pos, this.getStateFromMeta(3), 3);}
							else if (meta == 3)
							{world.setBlockState(pos, this.getStateFromMeta(4), 3);}

							if (soil == ModBlocks.saltDirt)
							{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3);}
							else if (soil == ModBlocks.saltDirtLite && meta2 == 2)
							{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3);}
							else if (soil == ModBlocks.saltDirtLite && meta2 == 1)
							{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState(), 3);}
						}

						int S = 0;
						int x = pos.getX();
						int y = pos.getY();
						int z = pos.getZ();

						for (int xi = x - 2; xi <= x + 2; xi++) {
						for (int zi = z - 2; zi <= z + 2; zi++) {
						
							if (world.getBlockState(new BlockPos(xi, y, zi)).getBlock() == ModBlocks.saltWort)
							{S = S + 1;}
						}
						}

						if (S < 7)
						{
							for (int xj = x - 1; xj <= x + 1; xj++) {
							for (int zj = z - 1; zj <= z + 1; zj++) {

								BlockPos jPos = new BlockPos(xj, y - 1, zj);
								Block jSoil = world.getBlockState(jPos).getBlock();
								int jMeta = jSoil.getMetaFromState(world.getBlockState(jPos));

								if (rand.nextInt(8) == 0 && world.isAirBlock(jPos.up()) &&
								  ((jSoil == ModBlocks.saltDirtLite && (jMeta == 1 || jMeta == 2)) || (jSoil == ModBlocks.saltDirt && jMeta == 0)) &&
								  ((soil == ModBlocks.saltDirtLite && (meta2 == 1 || meta2 == 2)) || (soil == ModBlocks.saltDirt && meta2 == 0)))
								{
									world.setBlockState(jPos.up(), this.getDefaultState(), 3);
							
									if (jSoil == ModBlocks.saltDirt)
									{world.setBlockState(jPos, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3);}
									else if (jSoil == ModBlocks.saltDirtLite && jMeta == 2)
									{world.setBlockState(jPos, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3);}
									else if (jSoil == ModBlocks.saltDirtLite && jMeta == 1)
									{world.setBlockState(jPos, ModBlocks.saltDirtLite.getDefaultState(), 3);}
							
									if (meta == 4)
									{
										if (soil == ModBlocks.saltDirt)
										{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3);}
										else if (soil == ModBlocks.saltDirtLite && meta2 == 2)
										{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3);}
										else if (soil == ModBlocks.saltDirtLite && meta2 == 1)
										{world.setBlockState(pos2, ModBlocks.saltDirtLite.getDefaultState(), 3);}
									}
								}
							}
							}
						}
					}
				}
			}

			else
			{
				if (rand.nextInt(SaltConfig.saltWortGrowSpeed + 1) == 0)
				{
					if (meta == 0)
					{world.setBlockState(pos, this.getStateFromMeta(1), 3);}
					else if (meta == 1)
					{world.setBlockState(pos, this.getStateFromMeta(5), 3);}
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote && world.getBlockState(pos).getBlock().getMetaFromState(state) == 4 &&
				 heldItem != null && heldItem.getItem() == Items.SHEARS)
		{
			world.setBlockState(pos, this.getStateFromMeta(2), 3);

			Random rand = new Random();
			int i = rand.nextInt(3) + 1;				
			int fort = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), heldItem);
			if (fort > 0)
			{
				i = rand.nextInt(5 - fort) + fort;
			}

			ItemStack item = new ItemStack(ModItems.saltWortSeed, i, 0);
			EntityItem entity_item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, item);
			entity_item.setDefaultPickupDelay();
			world.spawnEntityInWorld(entity_item);
			world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.2F);
								
			if (!player.capabilities.isCreativeMode)
			{
				heldItem.damageItem(1, player);
				if (heldItem.getItemDamage() > heldItem.getMaxDamage()){--heldItem.stackSize;}
			}
		}

        return false;
    }
	
	public static boolean fertilize(World world, BlockPos pos)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		boolean chek = false;
			
		for (int x3 = x - 1; x3 <= x + 1; x3++) {
		for (int z3 = z - 1; z3 <= z + 1; z3++) {
				
			BlockPos jPos = new BlockPos(x3, y, z3);
			Block jBlock = world.getBlockState(jPos).getBlock();
			Block jSoil = world.getBlockState(jPos.down()).getBlock();
			int jMeta = world.getBlockState(jPos).getBlock().getMetaFromState(world.getBlockState(jPos));
			int jMeta2 = jSoil.getMetaFromState(world.getBlockState(jPos.down()));
			boolean P = false;

			if (jBlock == ModBlocks.saltWort && jMeta < 4)
			{
				if ((jSoil == ModBlocks.saltDirt && jMeta2 == 0) ||
					(jSoil == ModBlocks.saltDirtLite && (jMeta2 == 1 || jMeta2 == 2)))
				{
					if (jMeta == 0) {world.setBlockState(jPos, ModBlocks.saltWort.getDefaultState().withProperty(SaltWort.STAGE, SaltWort.EnumType.STAGE_1), 3); chek = true; P = true;}

					else
					{
						if (jMeta == 1)
						{world.setBlockState(jPos, ModBlocks.saltWort.getDefaultState().withProperty(SaltWort.STAGE, SaltWort.EnumType.STAGE_2), 3); chek = true; P = true;}
						else if (jMeta == 2)
						{world.setBlockState(jPos, ModBlocks.saltWort.getDefaultState().withProperty(SaltWort.STAGE, SaltWort.EnumType.STAGE_3), 3); chek = true; P = true;}
						else if (jMeta == 3)
						{world.setBlockState(jPos, ModBlocks.saltWort.getDefaultState().withProperty(SaltWort.STAGE, SaltWort.EnumType.STAGE_4), 3); chek = true; P = true;}

						if (jMeta < 5)
						{
							if (jSoil == ModBlocks.saltDirt)
							{world.setBlockState(jPos.down(), ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3);}
							else if (jSoil == ModBlocks.saltDirtLite && jMeta2 == 2)
							{world.setBlockState(jPos.down(), ModBlocks.saltDirtLite.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3);}
							else if (jSoil == ModBlocks.saltDirtLite && jMeta2 == 1)
							{world.setBlockState(jPos.down(), ModBlocks.saltDirtLite.getDefaultState(), 3);}
						}
					}
				}

				else if (jMeta == 0) {world.setBlockState(jPos, ModBlocks.saltWort.getDefaultState().withProperty(SaltWort.STAGE, SaltWort.EnumType.STAGE_1), 3); chek = true; P = true;}
			}
			
			if (P) {P = false; if (!world.isRemote) world.playEvent(2005, jPos, 0);}
		}
		}
			
		if (chek) {chek = false; return true;}
		
		return false;		
	}
	
    @Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 30;
    }
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}
	
	public enum EnumType implements IStringSerializable
	{
		STAGE_0(0, "stage_0"),
		STAGE_1(1, "stage_1"),
		STAGE_2(2, "stage_2"),
		STAGE_3(3, "stage_3"),
		STAGE_4(4, "stage_4"),
		DEAD(5, "dead");

        private static final SaltWort.EnumType[] META_LOOKUP = new SaltWort.EnumType[values().length];
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
		
		@Override
		public String toString()
		{
		    return getName();
		}
		
		public static SaltWort.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
			return META_LOOKUP[meta];
		}
		
        static
        {
            for (SaltWort.EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return fertilize(worldIn, pos);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {}
}