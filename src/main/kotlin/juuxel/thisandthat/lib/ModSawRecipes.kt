package juuxel.thisandthat.lib

import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.SawRecipes
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ModSawRecipes {
    fun init() {
        // Platforms
        for (variant in BlockVariant.Wood.values()) {
            SawRecipes.register(
                SawRecipes.Recipe(
                    { it.block == Registry.BLOCK[Identifier("thisandthat:${variant.contentName}_platform")] },
                    { listOf(ItemStack(post(variant), 2), ItemStack(platform(variant), 4)) }
                )
            )
        }
        // Posts
        for (variant in BlockVariant.Wood.values()) {
            SawRecipes.register(
                SawRecipes.Recipe(
                    { it.block == Registry.BLOCK[Identifier("thisandthat:${variant.contentName}_post")] },
                    { listOf(ItemStack(post(variant), 2)) }
                )
            )
        }
    }

    private fun post(variant: BlockVariant): Item =
        Registry.ITEM[Identifier("thisandthat:${variant.contentName}_post_multipart")]

    private fun platform(variant: BlockVariant): Item =
        Registry.ITEM[Identifier("thisandthat:${variant.contentName}_platform_multipart")]
}
