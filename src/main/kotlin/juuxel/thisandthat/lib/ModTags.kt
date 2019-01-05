package juuxel.thisandthat.lib

import net.fabricmc.fabric.tags.TagRegistry
import net.minecraft.tag.FluidTags
import net.minecraft.util.Identifier

object ModTags {
    /**
     * The block itself doesn't burn, but prismarine fire doesn't burn out on top of it.
     */
    val burnsUnderwater = TagRegistry.block(Identifier("thisandthat:burns_underwater"))
    val canContainWetFire = FluidTags.class_3487(Identifier("thisandthat:can_contain_wet_fire"))
    val fire = TagRegistry.block(Identifier("thisandthat:fire"))

    // To ensure that the object is loaded.
    fun init() {}
}
