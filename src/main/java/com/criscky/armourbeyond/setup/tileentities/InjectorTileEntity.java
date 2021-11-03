package com.criscky.armourbeyond.setup.tileentities;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.crafting.recipes.InjectorRecipe;
import com.criscky.armourbeyond.setup.ModRecipes;
import com.criscky.armourbeyond.setup.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class InjectorTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {
    static final int WORK_TIME = 60 * 20;
    private NonNullList<ItemStack> items;
    private final LazyOptional<? extends IItemHandler>[] handlers;

    private int progress = 0;

    public InjectorTileEntity() {
        super(ModTileEntities.INJECTOR.get());
        this.handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
        this.items = NonNullList.withSize(6, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) {
            return;
        }

        InjectorRecipe recipe = getRecipe();
        if (recipe != null) {
            doWork(recipe);
        } else {
            stopWork();
        }
    }

    @Nullable
    public InjectorRecipe getRecipe() {
        if (this.level == null || getItem(0).isEmpty()) {
            return null;
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.Types.PRESSING, this, this.level).orElse(null);
    }

    private ItemStack getWorkOutput(@Nullable InjectorRecipe recipe) {
        if (recipe != null) {
            return recipe.assemble(this);
        }
        return ItemStack.EMPTY;
    }

    private void doWork(InjectorRecipe recipe) {
        assert this.level != null;

        ItemStack current = getItem(1);
        ItemStack output = getWorkOutput(recipe);

        if (!current.isEmpty()) {
            int newCount = current.getCount() + output.getCount();

            if (!ItemStack.matches(current, output) || newCount > output.getMaxStackSize()) {
                stopWork();
                return;
            }
        }

        if (progress < WORK_TIME) {
            ++progress;
        }

        if (progress >= WORK_TIME) {
            finishWork(recipe, current, output);
        }
    }

    private void stopWork() {
        progress = 0;
    }

    private void finishWork(InjectorRecipe recipe, ItemStack current, ItemStack output) {
        if (!current.isEmpty()) {
            current.grow(output.getCount());
        } else {
            setItem(5, output);
        }

        progress = 0;
        this.removeItem(0, 1);
    }


    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return pIndex == 6;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container."+ ArmourBeyond.MOD_ID +".injector");
    }

    @Override
    protected Container createMenu(int pId, PlayerInventory pPlayer) {
        /*the container i'll create later*/
        return null;
    }

    @Override
    public int getContainerSize() {
        return 6;
    }

    @Override
    public boolean isEmpty() {
        return IntStream.of(0, 1, 2, 3, 4, 5).allMatch(i -> getItem(i).isEmpty());
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return items.get(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ItemStackHelper.removeItem(items, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ItemStackHelper.takeItem(items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        items.set(pIndex, pStack);
    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return this.level != null
                && this.level.getBlockEntity(this.worldPosition) == this
                && pPlayer.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ()) <= 64;
    }

    @Override
    public void clearContent() {
        items.clear();
    }



    @Override
    public void load(BlockState state, CompoundNBT tags) {
        super.load(state, tags);
        this.items = NonNullList.withSize(2, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tags, this.items);

        this.progress = tags.getInt("Progress");
    }

    @Override
    public CompoundNBT save(CompoundNBT tags) {
        super.save(tags);
        ItemStackHelper.saveAllItems(tags, this.items);
        tags.putInt("Progress", this.progress);
        return tags;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tags = this.getUpdateTag();
        ItemStackHelper.saveAllItems(tags, this.items);
        return new SUpdateTileEntityPacket(this.worldPosition, 1, tags);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tags = super.getUpdateTag();
        tags.putInt("Progress", this.progress);
        return tags;
    }
}
