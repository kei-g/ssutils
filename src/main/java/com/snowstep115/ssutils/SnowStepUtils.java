package com.snowstep115.ssutils;

import com.snowstep115.ssutils.command.CommandGetNBT;
import com.snowstep115.ssutils.command.CommandSetNBT;
import com.snowstep115.ssutils.command.CommandUnsetNBT;
import com.snowstep115.ssutils.proxy.CommonProxy;
import com.snowstep115.ssutils.world.SnowWorldProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SnowStepUtils.MODID, name = SnowStepUtils.NAME, version = SnowStepUtils.VERSION)
public class SnowStepUtils {
    public static final String MODID = "ssutils";
    public static final String NAME = "SnowStep's Utilities";
    public static final String VERSION = "1.0.0";

    public static final String CLIENT_PROXY = "com.snowstep115.ssutils.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.snowstep115.ssutils.proxy.ServerProxy";

    public static DimensionType SNOW_DIMENSION;

    public static final CreativeTabs CREATIVE_TAB = new SnowStepUtilsTab();

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static CommonProxy PROXY;

    @Mod.Instance
    public static SnowStepUtils INSTANCE;

    public static Logger LOGGER;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        PROXY.preInit(event);
        int dimensionId = DimensionManager.getNextFreeDimId();
        SNOW_DIMENSION = DimensionType.register("Snow World", "_snow", dimensionId, SnowWorldProvider.class, false);
        DimensionManager.registerDimension(SNOW_DIMENSION.getId(), SNOW_DIMENSION);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGetNBT());
        event.registerServerCommand(new CommandSetNBT());
        event.registerServerCommand(new CommandUnsetNBT());
    }
}
