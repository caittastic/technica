package caittastic.technica.menuScreen;

import caittastic.technica.Technica;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Technica.MOD_ID);
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static final RegistryObject<MenuType<MetalBenderMenu>> METAL_BENDER_MENU = registerMenuType(MetalBenderMenu::new,"metal_bender_menu");
    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU = registerMenuType(GeneratorMenu::new,"generator_menu");

}
