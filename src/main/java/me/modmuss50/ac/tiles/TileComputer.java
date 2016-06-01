package me.modmuss50.ac.tiles;

import net.minecraft.util.EnumFacing;
import reborncore.api.power.EnumPowerTier;
import reborncore.common.powerSystem.TilePowerAcceptor;

/**
 * Created by Mark on 01/06/2016.
 */
public class TileComputer extends TilePowerAcceptor {
    public TileComputer() {
        super(1);
    }

    @Override
    public double getMaxPower() {
        return 0;
    }

    @Override
    public boolean canAcceptEnergy(EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canProvideEnergy(EnumFacing direction) {
        return false;
    }

    @Override
    public double getMaxOutput() {
        return 0;
    }

    @Override
    public double getMaxInput() {
        return 0;
    }

    @Override
    public EnumPowerTier getTier() {
        return null;
    }
}
