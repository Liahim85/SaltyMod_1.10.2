package ru.liahim.saltmod.inventory.container;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import ru.liahim.saltmod.api.ExtractRegistry;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.ModItems;

public class SlotExtractor extends Slot {
   
	private EntityPlayer thePlayer;
    private int count;

    public SlotExtractor(EntityPlayer player, IInventory inv, int x, int y, int z)
    {
        super(inv, x, y, z);
        this.thePlayer = player;
    }

    @Override
	public boolean isItemValid(ItemStack stack)
    {
        return false;
    }
    
    @Override
	public ItemStack decrStackSize(int par_1)
    {
        if (this.getHasStack())
        {
            this.count += Math.min(par_1, this.getStack().stackSize);
        }

        return super.decrStackSize(par_1);
    }

    @Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
    {
        this.onCrafting(stack);
        super.onPickupFromSlot(player, stack);
    }
    
    @Override
	protected void onCrafting(ItemStack stack, int par_2)
    {
        this.count += par_2;
        this.onCrafting(stack);
    }
    
    @Override
	protected void onCrafting(ItemStack stack)
    {
    	stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.count);

        if (!this.thePlayer.worldObj.isRemote)
        {
            int i = this.count;
            float f = ExtractRegistry.instance().getExtractExperience(stack);
            int j;

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                j = MathHelper.floor_float(i * f);

                if (j < MathHelper.ceiling_float_int(i * f) && (float)Math.random() < i * f - j)
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, j));
            }
        }

        this.count = 0;
        
        if (stack.getItem() == ModItems.saltPinch)
        {
            this.thePlayer.addStat(AchievSalt.moreBuckets, 1);
        }
    }
}