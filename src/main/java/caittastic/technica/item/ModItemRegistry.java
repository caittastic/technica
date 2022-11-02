package caittastic.technica.item;

import caittastic.technica.Technica;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.technica.ModGenericRegistry.TECHNICA_TAB;

public class ModItemRegistry {
    //item registry
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Technica.MOD_ID);
    //registering items
    /* tools */
    public static final RegistryObject<Item> HAMMER = ITEMS.register("forge_hammer", () -> new Item(new Item.Properties().tab(TECHNICA_TAB).durability(80)));
    public static final RegistryObject<Item> CUTTERS = ITEMS.register("cutters", () -> new Item(new Item.Properties().tab(TECHNICA_TAB).durability(80)));
    /* resources */
    public static final RegistryObject<Item> LATEX_CLUMP = ITEMS.register("latex_clump", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> LATEX_BOTTLE = ITEMS.register("latex_bottle", () -> new LatexBottleItem(new Item.Properties().tab(TECHNICA_TAB)));
    /* components */
    //cooked
    public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    //shaped
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    //crafted
    public static final RegistryObject<Item> BASIC_CIRCUIT = ITEMS.register("basic_circuit", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> FUSE = ITEMS.register("fuse", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
}
