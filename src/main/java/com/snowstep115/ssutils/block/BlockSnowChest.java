package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.tileentity.TileEntitySnowChest;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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

    public BlockSnowChest() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, NAME));
        setUnlocalizedName(SnowStepUtils.MODID + "." + NAME);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
        setSoundType(SoundType.METAL);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        return true;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySnowChest();
    }
}
