package com.criscky.armourbeyond.setup.blocks;

import com.criscky.armourbeyond.setup.tileentities.InjectorTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class Injector extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(11, 8, 8, 13, 12, 10),
            Block.box(2, 7, 2, 14, 8, 14),
            Block.box(7, 14, 7, 9, 16, 9),
            Block.box(7, 8, 7, 9, 12, 9),
            Block.box(7, 8, 11, 9, 12, 13),
            Block.box(3, 8, 8, 5, 12, 10),
            Block.box(4, 8, 4, 6, 12, 6),
            Block.box(10, 8, 4, 12, 12, 6),
            Block.box(0, 0, 0, 16, 7, 16)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SHAPE_N =Stream.of(
            Block.box(3, 8, 6, 5, 12, 8),
            Block.box(2, 7, 2, 14, 8, 14),
            Block.box(7, 14, 7, 9, 16, 9),
            Block.box(7, 8, 7, 9, 12, 9),
            Block.box(7, 8, 3, 9, 12, 5),
            Block.box(11, 8, 6, 13, 12, 8),
            Block.box(10, 8, 10, 12, 12, 12),
            Block.box(4, 8, 10, 6, 12, 12),
            Block.box(0, 0, 0, 16, 7, 16)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SHAPE_E =Stream.of(
            Block.box(8, 8, 3, 10, 12, 5),
            Block.box(2, 7, 2, 14, 8, 14),
            Block.box(7, 14, 7, 9, 16, 9),
            Block.box(7, 8, 7, 9, 12, 9),
            Block.box(11, 8, 7, 13, 12, 9),
            Block.box(8, 8, 11, 10, 12, 13),
            Block.box(4, 8, 10, 6, 12, 12),
            Block.box(4, 8, 4, 6, 12, 6),
            Block.box(0, 0, 0, 16, 7, 16)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SHAPE_W =Stream.of(
            Block.box(6, 8, 11, 8, 12, 13),
            Block.box(2, 7, 2, 14, 8, 14),
            Block.box(7, 14, 7, 9, 16, 9),
            Block.box(7, 8, 7, 9, 12, 9),
            Block.box(3, 8, 7, 5, 12, 9),
            Block.box(6, 8, 3, 8, 12, 5),
            Block.box(10, 8, 4, 12, 12, 6),
            Block.box(10, 8, 10, 12, 12, 12),
            Block.box(0, 0, 0, 16, 7, 16)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        switch (pState.getValue(FACING)) {
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPE_N;
        }
    }

    public Injector() {
        super(AbstractBlock.Properties.of(Material.CLAY));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new InjectorTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropContents(world, pos, (IInventory) tileEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }



    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        this.interactWith(world, pos, player);
        return ActionResultType.CONSUME;
    }

    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof InjectorTileEntity && player instanceof ServerPlayerEntity) {
            InjectorTileEntity te = (InjectorTileEntity) tileEntity;
            NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
        }
    }


    @Override
    public void animateTick(BlockState pState, World pLevel, BlockPos pPos, Random pRand) {
        if(pLevel.getBlockEntity(pPos) instanceof InjectorTileEntity){
            InjectorTileEntity tileEntity1 = (InjectorTileEntity) pLevel.getBlockEntity(pPos);
            if(tileEntity1.GetFields().get(0)>0){
                //float chance = 0.75f;
                //if(chance < pRand.nextFloat()) {
                    pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pPos.getX() + pRand.nextDouble(),
                            pPos.getY() + 0.5D, pPos.getZ() + pRand.nextDouble(),
                            0d,0.05d,0d);
                //}
            }
        }

        super.animateTick(pState, pLevel, pPos, pRand);
    }
}
