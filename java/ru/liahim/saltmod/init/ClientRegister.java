package ru.liahim.saltmod.init;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import ru.liahim.saltmod.SaltMod;

public class ClientRegister {
	
	public static String modid = SaltMod.MODID;
	
	public static void registerBlockRenderer()
	{
		registerBlocks(ModBlocks.saltOre);
		registerBlocks(ModBlocks.saltLake);
		registerBlocks(ModBlocks.saltBrickStair);
		registerMultyBlocks(ModBlocks.saltBlock, 0, "saltBlock");
		registerMultyBlocks(ModBlocks.saltBlock, 1, "saltBlock_Chiseled");
		registerMultyBlocks(ModBlocks.saltBlock, 2, "saltBlock_Pillar");
		registerMultyBlocks(ModBlocks.saltBlock, 5, "saltBrick");
		registerMultyBlocks(ModBlocks.saltBlock, 6, "saltBlock_Cracked");
		registerMultyBlocks(ModBlocks.saltBlock, 7, "saltBrick_Cracked");
		registerMultyBlocks(ModBlocks.saltBlock, 8, "saltBrick_Chiseled");
		registerMultyBlocks(ModBlocks.saltBlock, 9, "saltChapiter");
		registerMultyBlocks(ModBlocks.saltSlab, 0, "saltSlab_Block");
		registerMultyBlocks(ModBlocks.saltSlab, 1, "saltSlab_Brick");
		registerMultyBlocks(ModBlocks.saltSlab, 2, "saltSlab_Pillar");
		registerMultyBlocks(ModBlocks.saltSlabDouble, 0, "saltSlab_Double_Block");
		registerMultyBlocks(ModBlocks.saltSlabDouble, 1, "saltSlab_Double_Brick");
		registerMultyBlocks(ModBlocks.saltSlabDouble, 2, "saltSlab_Double_Pillar");
		registerBlocks(ModBlocks.saltLamp);
		registerMultyBlocks(ModBlocks.saltDirt, 1, "saltDirtLake");
		registerMultyBlocks(ModBlocks.saltDirt, 0, "saltDirt");
		registerBlocks(ModBlocks.saltDirtLite);
		registerBlocks(ModBlocks.saltGrass);
		registerBlocks(ModBlocks.mudBlock);		
		registerBlocks(ModBlocks.extractor);
		registerBlocks(ModBlocks.extractorLit);
		registerBlocks(ModBlocks.extractorSteam);
		registerBlocks(ModBlocks.saltCrystal);
		registerBlocks(ModBlocks.saltPot);
	}
	
