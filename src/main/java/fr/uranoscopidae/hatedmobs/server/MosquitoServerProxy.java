package fr.uranoscopidae.hatedmobs.server;

import fr.uranoscopidae.hatedmobs.MosquitoCommonProxy;

import java.io.File;

public class MosquitoServerProxy extends MosquitoCommonProxy
{
    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        System.out.println("pre init cote server");
    }

    @Override
    public void init()
    {
        super.init();
    }
}
