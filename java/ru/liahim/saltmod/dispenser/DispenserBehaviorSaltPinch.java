package ru.liahim.saltmod.dispenser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.liahim.saltmod.block.SaltDirtLite;
import ru.liahim.saltmod.init.ModBlocks;

public class DispenserBehaviorSaltPinch implements IBehaviorDispenseItem {

	@Override
	public ItemStack dispense(IBlockSource source, ItemStack stack)
	{
		World world = source.getWorld();
		EnumFacing enumfacing = source.getBlockState().getValue(BlockDispenser.FACING);
		IPosition iposition = BlockDispenser.getDispensePosition(source);
		BlockPos posFace = new BlockPos(iposition.getX(), iposition.getY(), iposition.getZ());
		BlockPos posSoil = posFace.down();
		IBlockState stateFace = world.getBlockState(posFace);
		IBlockState stateSoil = world.getBlockState(posSoil);
		Block blockFace = stateFace.getBlock();
		Block soil = stateSoil.getBlock();
		boolean chek = false;
		
		if (blockFace == ModBlocks.saltDirtLite)
		{
			int meta = world.getBlockState(posFace).getBlock().getMetaFromState(stateFace);

			if (meta == 0 || meta > 2)
			{world.setBlockState(posFace, blockFace.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3); stack.splitStack(1); chek = true;}
			else if (meta == 1)
			{world.setBlockState(posFace, blockFace.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3); stack.splitStack(1); chek = true;}
			else if (meta == 2)
			{world.setBlockState(posFace, ModBlocks.saltDirt.getDefaultState(), 3); stack.splitStack(1); chek = true;}	
		}
		
		else if (soil == ModBlocks.saltDirtLite && (world.isAirBlock(posFace) || blockFace == ModBlocks.saltWort))
		{
			int meta = world.getBlockState(posSoil).getBlock().getMetaFromState(stateSoil);

			if (meta == 0 || meta > 2)
			{world.setBlockState(posSoil, soil.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.MEDIUM), 3); stack.splitStack(1); chek = true;}
			else if (meta == 1)
			{world.setBlockState(posSoil, soil.getDefaultState().withProperty(SaltDirtLite.VARIANT, SaltDirtLite.EnumType.FULL), 3); stack.splitStack(1); chek = true;}
			else if (meta == 2)
			{world.setBlockState(posSoil, ModBlocks.saltDirt.getDefaultState(), 3); stack.splitStack(1); chek = true;}
		}
		
		else if (blockFace == ModBlocks.saltDirt) {}
		else if (soil == ModBlocks.saltDirt && (world.isAirBlock(posFace) || blockFace == ModBlocks.saltWort)) {}		
		else {BehaviorDefaultDispenseItem.doDispense(world, stack.splitStack(1), 1, enumfacing, iposition); chek = true;}
	
		if (chek) {source.getWorld().playEvent(1000, source.getBlockPos(), 0);}
		else {source.getWorld().playEvent(1001, source.getBlockPos(), 0);}
		
		source.getWorld().playEvent(2000, source.getBlockPos(), enumfacing.getFrontOffsetX() + 1 + (enumfacing.getFrontOffsetZ() + 1) * 3);
		
		return stack;
	}
}