package caittastic.technica.datagen.models;

import caittastic.technica.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static caittastic.technica.Technica.MOD_ID;

public class ModBlockStatesAndModels extends BlockStateProvider {
    public ModBlockStatesAndModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(ModBlocks.INSULATED_COPPER_WIRE.get());
    }
}

