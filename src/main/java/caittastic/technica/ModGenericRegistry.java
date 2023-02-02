package caittastic.technica;

import caittastic.technica.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModGenericRegistry {
    /* creative mode tab stuff */
    public static final CreativeModeTab TECHNICA_TAB = new CreativeModeTab("technica_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.STEEL_INGOT.get());
        }
    };
}
