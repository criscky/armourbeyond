package com.criscky.armourbeyond.setup.containers;

import com.criscky.armourbeyond.setup.ModContainerTypes;
import com.criscky.armourbeyond.setup.tileentities.InjectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;

import java.util.Objects;

public class InjectorContainer extends Container {
    private final IInventory inventory;
    private IIntArray fields;
    protected final InjectorTileEntity tileEntity;


    public InjectorContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        //this(id, playerInventory, new InjectorTileEntity(), new IntArray(buffer.readByte()));
        this(id, playerInventory, getTileEntity(playerInventory, buffer));
    }

    public InjectorContainer(int id, PlayerInventory playerInventory, InjectorTileEntity tileentity/*IInventory inventory, IIntArray fields*/) {
        super(ModContainerTypes.INJECTOR.get(), id);
        this.inventory = (IInventory)tileentity;
        //this.fields = fields;
        this.fields = tileentity.GetFields();
        this.tileEntity = tileentity;
        this.addDataSlots(fields);


        this.addSlot(new Slot(this.inventory, 0, 44, 29){@Override public int getMaxStackSize(){return 1;}});
        this.addSlot(new Slot(this.inventory, 1, 44, 5){@Override public int getMaxStackSize(){return 1;}});
        this.addSlot(new Slot(this.inventory, 2, 72, 25){@Override public int getMaxStackSize(){return 1;}});
        this.addSlot(new Slot(this.inventory, 3, 58, 53){@Override public int getMaxStackSize(){return 1;}});
        this.addSlot(new Slot(this.inventory, 4, 30, 53){@Override public int getMaxStackSize(){return 1;}});
        this.addSlot(new Slot(this.inventory, 5, 16, 25){@Override public int getMaxStackSize(){return 1;}});
        this.addSlot(new Slot(this.inventory, 6, 126, 29) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 7 + col * 18, 165 - (4 - row) * 18 - 10));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 7 + col * 18, 141));
        }
    }


    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return this.inventory.stillValid(pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = this.tileEntity.getContainerSize();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == inventorySize - 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= inventorySize) {
                if (!this.moveItemStackTo(itemstack1, 0, inventorySize - 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;

        //return ItemStack.EMPTY;
    }

    public int getProgressArrowScale() {

        int progress = fields.get(0);
        if (progress > 0) {
            return (int) Math.floor(progress * 24 / InjectorTileEntity.WORK_TIME);
        }
        return 0;
    }




    private static InjectorTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (te instanceof InjectorTileEntity) {
            return (InjectorTileEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }
}
