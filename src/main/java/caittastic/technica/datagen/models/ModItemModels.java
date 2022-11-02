package caittastic.technica.datagen.models;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.technica.Technica.MOD_ID;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    private ItemModelBuilder RegisterWithExistingParent(String name, RegistryObject<Block> block, String appendix) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).toString(), modLoc("block/" + name + appendix));
    }
}

