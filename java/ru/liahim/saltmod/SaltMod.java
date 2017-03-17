package ru.liahim.saltmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.ModBlockColors;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.ModItems;
import ru.liahim.saltmod.init.SaltConfig;

@Mod(modid = SaltMod.MODID, name = SaltMod.NAME, version = SaltMod.VERSION)

public class SaltMod {

    public static final String MODID = "SaltMod";
    public static final String NAME = "Salty Mod";
    public static final String VERSION = "1.8.d";
	public static final Logger logger = LogManager.getLogger(NAME);
    
	public static SaltConfig config;
	
    @Instance(MODID)
    public static SaltMod instance;
    
	@SidedProxy(clientSide="ru.liahim.saltmod.common.ClientProxy", serverSide="ru.liahim.saltmod.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		logger.info("Starting SaltMod PreInitialization");
        config = new SaltConfig(event.getSuggestedConfigurationFile());
        config.preInit();
	    SaltMod.proxy.preInit(event);
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	config.init();
	    ModBlocks.createBlocks();
        ModItems.createItems();
        if (event.getSide().isClient()) ModBlockColors.ColorRegister();
        AchievSalt.init();
    	SaltMod.proxy.init(event);
    }
 
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	config.postInit();
    	SaltMod.proxy.postInit(event);
    }
 
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event){}
}