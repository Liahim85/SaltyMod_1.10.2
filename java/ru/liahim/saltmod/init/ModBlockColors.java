package ru.liahim.saltmod.init;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModBlockColors {

    public static void ColorRegister()
    {
		final BlockColors block = FMLClientHandler.instance().getClient().getBlockColors();		 
		block.registerBlockColorHandler(new IBlockColor() { 
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex)
			{return worldIn != null && pos != null ? (tintIndex == 1 ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : 0xFFFFFFFF) : ColorizerGrass.getGrassColor(0.5D, 1.0D);}				
		}, ModBlocks.saltGrass);
		
		ItemColors color = FMLClientHandler.instance().getClient().getItemColors();
		color.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex)
			{return block.colorMultiplier(ModBlocks.saltGrass.getDefaultState(), (IBlockAccess)null, (BlockPos)null, tintIndex);}
		}, ModBlocks.saltGrass);
    }
}