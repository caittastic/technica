package caittastic.technica.item;

import caittastic.technica.Technica;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.technica.ModGenericRegistry.TECHNICA_TAB;

public class ModItemRegistry {
    /* item stuff */
    //item registry
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Technica.MOD_ID);
    //registering items
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
}
