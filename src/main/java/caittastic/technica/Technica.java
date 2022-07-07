package caittastic.technica;

import caittastic.technica.block.ModBlockRegistry;
import caittastic.technica.blockentity.ModBlockEntityRegistry;
import caittastic.technica.item.ModItemRegistry;
import caittastic.technica.screen.MetalBenderScreen;
import caittastic.technica.screen.ModMenuRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Technica.MOD_ID)
public class Technica {
    public static final String MOD_ID = "technica";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Technica() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItemRegistry.ITEMS.register(bus);
        ModBlockRegistry.BLOCKS.register(bus);
        ModBlockEntityRegistry.BLOCK_ENTITIES.register(bus);
        ModMenuRegistry.MENUS.register(bus);

        // Register the doClientStuff method for modloading
        bus.addListener(this::clientSetup);

        bus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }

    private void clientSetup(final FMLCommonSetupEvent event){
        MenuScreens.register(ModMenuRegistry.METAL_BENDER_MENU.get(), MetalBenderScreen::new);
        event.enqueueWork(()->{
        });
    }
}
