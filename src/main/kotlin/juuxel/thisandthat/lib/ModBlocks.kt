package juuxel.thisandthat.lib

import io.github.juuxel.polyester.plugin.PolyesterPluginManager
import io.github.juuxel.polyester.registry.PolyesterRegistry
import juuxel.thisandthat.ThisAndThat
import juuxel.thisandthat.block.*
import juuxel.thisandthat.item.ModWallBlockItem
import juuxel.thisandthat.util.BlockVariant
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ModBlocks : PolyesterRegistry(ThisAndThat.ID) {
    val STONE_TORCH_GROUND = registerBlock(StoneTorchBlock())

    val STONE_TORCH_WALL = registerBlock(
        StoneTorchBlock.Wall(
            Block.Settings.copy(STONE_TORCH_GROUND.unwrap()).dropsLike(STONE_TORCH_GROUND.unwrap())
        )
    )

    val STONE_TORCH_ITEM = register(
        Registry.ITEM,
        ModWallBlockItem(
            STONE_TORCH_GROUND,
            STONE_TORCH_WALL,
            Item.Settings().itemGroup(ItemGroup.DECORATIONS)
        )
    )

    private val WOOD_VARIANTS =
        PolyesterPluginManager.plugins.flatMap { it.woodTypes }
            .map { BlockVariant.Wood(getVariantContentName(it.id)) }

    private val ALL_VARIANTS = sequence {
        yieldAll(WOOD_VARIANTS)
        yieldAll(BlockVariant.Stone.values().iterator())
    }.toList()

    val POSTS = ALL_VARIANTS.map {
        registerBlock(PostBlock(it))
    }

    val PLATFORMS = ALL_VARIANTS.map {
        registerBlock(PlatformBlock(it))
    }

    val STEPS = ALL_VARIANTS.map {
        registerBlock(StepBlock(it))
    }

    val CHIMNEY = registerBlock(ChimneyBlock())

    val BUBBLE_CHIMNEY = registerBlock(BubbleChimneyBlock())

    fun init() {}

    private fun getVariantContentName(variantId: Identifier): String =
        when (variantId.namespace) {
            "minecraft" -> variantId.path
            else -> variantId.namespace + '_' + variantId.path
        }
}
