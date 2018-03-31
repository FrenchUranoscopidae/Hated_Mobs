package fr.uranoscopidae.mosquito.client;

import fr.uranoscopidae.mosquito.MosquitoCommonProxy;

import java.io.File;

public class MosquitoClientProxy extends MosquitoCommonProxy
{
    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        System.out.println("pre init cote client");
    }

    @Override
    public void init()
    {
        super.init();
    }
}
