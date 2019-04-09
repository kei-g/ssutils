package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import java.util.Random;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Yukiho extends ItemPickaxe {
    public static final int FORTUNE_LEVEL = 3;
    public static final String NAME = "yukiho";

    public Yukiho() {
        super(ToolMaterial.DIAMOND);

        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, NAME));
        setUnlocalizedName(SnowStepUtils.MODID + "." + NAME);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
    }

    public void process(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @SideOnly(Side.CLIENT)
    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null) {
            try {
                NBTTagCompound tag = tile.serializeNBT();
                String msg = String.format("[%s] %s", worldIn.isRemote ? "REMOTE" : "LOCAL", tag);
                player.sendStatusMessage(new TextComponentString(msg), false);
            } catch (Throwable exception) {
                String msg = exception.toString();
                player.sendStatusMessage(new TextComponentString(msg), false);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            world.getPlayers(EntityPlayer.class, (p) -> {
                player.sendStatusMessage(new TextComponentString(p.getName()), false);
                return true;
            });
            ChunkPos pos = new ChunkPos(player.getPosition());
            Random rnd = new Random(world.getSeed() + (long) (pos.x * pos.x * 0x4c1906) + (long) (pos.x * 0x5ac0db)
                    + (long) (pos.z * pos.z) * 0x4307a7L + (long) (pos.z * 0x5f24f) ^ 0x3ad8025f);
            if (rnd.nextInt(10) == 0) {
                player.sendStatusMessage(new TextComponentString("スライムチャンクです"), false);
            } else {
                player.sendStatusMessage(new TextComponentString("スライムチャンクではありません"), false);
            }
        }
        ItemStack item = player.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}
