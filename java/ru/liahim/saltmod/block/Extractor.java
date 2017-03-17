package ru.liahim.saltmod.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.tileEntity.TileEntityExtractor;

public class Extractor extends BlockContainer {
    
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private final boolean isBurning;
	private final boolean isExtract;
	private static boolean keepInventory;
	
	public Extractor(boolean burn, boolean ext, String name, CreativeTabs tab) {
		super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.isBurning = burn;
		this.isExtract = ext;
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setHardness(5F);
		this.setResistance(10F);
	}
    
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.extractor);
    }

    @Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
    
    
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
        if (this.isBurning)
        {
        	EnumFacing enumfacing = state.getValue(FACING);
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = pos.getX() + rand.nextDouble() * 0.4F + 0.3F;
            double d6 = pos.getZ() + rand.nextDouble() * 0.4F + 0.3F;
            double d7 = (double)pos.getX() + (double)rand.nextFloat();
            double d8 = (double)pos.getZ() + (double)rand.nextFloat();
            boolean clear = !worldIn.isSideSolid(pos.up(), EnumFacing.DOWN) &&
            		FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(pos.up()).getBlock()) == null;
            boolean ceiling = worldIn.isSideSolid(pos.up(2), EnumFacing.DOWN);

            if (rand.nextDouble() < 0.1D)
            {
            	worldIn.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            
            switch (enumfacing)
            {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
            }
            
        	if (isExtract && clear)
        	{
        		worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d5, pos.getY() + 1.1D, d6, 0.0D, 0.1D, 0.0D, new int[0]);

        		if (ceiling && rand.nextInt(10) == 0)
        		worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d7, pos.getY() + 1.95D, d8, 0.0D, 0.0D, 0.0D, new int[0]);
        	}
        }
    }

    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        
        else
        {
        	TileEntityExtractor te = (TileEntityExtractor) worldIn.getTileEntity(pos);

            if (te != null)
            {
				if (!fillTank(worldIn, pos, te, heldItem, playerIn))			
				{
					if (!drainTank(worldIn, pos, te, heldItem, playerIn))
					{
						playerIn.openGui(SaltMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
					}
				}
            }
            
            return true;
        }
	}
        
    public static boolean fillTank(World worldIn, BlockPos pos, IFluidHandler tank, ItemStack held, EntityPlayer playerIn)
    {
    	if (held != null)
    	{
    		FluidStack heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
    		
    		if (heldContents != null && held.getItem() != Items.POTIONITEM)
    		{
    			int used = tank.fill(EnumFacing.UP, heldContents, true);

    			if (used > 0)
    			{
    				ItemStack consumed = held.getItem().getContainerItem(held);
    				
    				if (consumed != null && !playerIn.capabilities.isCreativeMode)
    				{
    					playerInvChange(worldIn, pos, held, playerIn, consumed);
    				}
    				
					if (FluidContainerRegistry.isBucket(consumed))
					{
						if (heldContents.getFluid() == FluidRegistry.LAVA)
						{worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);}
						else {worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);}
					}					

    				return true;
    			}
    		}
    		
    		else if (held.getItem() == Items.POTIONITEM)
    		{
    			heldContents = new FluidStack(FluidRegistry.WATER, 333);
    			int used = tank.fill(EnumFacing.UP, heldContents, true);
    			
    			if (used > 0)
    			{
    				if (!playerIn.capabilities.isCreativeMode)
    				{
    					playerInvChange(worldIn, pos, held, playerIn, new ItemStack(Items.GLASS_BOTTLE));
     				}
    				
    				return true;
    			}
    		}
    	}

    	return false;
    }
    
	private boolean drainTank(World worldIn, BlockPos pos, IFluidHandler tank, ItemStack held, EntityPlayer playerIn)
	{
		if (held != null)
		{
			FluidStack heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
			FluidStack available = tank.drain(EnumFacing.UP, Integer.MAX_VALUE, false);

			if (available != null)
			{
				ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, held);
				heldContents = FluidContainerRegistry.getFluidForFilledItem(filled);

				if (available.getFluid() == FluidRegistry.WATER && held.getItem() == Items.GLASS_BOTTLE && available.amount >= 333)
				{
					if (!playerIn.capabilities.isCreativeMode)
					{
						playerInvChange(worldIn, pos, held, playerIn, new ItemStack(Items.POTIONITEM));
					}
					
					tank.drain(EnumFacing.UP, 333, true);
					
					return true;
				}
				
				else if (heldContents != null)
				{
					if (!playerIn.capabilities.isCreativeMode)
					{
 						playerInvChange(worldIn, pos, held, playerIn, filled);
					}
					
					if (FluidContainerRegistry.isBucket(held))
					{
						if (heldContents.getFluid() == FluidRegistry.LAVA)
						{worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);}
						else {worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);}
					}

					tank.drain(EnumFacing.UP, heldContents.amount, true);

					return true;
				}
			}
		}

		return false;
	}
	
    private static void playerInvChange(World worldIn, BlockPos pos, ItemStack held, EntityPlayer playerIn, ItemStack stack)
    {
		if (--held.stackSize <= 0)
		{
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
		}

		if (playerIn.inventory.getCurrentItem() != null)
		{
			if (!playerIn.inventory.addItemStackToInventory(stack))
			{
				worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, stack));
			}
			
			else if (playerIn instanceof EntityPlayerMP)
			{
				((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
			}
		}
		
		else
		{
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, stack);
			
			if (playerIn instanceof EntityPlayerMP)
			{
				((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
			}
		}
    }
    
    public static void setState(boolean active, boolean extract, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active)
        {
        	if (extract)
        	{
        		worldIn.setBlockState(pos, ModBlocks.extractorSteam.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        		worldIn.setBlockState(pos, ModBlocks.extractorSteam.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        	}
        	
        	else
        	{
        		worldIn.setBlockState(pos, ModBlocks.extractorLit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        		worldIn.setBlockState(pos, ModBlocks.extractorLit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        	}
        }
        
        else
        {
        	worldIn.setBlockState(pos, ModBlocks.extractor.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
    		worldIn.setBlockState(pos, ModBlocks.extractor.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
		return new TileEntityExtractor();
	}
    
    @Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TileEntityExtractor)
            {
                ((TileEntityExtractor)te).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TileEntityExtractor)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityExtractor)te);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
	public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
    
    @Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
    	TileEntityExtractor te = (TileEntityExtractor)worldIn.getTileEntity(pos);
    	return te.tank.getFluid() != null ? te.getFluidAmountScaled(15) : 0;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.extractor);
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
	public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
	public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
    
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
}