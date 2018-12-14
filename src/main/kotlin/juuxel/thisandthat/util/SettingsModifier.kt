/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.block.Block
import net.minecraft.sound.BlockSoundGroup
import java.lang.reflect.Method

/**
 * Booleans: if true, call parameterless function
 * Nullables: if nonnull, call function
 * Int: if not -1, call function
 */
fun Block.Settings.modify(
    sounds: BlockSoundGroup? = null,
    lightLevel: Int = -1,
    breakInstantly: Boolean = false,
    ticksRandomly: Boolean = false,
    isPistonExtension: Boolean = false,
    dropsNothing: Boolean = false
): Block.Settings = apply {
    if (sounds != null)
        getFunction("sounds", BlockSoundGroup::class.java)(this, sounds)

    if (lightLevel != -1)
        getFunction("lightLevel", PrimitiveClasses.INT)(this, lightLevel)

    if (breakInstantly)
        getFunction("breaksInstantly")(this)

    if (ticksRandomly)
        getFunction("ticksRandomly")(this)

    if (isPistonExtension)
        getFunction("isPistonExtension")(this)

    if (dropsNothing)
        getFunction("dropsNothing")(this)
}

private fun getFunction(name: String, vararg paramTypes: Class<*>): Method =
    Block.Settings::class.java.getMethod(name, *paramTypes)
