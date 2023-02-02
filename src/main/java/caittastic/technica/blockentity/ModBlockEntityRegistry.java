package caittastic.technica.blockentity;

import caittastic.technica.Technica;
import caittastic.technica.block.GeneratorBlock;
import caittastic.technica.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Technica.MOD_ID);
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final RegistryObject<BlockEntityType<MetalBenderBlockEntity>> METAL_BENDER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("metal_bender_block_entity", () ->
            BlockEntityType.Builder.of(MetalBenderBlockEntity::new, ModBlocks.METAL_BENDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("generator_block_entity", () ->
                    BlockEntityType.Builder.of(GeneratorBlockEntity::new, ModBlocks.GENERATOR.get()).build(null));
}