	public static void init()
	{
	    ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.saltBlock),
	    new ResourceLocation("saltmod:saltBlock"), new ResourceLocation("saltmod:saltBlock_Chiseled"),
	    new ResourceLocation("saltmod:saltBlock_Pillar"), new ResourceLocation("saltmod:saltBrick"),
	    new ResourceLocation("saltmod:saltBlock_Cracked"), new ResourceLocation("saltmod:saltBrick_Cracked"),
	    new ResourceLocation("saltmod:saltBrick_Chiseled"), new ResourceLocation("saltmod:saltChapiter"));

	    ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.saltSlab),
	    new ResourceLocation("saltmod:saltSlab_Block"), new ResourceLocation("saltmod:saltSlab_Brick"),
	    new ResourceLocation("saltmod:saltSlab_Pillar"));

	    ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.saltSlabDouble),
	    new ResourceLocation("saltmod:saltSlab_Double_Block"), new ResourceLocation("saltmod:saltSlab_Double_Brick"),
	    new ResourceLocation("saltmod:saltSlab_Double_Pillar"));
	    
	    ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.saltDirt),
	    new ResourceLocation("saltmod:saltDirt"), new ResourceLocation("saltmod:saltDirtLake"));
	    
	    ModelBakery.registerItemVariants(ModItems.achievItem,
	    new ResourceLocation("saltmod:achievItem_0"), new ResourceLocation("saltmod:achievItem_1"),
	    new ResourceLocation("saltmod:achievItem_2"));
	}
	
	public static void registerItemRenderer()
	{
		registerItems(ModItems.salt);
		registerItems(ModItems.saltPinch);
		registerItems(ModItems.saltWortSeed);
		registerItems(ModItems.soda);
		registerItems(ModItems.mineralMud);
		
		registerItems(ModItems.saltBeefCooked);
		registerItems(ModItems.saltPorkchopCooked);
		registerItems(ModItems.saltPotatoBaked);
		registerItems(ModItems.saltChickenCooked);
		registerItems(ModItems.saltFishCod);
		registerItems(ModItems.saltFishCodCooked);
		registerItems(ModItems.saltFishSalmon);
		registerItems(ModItems.saltFishSalmonCooked);
		registerItems(ModItems.saltFishClownfish);
		registerItems(ModItems.cornedBeef);
		registerItems(ModItems.saltBeetroot);		
		registerItems(ModItems.saltBread);
		registerItems(ModItems.saltEgg);
		registerItems(ModItems.saltMushroomStew);
		registerItems(ModItems.saltBeetrootSoup);
		registerItems(ModItems.pumpkinPorridge);
		registerItems(ModItems.vegetableStew);
		registerItems(ModItems.saltVegetableStew);
		registerItems(ModItems.potatoMushroom);
		registerItems(ModItems.saltPotatoMushroom);
		registerItems(ModItems.fishSoup);
		registerItems(ModItems.saltFishSoup);
		registerItems(ModItems.fishSalmonSoup);
		registerItems(ModItems.saltFishSalmonSoup);
		registerItems(ModItems.saltWortBeef);
		registerItems(ModItems.saltWortPorkchop);
		registerItems(ModItems.beetrootSalad);
		registerItems(ModItems.saltBeetrootSalad);
		registerItems(ModItems.HuFC);
		registerItems(ModItems.saltHuFC);
		registerItems(ModItems.dandelionSalad);
		registerItems(ModItems.saltDandelionSalad);
		registerItems(ModItems.wheatSprouts);
		registerItems(ModItems.saltWheatSprouts);	
		registerItems(ModItems.fruitSalad);
		registerItems(ModItems.gratedCarrot);
		registerItems(ModItems.saltWortSalad);
		registerItems(ModItems.carrotPie);
		registerItems(ModItems.applePie);
		registerItems(ModItems.potatoPie);
		registerItems(ModItems.onionPie);
		registerItems(ModItems.fishPie);
		registerItems(ModItems.fishSalmonPie);
		registerItems(ModItems.mushroomPie);
		registerItems(ModItems.saltWortPie);
		registerItems(ModItems.fermentedSaltWort);
		registerItems(ModItems.pickledMushroom);
		registerItems(ModItems.pickledFern);
		registerItems(ModItems.fizzyDrink);
		registerItems(ModItems.muffin);
		
		registerItems(ModItems.saltMuttonCooked);
		registerItems(ModItems.saltWortMutton);
		registerItems(ModItems.saltRabbitCooked);
		registerItems(ModItems.saltRabbitStew);
		
		registerItems(ModItems.mudHelmet);
		registerItems(ModItems.mudChestplate);
		registerItems(ModItems.mudLeggings);
		registerItems(ModItems.mudBoots);
		
		registerItems(ModItems.powderedMilk);
		
		registerItems(ModItems.escargot);		
		registerItems(ModItems.saltStar);
		registerItems(ModItems.rainmaker);
		
		registerMultyItems(ModItems.achievItem, 0, "achievItem_0");
		registerMultyItems(ModItems.achievItem, 1, "achievItem_1");
		registerMultyItems(ModItems.achievItem, 2, "achievItem_2");
	}
	
	public static void registerItems(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void registerMultyItems(Item item, int meta, String file) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(modid + ":" + file, "inventory"));
	}

	public static void registerBlocks(Block block)
	{
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void registerMultyBlocks(Block block, int meta, String file) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(modid + ":" + file, "inventory"));
	}
}