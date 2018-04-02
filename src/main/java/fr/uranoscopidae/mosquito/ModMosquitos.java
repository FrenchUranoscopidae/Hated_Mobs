package fr.uranoscopidae.mosquito;

import fr.uranoscopidae.mosquito.common.BlockNet;
import fr.uranoscopidae.mosquito.common.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
    public static final CreativeTabs MOSQUITOS = new CreativeTabs(MODID)
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.BAKED_POTATO);
        }
    };

    public static final Block NET = new BlockNet();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
}
