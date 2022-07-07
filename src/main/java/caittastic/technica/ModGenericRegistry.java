package caittastic.technica;

import caittastic.technica.item.ModItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModGenericRegistry {
    /* creative mode tab stuff */
    public static final CreativeModeTab TECHNICA_TAB = new CreativeModeTab("technica_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItemRegistry.STEEL_INGOT.get());
        }
    };
}
