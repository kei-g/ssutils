package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.BlockSnowChest;
import com.snowstep115.ssutils.container.ContainerSnowChest;
import com.snowstep115.ssutils.inventory.InventorySnowChest;
import com.snowstep115.ssutils.network.GuiHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSnowChest extends ItemBlockGeneric<BlockSnowChest> {
    public ItemSnowChest()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("snowchest", BlockSnowChest.class);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack snowchest, World world, List<String> tooltip, ITooltipFlag advanced) {
        if (!snowchest.hasTagCompound()) {
            return;
        }
        NBTTagCompound compound = snowchest.getTagCompound();
        if (!compound.hasKey("items", NBT.TAG_LIST)) {
            return;
        }
        NBTTagList itemsTag = compound.getTagList("items", NBT.TAG_COMPOUND);
        int tagCount = itemsTag.tagCount();
        for (int i = 0, j = 0; i < tagCount && j < 5; i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            if (stack.isEmpty()) {
                continue;
            }
            tooltip.add(String.format("%s x%d", stack.getDisplayName(), stack.getCount()));
            j++;
        }
        int actualCount = 0;
        for (int i = 0; i < tagCount; i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            if (!stack.isEmpty()) {
                actualCount++;
            }
        }
        if (5 < actualCount) {
            tooltip.add(I18n.format("tooltip.container.itemstack.remaining", actualCount - 5));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack snowchest = player.getHeldItem(hand);
        player.openContainer = new ContainerSnowChest(new InventorySnowChest(snowchest), player.inventory, hand);
        player.openGui(SnowStepUtils.INSTANCE, GuiHandler.OPEN_GUI_SNOWCHEST_ID, world, 0, 0, 0);
        return ActionResult.newResult(EnumActionResult.SUCCESS, snowchest);
    }
}
