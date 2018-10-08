package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class HarukaAxe extends ItemAxe {
    private static final String NAME = "haruka_axe";

    public HarukaAxe() {
        super(ToolMaterial.DIAMOND);

        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, NAME));
        setUnlocalizedName(SnowStepUtils.MODID + "." + NAME);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
    }

    public void process(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    private static int getFortuneLevel(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return 0;
        }
        NBTTagCompound tag = stack.getTagCompound();
        if (!tag.hasKey("ench", 9)) {
            return 0;
        }
        NBTTagList enchantments = (NBTTagList) tag.getTag("ench");
        for (NBTBase enchTag : enchantments) {
            if (enchTag.getId() != 10) {
                continue;
            }
            NBTTagCompound ench = (NBTTagCompound) enchTag;
            if (ench.hasKey("id") && ench.getInteger("id") == 35) {
                return ench.getInteger("lvl");
            }
        }
        return 0;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
            EntityLivingBase entity) {
        boolean result = super.onBlockDestroyed(stack, world, state, pos, entity);
        setDamage(stack, 0);
        int fortuneLevel = getFortuneLevel(stack);
        SnowStepUtils.LOGGER.info("fortune level = " + fortuneLevel);
        for (int x = -16; x < 16; x++) {
            for (int y = -16; y < 16; y++) {
                for (int z = -16; z < 16; z++) {
                    BlockPos p = new BlockPos(entity.posX + x, entity.posY + y, entity.posZ + z);
                    IBlockState st = world.getBlockState(p);
                    Block block = st.getBlock();
                    String name = block.getRegistryName().getResourcePath();
                    if (name.equals("log")) {
                        world.destroyBlock(p, true);
                    } else if (name.equals("leaves")) {
                        world.destroyBlock(p, false);
                        block.dropBlockAsItem(world, p, st, fortuneLevel);
                    }
                }
            }
        }
        return result;
    }
}
