package caittastic.technica;

import caittastic.technica.block.ModBlocks;
import caittastic.technica.blockentity.ModBlockEntityRegistry;
import caittastic.technica.item.ModItems;
import caittastic.technica.menuScreen.GeneratorScreen;
import caittastic.technica.recipe.ModRecipeRegistry;
import caittastic.technica.menuScreen.MetalBenderScreen;
import caittastic.technica.menuScreen.ModMenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Technica.MOD_ID)
public class Technica {
    public static final String MOD_ID = "technica";

    public Technica() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntityRegistry.BLOCK_ENTITIES.register(bus);
        ModMenuRegistry.MENUS.register(bus);
        ModRecipeRegistry.SERIALIZERS.register(bus);

        // Register the doClientStuff method for modloading
        bus.addListener(this::clientSetup);
        bus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLCommonSetupEvent event) {
        MenuScreens.register(ModMenuRegistry.METAL_BENDER_MENU.get(), MetalBenderScreen::new);
        MenuScreens.register(ModMenuRegistry.GENERATOR_MENU.get(), GeneratorScreen::new);
        event.enqueueWork(() -> {
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
//future plans
/*
    add machine power generation
    add machine power usage
    add machine hopper input/output
    add power cable transfer

 */
//future machines
/*
    mechanized alveary
        a nest for robotic drone bees that polinate neerby flowers, trees, and crops to mutate them and produce new plants
        used to make new plants
            sticky bamboo to get resin and sticks
            jute/bulrush to get fiber, string alternateive and possible high teir cable insulator
            ironberry bush to get tiny piles of iron dust
            two tall flowers for white, green, blue, black, and brown dyes
            rubber trees + spruce to get maple syrup
        upgrades increse chance of mutations to occur per drone trip
        mechanics idea? not final
            sends out a virtual bee that goes to compatable pollen blocks in range
            when landing, it has a chance to gather a pollen from that block, and a chance to attempt a mutation if it has a compatable block in its pollen

      cooler igneus extruder
      pahoe lava

      solder recipes that require putting solder alloy in a machine with crafting slots that get soldered together, high teir circuits/components?

      get rare earth metals that come from space by finding geode like meteor stone deposits and refinind them to get trace rare metals and then the rare metals themselves
      as a companion feature to this, an asteroid miner built with components that use rare earth materials
      that sends a drone into space and comes back damaged with high density ore and meteor stone

 */




