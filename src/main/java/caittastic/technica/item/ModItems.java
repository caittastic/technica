package caittastic.technica.item;

import caittastic.technica.Technica;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.technica.ModGenericRegistry.TECHNICA_TAB;

public class ModItems {
    //item registry
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Technica.MOD_ID);
    //registering items
    /* tools */
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new Item(new Item.Properties().tab(TECHNICA_TAB).durability(80)));
    public static final RegistryObject<Item> CUTTERS = ITEMS.register("cutters", () -> new Item(new Item.Properties().tab(TECHNICA_TAB).durability(80)));
    public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver", () -> new Item(new Item.Properties().tab(TECHNICA_TAB).durability(80)));

    /* resources */
    public static final RegistryObject<Item> LATEX_CLUMP = ITEMS.register("latex_clump", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> LATEX_BOTTLE = ITEMS.register("latex_bottle", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    /* metals */
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> TIN_PLATE = ITEMS.register("tin_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> TIN_CASING = ITEMS.register("tin_casing", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> TIN_WIRE = ITEMS.register("tin_wire", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> BRONZE_PLATE = ITEMS.register("bronze_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    //cooked
    public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));

    //crafted
    public static final RegistryObject<Item> BASIC_CIRCUIT = ITEMS.register("basic_circuit", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> FUSE = ITEMS.register("fuse", () -> new Item(new Item.Properties().tab(TECHNICA_TAB)));
    public static final RegistryObject<Item> LV_BATTERY = ITEMS.register("lv_battery", () -> new Battery(new Item.Properties().tab(TECHNICA_TAB)));
}
