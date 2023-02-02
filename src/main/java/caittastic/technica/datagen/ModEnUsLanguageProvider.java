package caittastic.technica.datagen;

import caittastic.technica.block.ModBlocks;
import caittastic.technica.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static caittastic.technica.Technica.MOD_ID;

public class ModEnUsLanguageProvider extends LanguageProvider {
    public ModEnUsLanguageProvider(DataGenerator gen, String locale) {
        super(gen, MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        /* -------------------- items -------------------- */
        add(ModItems.HAMMER.get(), "Forge Hammer");
        add(ModItems.CUTTERS.get(), "Industrial Cutters");

        add(ModItems.LATEX_CLUMP.get(), "Latex Clump");
        add(ModItems.LATEX_BOTTLE.get(), "Bottle Of Latex");
        add(ModItems.RUBBER.get(), "Rubber");

        add(ModItems.STEEL_INGOT.get(), "Steel Ingot");
        add(ModItems.STEEL_PLATE.get(), "Steel Plate");
        add(ModItems.IRON_PLATE.get(), "Iron Plate");
        add(ModItems.COPPER_PLATE.get(), "Copper Plate");
        add(ModItems.COPPER_WIRE.get(), "Copper Wire");

        add(ModItems.BASIC_CIRCUIT.get(), "Basic Circuit");
        add(ModItems.FUSE.get(), "Fuse");

        /* -------------------- blocks -------------------- */
        add(ModBlocks.METAL_BENDER.get(), "Metal Bender");
    }
}
