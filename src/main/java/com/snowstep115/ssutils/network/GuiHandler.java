package com.snowstep115.ssutils.network;

import com.snowstep115.ssutils.client.gui.GuiContainerSnowChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int OPEN_GUI_SNOWCHEST_ID = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        switch (id) {
        case OPEN_GUI_SNOWCHEST_ID:
            return player.openContainer;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int handId, int unused1, int unused2) {
        switch (id) {
        case OPEN_GUI_SNOWCHEST_ID:
            return new GuiContainerSnowChest(player.openContainer);
        }
        return null;
    }
}
