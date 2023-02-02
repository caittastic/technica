package caittastic.technica.blockentity;

import caittastic.technica.block.MetalBenderBlock;
import caittastic.technica.block.ModBlocks;
import caittastic.technica.item.ModItems;
import caittastic.technica.recipe.MetalBenderRecipe;
import caittastic.technica.menuScreen.MetalBenderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class MetalBenderBlockEntity extends BlockEntity implements MenuProvider {
    //-------------------------------------------------------constructor-------------------------------------------------------//
    public MetalBenderBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityRegistry.METAL_BENDER_BLOCK_ENTITY.get(), blockPos, blockState);
        //something to do with transfering NBT data between block entity, menu, and screen, but im really not sure
        //creates a new containerData
        //passes in progress in index 0
        //passes in max progress in index 1
        //count is how many variables are being saved
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

    //-------------------------------------------------------variables-------------------------------------------------------//
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
    private int maxProgress = 200; //how much progress is required for a craft to complete, basically how many ticks it takes
    public static int SLOT_COUNT = 4; //the amount of slots in our inventory
    public static int INPUT_SLOT = 0;
    public static int OUTPUT_SLOT = 1;
    public static int PROGRAM_SLOT = 2;
    public static int FUEL_SLOT = 3;
    private boolean isCrafting() {
        return this.progress > 0;
    }

    //-------------------------------------------------------base methods-------------------------------------------------------//
    @Override //this is the name that shows up in the block gui
    public Component getDisplayName() {
        return Component.translatable(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.METAL_BENDER.get())).toLanguageKey());
    }

    @Nullable @Override //idk what this does
    public AbstractContainerMenu createMenu(int containerID, Inventory inventory, Player player) {
        return new MetalBenderMenu(containerID, inventory, this, this.data);
    }

    @Nonnull @Override //is something to do with helping other mods interact with my block
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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

    //-------------------------------------------------------custom methods-------------------------------------------------------//
    //handles dropping the block and its inventory on breaking 
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
        for (int i = 0; i < itemHandler.getSlots(); i++) { //loops through every item in the itemhandler
            inventory.setItem(i, itemHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
        }

        Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
    }

    //gets called every tick from the block class
    public static void tick(Level level, BlockPos blockPos, BlockState state, MetalBenderBlockEntity blockEntity) {
        if (blockEntity.isCrafting()){

        }
        if(hasRecipe(blockEntity)) { //if the container contains a valid craftable recipe
            blockEntity.progress++; //increment crafting progress
            setChanged(level, blockPos, state); //set that the block has been updated
            if(blockEntity.progress >= blockEntity.maxProgress) { //if the progress is greater than or equal to the maximum progress
                craftItem(blockEntity); //do the craft
                blockEntity.resetProgress(); //set the progress to 0
            }
        } else { //if the container does not contain a valid item
            blockEntity.resetProgress(); //set the progress to 0
            setChanged(level, blockPos, state); //set that the block has been updated
        }
    }

    //checks if the container has a recipe
    private static boolean hasRecipe(MetalBenderBlockEntity entity) {
        Level level = entity.level; //gets the level that the entity is in
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots()); //creates a simple container that has the same amount of slots as our item handler

        //fills in a simplecontainer with all of the containers slots
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) { //makes a loop of the amount of slots in our item handler
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i)); //puts every item from our itemhandler into the simplecontainer at their matching indexes
        }

        //asks the recipe manager if their is a recipe present in the container
        Optional<MetalBenderRecipe> match = level.getRecipeManager().getRecipeFor(MetalBenderRecipe.Type.INSTANCE, inventory, level); //checks


        return  match.isPresent() //if the container contains a valid recipe
                && canInsertAmountIntoOutputSlot(inventory)  // and and the amount of the result item can be inserted into the output slot
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())  // and the result item can be inserted into the output slot
                && hasCorrectItemInFuelSlot(entity) //and the container has the correct item in fuel slot
                && hasToolsInToolSlot(entity); //and the container has the correct item in tool slot
                //return true, the container has a recipe
    }

    //checks if the container can craft an item
    private static void craftItem(MetalBenderBlockEntity entity) {
        Level level = entity.level; //get the level of our block entity
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots()); //creates a simple container that has the same amount of slots as our item handler
        //fills in a simplecontainer with all of the containers slots
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) { //makes a loop of the amount of slots in our item handler
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i)); //puts every item from our itemhandler into the simplecontainer at their matching indexes
        }

        Optional<MetalBenderRecipe> match = level.getRecipeManager().getRecipeFor(MetalBenderRecipe.Type.INSTANCE, inventory, level); //asks the recipe manager if there is a valid recipe in the container

        if(match.isPresent()) { //if the container has a valid recipe present
            entity.itemHandler.extractItem(INPUT_SLOT,1, false); //removes 1 from the input slot
            entity.itemHandler.extractItem(FUEL_SLOT,1, false); //removes 1 from the fuel slot

            //increses how many items are in the output slot, will this have an issue with doing multiple recipes?
            entity.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + 1));

            entity.resetProgress(); //resets crafting progress
        }
    }

    //checks if the container has correct item in the fuel slot
    private static boolean hasCorrectItemInFuelSlot(MetalBenderBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(FUEL_SLOT).getItem() == Items.REDSTONE;
    }

    //checks if the container has tool in program slot
    private static boolean hasToolsInToolSlot(MetalBenderBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(PROGRAM_SLOT).getItem() == ModItems.HAMMER.get();
    }

    //resets the crafting progress
    private void resetProgress() {
        this.progress = 0;
    }

    //checks if an item can be inserted into the output slot
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(OUTPUT_SLOT).getItem() == output.getItem() || inventory.getItem(OUTPUT_SLOT).isEmpty();
    }

    //checks if the amount of items being crafted can be inserted into the output slot
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(OUTPUT_SLOT).getMaxStackSize() > inventory.getItem(OUTPUT_SLOT).getCount();
    }
}
