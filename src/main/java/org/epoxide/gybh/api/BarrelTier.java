package org.epoxide.gybh.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BarrelTier implements Comparable<BarrelTier> {

    /**
     * The identifier for the tier type. Follows same format as the rest of the game.
     */
    public final ResourceLocation identifier;

    /**
     * The crafting ingredient for the tier. Can be ItemStack, Item, Block, or oredict name.
     */
    public final Object recipe;

    /**
     * The tier of the tank. Used to establish the upgrade hierarchy.
     */
    public final int tier;

    /**
     * The block state to use when rendering this tier.
     */
    public final IBlockState renderState;

    /**
     * Constructs the tank tier. Does not register it.
     *
     * @param identifier The identifier for the tier. Follows standard mc format.
     * @param renderState The block to use in tank rendering.
     * @param recipe The crafting ingredient for the tier.
     * @param tier The tier of the tank, used to establish the upgrade hierarchy.
     */
    public BarrelTier (ResourceLocation identifier, IBlockState renderState, Object recipe, int tier) {

        this.identifier = identifier;
        this.renderState = renderState;
        this.recipe = recipe;
        this.tier = tier;
    }

    /**
     * Gets the capacity for the tier. By default, a tier capacity is equal to 4^tier buckets.
     *
     * @return The capacity held by this tier.
     */
    public int getCapacity () {

        return (int) (Math.pow(this.tier, this.tier) * 32 * 64);
    }

    /**
     * Checks if a tier can be upgraded into another one.
     *
     * @param upgradeTier The new tier being applied.
     * @return Whether or not the new tier is allowed.
     */
    public boolean canApplyUpgrage (BarrelTier upgradeTier) {

        return upgradeTier.tier == this.tier + 1 || upgradeTier.tier == this.tier;
    }

    /**
     * Checks if the tier is flammable or not. A flammable tank can not accept fluids which
     * have Lava as their material. For the majority of cases all params can be null, however
     * it is best to try and provide the information.
     *
     * @param access The world access.
     * @param pos The position of the block.
     * @param face The face of the block.
     * @return Whether or not the tank is flammable.
     */
    public boolean isFlammable (IBlockAccess access, BlockPos pos, EnumFacing face) {

        try {

            return this.renderState.getBlock().isFlammable(access, pos, face);
        }

        catch (final Exception e) {

            return true;
        }
    }

    @Override
    public int compareTo (BarrelTier otherTier) {

        return otherTier.tier == this.tier ? 0 : otherTier.tier > this.tier ? -1 : +1;
    }
}
