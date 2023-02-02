package caittastic.technica.datagen.models;

import caittastic.technica.block.ModBlocks;
import caittastic.technica.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.technica.Technica.MOD_ID;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        /* -------------------- items -------------------- */
        simpleFlatItemModel(ModItems.HAMMER, MOD_ID);
        simpleFlatItemModel(ModItems.CUTTERS, MOD_ID);
        simpleFlatItemModel(ModItems.SCREWDRIVER, MOD_ID);

        simpleFlatItemModel(ModItems.LATEX_CLUMP, MOD_ID);
        simpleFlatItemModel(ModItems.LATEX_BOTTLE, MOD_ID);
        simpleFlatItemModel(ModItems.RUBBER, MOD_ID);

        simpleFlatItemModel(ModItems.STEEL_INGOT, MOD_ID);
        simpleFlatItemModel(ModItems.STEEL_PLATE, MOD_ID);

        simpleFlatItemModel(ModItems.TIN_INGOT, MOD_ID);
        simpleFlatItemModel(ModItems.TIN_PLATE, MOD_ID);
        simpleFlatItemModel(ModItems.TIN_CASING, MOD_ID);
        simpleFlatItemModel(ModItems.TIN_WIRE, MOD_ID);

        simpleFlatItemModel(ModItems.BRONZE_INGOT, MOD_ID);
        simpleFlatItemModel(ModItems.BRONZE_PLATE, MOD_ID);

        simpleFlatItemModel(ModItems.PLATINUM_INGOT, MOD_ID);

        simpleFlatItemModel(ModItems.GALLIUM, MOD_ID);

        simpleFlatItemModel(ModItems.IRON_PLATE, MOD_ID);
        simpleFlatItemModel(ModItems.COPPER_PLATE, MOD_ID);
        simpleFlatItemModel(ModItems.COPPER_WIRE, MOD_ID);

        simpleFlatItemModel(ModItems.BASIC_CIRCUIT, MOD_ID);
        simpleFlatItemModel(ModItems.FUSE, MOD_ID);

        /* -------------------- blocks -------------------- */
        simpleExistingBlockParent(ModBlocks.METAL_BENDER);
        flatBlockModel(ModBlocks.INSULATED_COPPER_WIRE, "block/insulated_copper_wire_item"); //IRONWOOD_SAPLING

    }

    private void simpleFlatItemModel(RegistryObject<Item> item, String modId) {
        singleTexture(item.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", new ResourceLocation(modId, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleExistingBlockParent(RegistryObject<Block> blockToModel) {
        String registryLocation = "block/" + blockToModel.getId().getPath();
        return withExistingParent(blockToModel.getId().getPath(), modLoc(registryLocation));
    }

    private void flatBlockModel(RegistryObject<Block> blockToModel, String registryLocation) {
        singleTexture(blockToModel.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc(registryLocation));
    }

}

