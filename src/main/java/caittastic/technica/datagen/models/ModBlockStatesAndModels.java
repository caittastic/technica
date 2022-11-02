package caittastic.technica.datagen.models;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static caittastic.technica.Technica.MOD_ID;

public class ModBlockStatesAndModels extends BlockStateProvider {
    public ModBlockStatesAndModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}

