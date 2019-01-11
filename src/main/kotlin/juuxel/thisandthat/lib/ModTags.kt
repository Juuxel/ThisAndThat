/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import net.fabricmc.fabric.tags.TagRegistry
import net.minecraft.tag.FluidTags
import net.minecraft.util.Identifier

object ModTags {
    val keepsWetFireBurning = TagRegistry.block(Identifier("thisandthat", "keeps_wet_fire_burning"))
    val canContainWetFire = FluidTags.class_3487(Identifier("thisandthat", "can_contain_wet_fire"))

    // To ensure that the object is loaded.
    fun init() {}
}
