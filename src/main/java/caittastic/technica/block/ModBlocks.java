package caittastic.technica.block;

import caittastic.technica.ModGenericRegistry;
import caittastic.technica.Technica;
import caittastic.technica.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.function.Supplier;

public class ModBlocks {
    /* block stuff */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Technica.MOD_ID);
    //utilities
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    //registering blocks
    //metal bender
    public static final RegistryObject<Block> METAL_BENDER = registerBlock("metal_bender", () -> new MetalBenderBlock(BlockBehaviour.
            Properties.of(Material.METAL).
            lightLevel(state -> state.getValue(MetalBenderBlock.LIT) ? 5 : 0)
    ), ModGenericRegistry.TECHNICA_TAB);
    //generator
    public static final RegistryObject<Block> GENERATOR = registerBlock("generator", () -> new GeneratorBlock(BlockBehaviour.
            Properties.of(Material.METAL).
            lightLevel(state -> state.getValue(GeneratorBlock.LIT) ? 5 : 0)
    ), ModGenericRegistry.TECHNICA_TAB);
    //wire
    public static final RegistryObject<Block> INSULATED_COPPER_WIRE = registerBlock("insulated_copper_wire", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(0.4f).sound(SoundType.WOOL).noOcclusion()), ModGenericRegistry.TECHNICA_TAB);

}
