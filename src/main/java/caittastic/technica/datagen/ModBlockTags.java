package caittastic.technica.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static caittastic.technica.Technica.MOD_ID;

public class ModBlockTags extends BlockTagsProvider {

    public ModBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, MOD_ID, helper);
    }

    @Override
    protected void addTags() {

    }

    @Override
    public String getName() {
        return "Technica Tags";
    }
}