package me.modmuss50.ac;


import me.modmuss50.ac.blocks.BlockComputer;
import me.modmuss50.ac.tiles.TileComputer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod(name = "Advanced Computers", modid = "advancedcomputers")
public class AdvancedComputers {

    public static Block computer;

    public static File configDir;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        computer = new BlockComputer();
        GameRegistry.register(computer);
        GameRegistry.register(new ItemBlock(computer), computer.getRegistryName());
        GameRegistry.registerTileEntity(TileComputer.class, "TileComputer");
        configDir = new File(event.getModConfigurationDirectory(), "advancedComputers");
        if(!configDir.exists()){
         configDir.mkdir();
        }
    }


}
