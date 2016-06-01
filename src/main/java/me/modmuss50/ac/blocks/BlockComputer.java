package me.modmuss50.ac.blocks;

import me.modmuss50.ac.AdvancedComputers;
import me.modmuss50.ac.compiler.JavaCompiler;
import me.modmuss50.ac.tiles.TileComputer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.common.BaseTileBlock;

import javax.annotation.Nullable;
import java.io.File;

/**
 * Created by Mark on 01/06/2016.
 */
public class BlockComputer extends BaseTileBlock {

    public BlockComputer() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation("advancedcomputers", "computer"));
        setUnlocalizedName("advancedcomputers.computer");
        setCreativeTab(CreativeTabs.REDSTONE);
        setHardness(2f);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileComputer();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        if(!worldIn.isRemote){
            TileComputer computer = (TileComputer) worldIn.getTileEntity(pos);
            JavaCompiler.compileAndInject(new File(AdvancedComputers.configDir, "test.java"), AdvancedComputers.configDir, computer);
        }


        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}
