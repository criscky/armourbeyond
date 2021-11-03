package com.criscky.armourbeyond.setup.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;

public class Injector extends Block {
    public Injector() {
        super(AbstractBlock.Properties.of(Material.CLAY));
    }

    /*@Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }*/
}
