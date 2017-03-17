package ru.liahim.saltmod.tileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.api.ExtractRegistry;
import ru.liahim.saltmod.block.Extractor;
import ru.liahim.saltmod.init.SaltConfig;
import ru.liahim.saltmod.inventory.container.ContainerExtractor;

public class TileEntityExtractor extends TileEntityLockable implements ITickable, ISidedInventory, IFluidHandler
{
    private static final int[] slotsBottom = new int[] {0, 1};
    private static final int[] slotsSides = new int[] {1};
    private ItemStack[] invSlots = new ItemStack[2];
    public int burningTime;
    public int currentItemBurnTime;
    public int extractTime;
    public int liquidID;
    public int liquidLevel;
    private String inventoryName;
    
    //red stone
    private int liquidChange;
    private int redSS;
    
    //steam
    private int steamLevel;
    private int steamTime;
    public int pressure;

    //tank
    private static final int maxCap = FluidContainerRegistry.BUCKET_VOLUME * SaltConfig.extractorVolume;
	public FluidTank tank = new FluidTank(maxCap);

/** UPDATE */

	@Override
	public void update()
    {
		if (!this.worldObj.isRemote)
        {
			boolean burn = this.burningTime > 0;
			boolean teUpdate = false;
			boolean clear = !this.worldObj.isSideSolid(this.pos.up(), EnumFacing.DOWN);
			IBlockState stateUp = this.worldObj.getBlockState(this.pos.up());
			Block blockUp = stateUp.getBlock();
			boolean liquid = FluidRegistry.lookupFluidForBlock(blockUp) != null || blockUp instanceof BlockDynamicLiquid;

			if (liquid)
			{
				Fluid blockFluid = null;
				if (FluidRegistry.lookupFluidForBlock(blockUp) != null) {blockFluid = FluidRegistry.lookupFluidForBlock(blockUp);}
				else if (stateUp.getMaterial() == Material.WATER) {blockFluid = FluidRegistry.WATER;}
				else if (stateUp.getMaterial() == Material.LAVA) {blockFluid = FluidRegistry.LAVA;}

				if (blockFluid != null && !blockFluid.isGaseous() && blockUp.getMetaFromState(stateUp) == 0)
				{
					int den = blockFluid.getViscosity() / 200;

					if (this.liquidLevel == 0 || (TileEntityExtractor.maxCap - this.liquidLevel > den &&
							blockFluid == FluidRegistry.getFluid(this.liquidID))) {
						this.worldObj.setBlockToAir(this.pos.up());
						this.tank.fill(new FluidStack(blockFluid, FluidContainerRegistry.BUCKET_VOLUME), true);
						liquid = false;
					}
    		
					else if (TileEntityExtractor.maxCap - this.liquidLevel == den)
					{this.tank.fill(new FluidStack(blockFluid, den), true);}
				}        	
			}

			if (this.liquidLevel > 0 && liquidChange == 0)
			{
				liquidChange = this.liquidLevel;
				teUpdate = true;
				if (this.canExtract()) 
				{Extractor.setState(this.burningTime > 0, true, this.worldObj, this.pos);}
			}

			if (this.liquidLevel == 0 && liquidChange > 0)
			{
				liquidChange = 0;
				extractTime = 0;
				teUpdate = true;
				Extractor.setState(this.burningTime > 0, false, this.worldObj, this.pos);
			}

			if (this.burningTime > 0)
			{
				--this.burningTime;
			}

			if (liquidChange != this.liquidLevel && redSS != this.getFluidAmountScaled(15))
			{
				liquidChange = this.liquidLevel;
				redSS = this.getFluidAmountScaled(15);
				teUpdate = true;
			}

        	this.liquidID = this.tank.getFluid() != null ? FluidRegistry.getFluidID(this.tank.getFluid().getFluid()) : 0;
        	this.liquidLevel = this.tank.getFluid() != null ? this.tank.getFluidAmount() : 0;

            if (this.burningTime != 0 || this.invSlots[1] != null && !this.isFluidTankEmpty())
            {            	
                if (this.burningTime == 0 && this.canExtract() && !liquid)
                {
                    this.currentItemBurnTime = this.burningTime = TileEntityFurnace.getItemBurnTime(this.invSlots[1]);

                    if (this.burningTime > 0)
                    {
                    	teUpdate = true;

                        if (this.invSlots[1] != null)
                        {
                            --this.invSlots[1].stackSize;

                            if (this.invSlots[1].stackSize == 0)
                            {
                                this.invSlots[1] = invSlots[1].getItem().getContainerItem(invSlots[1]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canExtract())
                {
                	if (clear && !liquid)
                	{
                		int vol = ExtractRegistry.instance().getExtractFluidVolum(this.tank.getFluid().getFluid());
                		++this.extractTime;

                		if (this.extractTime == vol)
                		{
                			this.extractTime = 0;
                			this.extract();
                			teUpdate = true;                			
                		}

                		this.tank.drain(1, true);
                	}
                	
                	else if (liquid) {}
                	else {pressure();}
                }
                
                else
                {
                    this.extractTime = 0;
                }
            }

            if (burn != this.burningTime > 0)
            {
            	teUpdate = true;
                Extractor.setState(this.burningTime > 0, this.canExtract(), this.worldObj, this.pos);
            }
            
            if ((this.steamLevel != 0 && clear) || (this.liquidLevel == 0 && !clear) || !this.isBurning())
            {this.pressure = 0; this.steamLevel = 0; this.steamTime = 0;}

            if (teUpdate) {this.markDirty();}
        }
    }

    public boolean isBurning()
    {
        return this.burningTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inv)
    {
        return inv.getField(0) > 0;
    }

    public void pressure()
    {
    	int vol = ExtractRegistry.instance().getExtractFluidVolum(this.tank.getFluid().getFluid());
    	this.pressure = this.steamLevel/((32 - getFluidAmountScaled(32) + 1) * 4);
    	++this.steamTime;

    	if (this.steamTime % (pressure + 1) == 0)
    	{
    		++this.extractTime;
    		this.steamTime = 0;

    		if (this.extractTime == vol)
    		{
    			this.extractTime = 0;
    			this.extract();
    			this.markDirty();
    		}
    		
    		this.tank.drain(1, true);
    	}
    	
    	++this.steamLevel;
    	
    	if (pressure >= 16)
    	{
    		this.worldObj.setBlockToAir(this.pos);
    		this.worldObj.createExplosion((Entity)null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 2.5F, true);
    	}
    }

	public boolean canExtract()
    {
    	if (this.isFluidTankEmpty()) {return false;}
    	
    	else
    	{
    		ItemStack itemstack = ExtractRegistry.instance().getExtractItemStack(this.tank.getFluid().getFluid());
    		if (itemstack == null) return false;
    		if (this.invSlots[0] == null) return true;
    		if (!this.invSlots[0].isItemEqual(itemstack)) return false;
    		int result = invSlots[0].stackSize + itemstack.stackSize;
    		return result <= getInventoryStackLimit() && result <= this.invSlots[0].getMaxStackSize();
    	}
    }
    
    public void extract()
    {
        if (this.canExtract())
        {
            ItemStack itemstack = ExtractRegistry.instance().getExtractItemStack(this.tank.getFluid().getFluid());

            if (this.invSlots[0] == null)
            {
                this.invSlots[0] = itemstack.copy();
            }
            
            else if (this.invSlots[0].isItemEqual(itemstack))
            {
                this.invSlots[0].stackSize += itemstack.stackSize;
            }
        }
    }
    
/** INVENTORY */

    @Override
	public ItemStack getStackInSlot(int index)
    {
        return this.invSlots[index];
    }

    @Override
	public ItemStack decrStackSize(int index, int count)
    {
        if (this.invSlots[index] != null)
        {
            ItemStack itemstack;

            if (this.invSlots[index].stackSize <= count)
            {
                itemstack = this.invSlots[index];
                this.invSlots[index] = null;
                return itemstack;
            }
            
            else
            {
                itemstack = this.invSlots[index].splitStack(count);

                if (this.invSlots[index].stackSize == 0)
                {
                    this.invSlots[index] = null;
                }

                return itemstack;
            }
        }
        
        else
        {
            return null;
        }
    }

    @Override
	public ItemStack removeStackFromSlot(int index)
    {
        if (this.invSlots[index] != null)
        {
            ItemStack itemstack = this.invSlots[index];
            this.invSlots[index] = null;
            return itemstack;
        }
        
        else
        {
            return null;
        }
    }

    @Override
	public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.invSlots[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
	public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
	public int getSizeInventory()
    {
        return this.invSlots.length;
    }

    @Override
	public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
    }

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

    @Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == 0 ? false : (index == 1 ? TileEntityFurnace.isItemFuel(stack) : false);
    }

    @Override
	public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? slotsBottom : (side != EnumFacing.UP ? slotsSides : new int[0]);
    }

    @Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
    	return direction != EnumFacing.DOWN || index != 1 || stack.getItem() == Items.BUCKET;
    }
    
/** NAMES */

    @Override
	public String getName()
    {
        return this.hasCustomName() ? this.inventoryName : "container.extractor";
    }

    @Override
	public boolean hasCustomName()
    {
        return this.inventoryName != null && !this.inventoryName.isEmpty();
    }

    public void setCustomInventoryName(String string)
    {
        this.inventoryName = string;
    }

    @Override
	public String getGuiID()
    {
        return "saltmod:extractor";
    }

    @Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerExtractor(playerInventory, this);
    }

