package caittastic.technica.recipe;

import caittastic.technica.Technica;
import caittastic.technica.block.MetalBenderBlock;
import caittastic.technica.blockentity.MetalBenderBlockEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MetalBenderRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public MetalBenderRecipe(ResourceLocation id, ItemStack output,
                             NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItems.get(0).test(pContainer.getItem(MetalBenderBlockEntity.INPUT_SLOT));
        //recipeItems.get(0) will return the first item in the ingredients list of the json file
        //tests if the item in the input index matches with it
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MetalBenderRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "metal_bending";
    }

    public static class Serializer implements RecipeSerializer<MetalBenderRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Technica.MOD_ID,"metal_bending");

        @Override //reads the json file
        public MetalBenderRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output")); //gets an itemstack from the json object listed as "output"

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients"); //gets a json array from the json array labeled "ingredients"
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY); //size is the amount of ingredients in our recipe

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new MetalBenderRecipe(id, output, inputs);
        }

        @Override //gets the recipe "from the network"
        public MetalBenderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new MetalBenderRecipe(id, output, inputs);
        }

        @Override //writes the recipe "to the network"
        public void toNetwork(FriendlyByteBuf buf, MetalBenderRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}