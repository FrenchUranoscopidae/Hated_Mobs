package fr.uranoscopidae.mosquito;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "mosquitos", name = "Mosquitos Mod", version = "1.0", acceptedMinecraftVersions = "1.12.2")
public class ModMosquitos
{
    public static final String MODID = "mosquitos";
    @Mod.Instance(ModMosquitos.MODID)
    public static  ModMosquitos instance;
    @SidedProxy(clientSide = "fr.uranoscopidae.mosquito.client.MosquitoClientProxy", serverSide = "fr.uranoscopidae.mosquito.server.MosquitoServerProxy")
    public static MosquitoCommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
}
