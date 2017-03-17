package ru.liahim.saltmod.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.liahim.saltmod.entity.EntityRainmaker;

public class Rainmaker extends Item {
	
	public static NBTTagCompound tag = new NBTTagCompound();
	private static NBTTagCompound tag1 = new NBTTagCompound();
	private static NBTTagList nbtlist = new NBTTagList();
	
	public Rainmaker(String name, CreativeTabs tab) {
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		Rainmaker.tag1.setIntArray("Colors", new int[] {2651799, 4312372});
		Rainmaker.tag1.setIntArray("FadeColors", new int[] {15790320});
		Rainmaker.tag1.setBoolean("Trail", true);
		Rainmaker.tag1.setByte("Type", (byte)1);
		Rainmaker.nbtlist.appendTag(tag1);
		Rainmaker.tag.setTag("Explosions", nbtlist);
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
	{list.add(I18n.format(getUnlocalizedName() + ".tooltip"));}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
        	EntityRainmaker entityRainmaker = new EntityRainmaker(world, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ, player);
            world.spawnEntityInWorld(entityRainmaker);

            if (!player.capabilities.isCreativeMode)
            {
                --stack.stackSize;
            }

            return EnumActionResult.SUCCESS;
        }
        
        else
        {
            return EnumActionResult.PASS;
        }
    }	
}