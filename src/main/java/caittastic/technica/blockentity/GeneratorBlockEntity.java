package caittastic.technica.blockentity;

import caittastic.technica.block.ModBlocks;
import caittastic.technica.menuScreen.GeneratorMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GeneratorBlockEntity extends BlockEntity implements MenuProvider {
    public static final int DATA_CONTAINER_COUNT = 3;

    //constructor
    public GeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityRegistry.GENERATOR_BLOCK_ENTITY.get(), blockPos, blockState);
        //something to do with transfering NBT data between block entity, menu, and screen, but im really not sure
        //creates a new containerData
        //passes in progress in index 0
        //passes in max progress in index 1
        //count is how many variables are being saved
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case burnTimeCase -> GeneratorBlockEntity.this.burnTime;
                    case maxBurnTimeCase -> GeneratorBlockEntity.this.maxBurnTime;
                    case 2 -> GeneratorBlockEntity.this.energy;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case burnTimeCase -> GeneratorBlockEntity.this.burnTime = value;
                    case maxBurnTimeCase -> GeneratorBlockEntity.this.maxBurnTime = value;
                    case 2 -> GeneratorBlockEntity.this.energy = value;
                }
            }

            public int getCount() {
                return DATA_CONTAINER_COUNT; //has to match the simple container size in menu
            }
        };
    }

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(SLOT_COUNT){ @Override protected void onContentsChanged(int slot){setChanged();}}; //inventory of our container
    public String inventoryKey = "inventory"; //nbt key for inventory

    private int burnTime = 0; //amount of ticks the machine has been burning
    public String burnTimeKey = "generator.burn_time"; // nbt key for burn time
    public static final int burnTimeCase = 0; //container data case for burn time

    private int maxBurnTime = 0; //ticks for one operation to complete
    public String maxBurnTimeKey = "generator.max_burn_time"; // nbt key for burn time
    public static final int maxBurnTimeCase = 1; //container data case for max progress

    private int energy = 0; //energy stored in machine
    public String energyKey = "generator.energy"; //nbt key for energy
    public static final int energyCase = 2; //container data case for burn time

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty(); //no clue what this does
    protected final ContainerData data; //something to do with transfering NBT data between block entity, menu, and screen
    public static int MAX_ENERGY_STORE = 25600; //total storable energy
    public static int SLOT_COUNT = 2; //the amount of slots in our inventory
    public static int FUEL_SLOT = 0; //the slot that fuel goes in
    public static int CHARGING_SLOT = 1; //the slot that items go in to get powered up

    @Override //saves the inventory and crafting progress data to NBT on saving the game
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put(inventoryKey, itemStackHandler.serializeNBT());
        tag.putInt(burnTimeKey, burnTime);
        tag.putInt(maxBurnTimeKey, maxBurnTime);
        tag.putInt(energyKey, energy);
        super.saveAdditional(tag);
    }

    //loads the inventory and crafting progress data from NBT on saving the game
    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound(inventoryKey));
        burnTime = nbt.getInt(burnTimeKey);
        maxBurnTime = nbt.getInt(maxBurnTimeKey);
        energy = nbt.getInt(energyKey);
    }

    @Nullable @Override //attatches a menu type to our block entity, and creates a new one if needed
    public AbstractContainerMenu createMenu(int containerID, Inventory inventory, Player player) {
        return new GeneratorMenu(containerID, inventory, this, this.data);
    }

    @Override //this is the name that shows up in the block gui
    public @NotNull Component getDisplayName() {
        return Component.translatable(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ModBlocks.GENERATOR.get())).toLanguageKey());
    }

    @Nonnull @Override //is something to do with helping other mods interact with my block
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override //code that gets run when our block is loaded?
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override //idk what this does
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    //-------------------------------------------------------custom methods-------------------------------------------------------//
    //handles dropping the block and its inventory on breaking
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
        for (int i = 0; i < itemStackHandler.getSlots(); i++) { //loops through every item in the itemhandler
            inventory.setItem(i, itemStackHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
    }

    //is the machine crafting
    private boolean isCrafting() {
        return this.burnTime > 0;
    }

    //gets called every tick from the block class
    public static void tick(Level level, BlockPos blockPos, BlockState state, GeneratorBlockEntity entity) {
        int energyPerTick = 4; //energy output per tick
        float totalEnergyOfItem = 1600*4; //get total energy provided by recipe input item
        int totalBurnTime = (int) (totalEnergyOfItem / energyPerTick); //get total burn time from total energy and energy per tick
        Item recipeItem = Items.COAL; //get recipe item

        //if has fuel in fuel slot && if burn time is <=0
        if(entity.itemStackHandler.getStackInSlot(FUEL_SLOT).getItem() == recipeItem && entity.burnTime <= 0){
            entity.burnTime = totalBurnTime; //insert burn time of recipe into burn time
            entity.maxBurnTime = totalBurnTime;
            entity.itemStackHandler.extractItem(FUEL_SLOT, 1, false); //remove 1 from fuel slot
        }
        //if burn time > 0 && current energy + e/t <= energy store cap
        if(entity.burnTime > 0 && entity.energy + energyPerTick <= entity.MAX_ENERGY_STORE){
            entity.burnTime -= 1; //decrement burn time
            entity.energy += energyPerTick; //add to energy by energy per tick
        }
    }
}
