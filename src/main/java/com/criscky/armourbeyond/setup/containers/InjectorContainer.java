package com.criscky.armourbeyond.setup.containers;

import com.criscky.armourbeyond.setup.ModContainerTypes;
import com.criscky.armourbeyond.setup.tileentities.InjectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

import javax.annotation.Nullable;

public class InjectorContainer extends Container {
    private final IInventory inventory;
    private IIntArray fields;


    public InjectorContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, new InjectorTileEntity(), new IntArray(buffer.readByte()));
    }

    public InjectorContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields) {
        super(ModContainerTypes.INJECTOR.get(), id);
        this.inventory = inventory;
        this.fields = fields;


        this.addSlot(new Slot(this.inventory, 0, 44, 29));
        this.addSlot(new Slot(this.inventory, 1, 44, 5));
        this.addSlot(new Slot(this.inventory, 2, 72, 25));
        this.addSlot(new Slot(this.inventory, 3, 58, 53));
        this.addSlot(new Slot(this.inventory, 4, 30, 53));
        this.addSlot(new Slot(this.inventory, 5, 16, 25));
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
    public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
        //return super.quickMoveStack(pPlayer, pIndex);
        return ItemStack.EMPTY;
    }

    public int getProgressArrowScale() {
        int progress = fields.get(0);
        if (progress > 0) {
            return progress * 24 / InjectorTileEntity.WORK_TIME;
        }
        return 0;
    }
}
