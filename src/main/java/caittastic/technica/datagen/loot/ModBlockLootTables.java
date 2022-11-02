package caittastic.technica.datagen.loot;

import caittastic.technica.block.ModBlockRegistry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    @Override
    protected void addTables() {

    }
}
