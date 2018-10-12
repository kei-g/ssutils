package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.tileentity.TileEntitySnowChest;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockSnowChest extends Block implements ITileEntityProvider {
    private static final String NAME = "snowchest";

    private final HashMap<BlockPos, NBTTagCompound> displacementsLocal = new HashMap<BlockPos, NBTTagCompound>();
    private final HashMap<BlockPos, NBTTagCompound> displacementsRemote = new HashMap<BlockPos, NBTTagCompound>();
    private final HashMap<IBlockState, ItemStack> placements = new HashMap<IBlockState, ItemStack>();

    public BlockSnowChest() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, NAME));
        setUnlocalizedName(SnowStepUtils.MODID + "." + NAME);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
        setSoundType(SoundType.METAL);
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        this.placements.put(state, placer.getHeldItem(hand));
        return state;
    }

    public TileEntity createTileEntity(World world, IBlockState state) {
        ItemStack stack = this.placements.remove(state);
        TileEntity tile = super.createTileEntity(world, state);
        SnowStepUtils.LOGGER.info(String.format("%s => %s", stack, tile));
        return tile;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        return true;
    }

    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
            boolean willHarvest) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null) {
            if (world.isRemote) {
                this.displacementsRemote.put(pos, tile.serializeNBT());
            } else {
                this.displacementsLocal.put(pos, tile.serializeNBT());
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        if ((world.isRemote && !this.displacementsRemote.containsKey(pos))
                || (!world.isRemote && !this.displacementsLocal.containsKey(pos))) {
            super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
            return;
        }
        ItemStack chest = new ItemStack(Item.getByNameOrId(getRegistryName().toString()));
        // XXX: TODO - deserialize contents of the chest from this.displacements
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), chest));
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySnowChest();
    }
}
