package fr.uranoscopidae.hatedmobs;

import fr.uranoscopidae.hatedmobs.common.*;
import fr.uranoscopidae.hatedmobs.common.blocks.*;
import fr.uranoscopidae.hatedmobs.common.items.ItemFrogLeg;
import fr.uranoscopidae.hatedmobs.common.items.ItemSilkBoots;
import fr.uranoscopidae.hatedmobs.common.items.ItemSpiderEgg;
import fr.uranoscopidae.hatedmobs.common.items.ItemSwatter;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = HatedMobs.MODID, name = "Hated Mobs", version = "1.1.2", acceptedMinecraftVersions = "1.12.2", dependencies = "required-after:llibrary@[1.7.9,)", updateJSON = "https://raw.githubusercontent.com/FrenchUranoscopidae/Hated_Mobs/master/updateCheck.json")
public class HatedMobs
{
    public static final String MODID = "hatedmobs";
    @Mod.Instance(HatedMobs.MODID)
    public static HatedMobs instance;
    @SidedProxy(clientSide = "fr.uranoscopidae.hatedmobs.client.MosquitoClientProxy", serverSide = "fr.uranoscopidae.hatedmobs.server.MosquitoServerProxy")
    public static MosquitoCommonProxy proxy;
    public static Logger logger;
    public static final AttributeModifier modifier = new AttributeModifier(MODID + ".silk_walk", 1, 0).setSaved(false);

    public static final CreativeTabs TAB = new CreativeTabs(MODID)
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(SWATTER);
        }
    };
//truc
    public static final ItemArmor.ArmorMaterial silkBootsMaterial = EnumHelper.addArmorMaterial("silk_boots", MODID + ":silk_boots", 5, new int[]{0, 0, 0, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public static final Material WEB_MATERIAL = new Material(MapColor.SNOW);
    public static final Block NET = new BlockNet();
    public static final Item SWATTER = new ItemSwatter();
    public static final Block WEB_BLOCK = new BlockWeb();
    public static final Block EGG_SACK = new BlockEggSack();
    public static final Item SPIDER_EGG = new ItemSpiderEgg();
    public static final Block SPIDER_INFESTED_LEAVES_BLOCK = new BlockSpiderInfestedLeaves();
    public static final ItemArmor SILK_BOOTS = new ItemSilkBoots();
    public static final Potion INSOMNIA = new PotionInsomnia();
    public static final Block ANTI_MOSQUITO_GLASS = new BlockAntiMosquitoGlass();
    public static final Block NET_DOOR = new BlockNetDoor();
    public static final Item NET_DOOR_ITEM = new ItemDoor(HatedMobs.NET_DOOR).setRegistryName(NET_DOOR.getRegistryName()).setUnlocalizedName("net_door").setCreativeTab(HatedMobs.TAB);
    public static final Block WASP_NEST = new BlockWaspNest();
    public static final Item FROG_LEG = new ItemFrogLeg(1, 1.5f, false);
    public static final Item COOKED_FROG_LEG = new ItemFrogLeg(3, 1.5f, false);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GameRegistry.registerWorldGenerator(new WorldGeneratorSpiderNest(), 0);
        GameRegistry.registerWorldGenerator(new WorldGeneratorEggSack(), 0);
        GameRegistry.registerWorldGenerator(new WorldGeneratorSpiderLeaves(), 0);
        GameRegistry.registerWorldGenerator(new WorldGeneratorWaspNest(), 0);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        //FurnaceRecipes.instance().addSmelting(FROG_LEG, COOKED_FROG_LEG., 1f);
    }
}
