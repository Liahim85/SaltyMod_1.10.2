package ru.liahim.saltmod.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import ru.liahim.saltmod.entity.EntityRainmaker;
import ru.liahim.saltmod.entity.EntityRainmakerDust;
import ru.liahim.saltmod.entity.render.RenderRainmakerDust;
import ru.liahim.saltmod.init.ClientRegister;
import ru.liahim.saltmod.init.ModItems;

public class ClientProxy extends CommonProxy {
	
	/*@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityRainmaker.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderSnowball(manager, ModItems.rainmaker, Minecraft.getMinecraft().getRenderItem());
			}
		});
	
        /*RenderingRegistry.registerEntityRenderingHandler(EntityRainmakerDust.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderRainmakerDust(manager);
			}
		});
	}*/

	@Override
	public void init(FMLInitializationEvent event)
	{
	    super.init(event);
	    ClientRegister.init();
	    ClientRegister.registerItemRenderer();
	    ClientRegister.registerBlockRenderer();
	    RenderingRegistry.registerEntityRenderingHandler(EntityRainmaker.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ModItems.rainmaker, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRainmakerDust.class, new RenderRainmakerDust(Minecraft.getMinecraft().getRenderManager()));
	}
}