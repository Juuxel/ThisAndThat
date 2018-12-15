/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import juuxel.thisandthat.item.ModBlockItem
import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.ModContent
import net.minecraft.util.registry.Registry

abstract class ModRegistry {
    @Suppress("UNCHECKED_CAST")
    protected fun <R> register(registry: Registry<R>, content: ModContent<R>): ModContent<R> {
        Registry.register(registry, "thisandthat:${content.name}", content.unwrap())
        return content
    }

    protected fun <T : ModBlock> registerBlock(content: T): T {
        register(Registry.BLOCK, content)

        if (content.registerItem)
            Registry.register(Registry.ITEM, "thisandthat:${content.name}", ModBlockItem(content))

        return content
    }
}