    @Override
	public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.burningTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.extractTime;
            case 3:
                return this.liquidID;
            case 4:
                return this.liquidLevel;
            case 5:
                return this.pressure;
            default:
                return 0;
        }
    }
    
    @Override
	public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.burningTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.extractTime = value;
                break;
            case 3:
                this.liquidID = value;
                break;
            case 4:
                this.liquidLevel = value;
                break;
            case 5:
                this.pressure = value;
        }
    }
    
    @Override
	public int getFieldCount()
    {
        return 6;
    }

    @Override
	public void clear()
    {
        for (int i = 0; i < this.invSlots.length; ++i)
        {
            this.invSlots[i] = null;
        }
    }
    
/** NBT */

    @Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList inv_tags = nbt.getTagList("Items", 10);
        this.invSlots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < inv_tags.tagCount(); ++i)
        {
            NBTTagCompound tag = inv_tags.getCompoundTagAt(i);
            byte b0 = tag.getByte("Slot");

            if (b0 >= 0 && b0 < this.invSlots.length)
            {
                this.invSlots[b0] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        this.burningTime = nbt.getShort("BurnTime");
        this.extractTime = nbt.getShort("CookTime");
        this.currentItemBurnTime = nbt.getShort("ItemBurnTime");
        this.steamLevel = nbt.getShort("SteamLevel");
        readTankFromNBT(nbt);

        if (nbt.hasKey("CustomName", 8))
        {
            this.inventoryName = nbt.getString("CustomName");
        }
    }

	protected void readTankFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("Tank"))
		{
			this.tank.readFromNBT(nbt.getCompoundTag("Tank"));
		}
	}

    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setShort("BurnTime", (short)this.burningTime);
        nbt.setShort("CookTime", (short)this.extractTime);
        nbt.setShort("ItemBurnTime", (short)this.currentItemBurnTime);
        nbt.setShort("SteamLevel", (short)this.steamLevel);
        
        NBTTagList taglist = new NBTTagList();

        for (int i = 0; i < this.invSlots.length; ++i)
        {
            if (this.invSlots[i] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte)i);
                this.invSlots[i].writeToNBT(tag);
                taglist.appendTag(tag);
            }
        }

        nbt.setTag("Items", taglist);
        writeTankToNBT(nbt);

        if (this.hasCustomName())
        {
        	nbt.setString("CustomName", this.inventoryName);
        }
        
        return nbt;
    }

	protected void writeTankToNBT(NBTTagCompound nbt)
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.tank.writeToNBT(tag);
		nbt.setTag("Tank", tag);
	}
	
/** FLUID */

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill)
	{
		int f = this.tank.fill(resource, doFill);
		return f;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
	{
		if (resource == null || !resource.isFluidEqual(this.tank.getFluid())) 
		{
			return null;
		}

		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
	{
		FluidStack d = this.tank.drain(maxDrain, doDrain);
		return d;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		return new FluidTankInfo[] {this.tank.getInfo()};
	}

	public int getFluidAmountScaled(int scale) 
	{
		return MathHelper.ceiling_float_int((float)this.getFluidAmount() * scale / TileEntityExtractor.maxCap);
	}

	public boolean isFluidTankEmpty()
	{
		return getFluidAmount() == 0;
	}

	public int getFluidAmount()
	{
		return this.tank.getFluidAmount();
	}
}