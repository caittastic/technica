package caittastic.technica.blockentity;

import caittastic.technica.item.ModItemRegistry;
import caittastic.technica.screen.MetalBenderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Random;

public class MetalBenderBlockEntity extends BlockEntity implements MenuProvider {
    //----------------------------------------------------------------------------------------------------------------//
    /* constructor */
    public MetalBenderBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityRegistry.METAL_BENDER_BLOCK_ENTITY.get(), blockPos, blockState);
        //something to do with transfering NBT data between block entity, menu, and screen, but im really not sure
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return MetalBenderBlockEntity.this.progress;
                    case 1: return MetalBenderBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: MetalBenderBlockEntity.this.progress = value; break;
                    case 1: MetalBenderBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 2; //has to match the simple container size in menu
            }
        };
    }

    //----------------------------------------------------------------------------------------------------------------//
    /* variables */
    //"is important" but i dont know why or what for
    //the size is for every number of slots in the inventory
    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty(); //no clue what this does
    protected final ContainerData data; //something to do with transfering NBT data between block entity, menu, and screen
    private int progress = 0; //how much progress has been made
    private int maxProgress = 72; //how much progress is required for a craft to complete, basically how many ticks it takes
    public static int SLOT_COUNT = 4; //the amount of slots in our inventory
    public static int INPUT_SLOT = 0;
    public static int OUTPUT_SLOT = 1;
    public static int PROGRAM_SLOT = 2;
    public static int FUEL_SLOT = 3;

    //----------------------------------------------------------------------------------------------------------------//
    /* base methods */
    @Override //this is the name that shows up in the block gui
    public Component getDisplayName() {
        return Component.literal("metal bender");
    }

    @Nullable @Override //idk what this does
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new MetalBenderMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull @Override //is something to do with helping other mods interact with my block
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override //idk what this does
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override //idk what this does
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override //saves the inventory and crafting progress data to NBT on saving the game
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("metal_bender.progress", progress);
        super.saveAdditional(tag);
    }
    
    //loads the inventory and crafting progress data from NBT on saving the game
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("metal_bender.progress");
    }

    //----------------------------------------------------------------------------------------------------------------//
    /* custom methods */
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
        for (int i = 0; i < itemHandler.getSlots(); i++) { //loops through every item in the itemhandler
            inventory.setItem(i, itemHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
        }

        Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
    }

    //gets called every tick
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, MetalBenderBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) { //if the block entity has a valid recipie
            pBlockEntity.progress++; //increse progress by 1
            setChanged(pLevel, pPos, pState); // set that the block has been updated
            if(pBlockEntity.progress > pBlockEntity.maxProgress) { //if the crafting progress is bigger than the maximum progress
                craftItem(pBlockEntity); //craft the item
            }
        } else { //if the block entity does not have a valic recipe
            pBlockEntity.progress = 0;;  //reset the crafting progress to 0
            setChanged(pLevel, pPos, pState); //set an change has happened
        }
    }

    //checks if the container contains a valid recipie
    private static boolean hasRecipe(MetalBenderBlockEntity entity) {
        boolean hasItemInFuelSlot = entity.itemHandler.getStackInSlot(FUEL_SLOT).getItem() == Items.REDSTONE;
        boolean hasItemInInputSlot = entity.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == Items.IRON_INGOT;
        boolean hasItemInProgramSlot = entity.itemHandler.getStackInSlot(PROGRAM_SLOT).getItem() == Items.FLINT_AND_STEEL;
        return hasItemInFuelSlot && hasItemInInputSlot && hasItemInProgramSlot;
    }

    private static void craftItem(MetalBenderBlockEntity entity) {
        entity.itemHandler.extractItem(INPUT_SLOT, 1, false);
        entity.itemHandler.extractItem(FUEL_SLOT, 1, false);
        entity.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(ModItemRegistry.STEEL_INGOT.get(), entity.itemHandler.getStackInSlot(3).getCount() + 1));
    }
}
