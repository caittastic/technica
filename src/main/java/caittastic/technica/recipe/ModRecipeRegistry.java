package caittastic.technica.recipe;

import caittastic.technica.Technica;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Technica.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MetalBenderRecipe>> METAL_BENDER_RECIPE_SERIALZER =
            SERIALIZERS.register("metal_bending", () -> MetalBenderRecipe.Serializer.INSTANCE);
}
