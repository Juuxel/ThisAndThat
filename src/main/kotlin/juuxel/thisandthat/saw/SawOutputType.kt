package juuxel.thisandthat.saw

import net.minecraft.item.Item as MCItem
import net.minecraft.item.ItemStack
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultMappedRegistry
import net.minecraft.util.registry.Registry
import java.util.concurrent.ThreadLocalRandom

/**
 * An output type for a saw recipe.
 *
 * @see getOutput
 * @see Companion
 */
interface SawOutputType {
    /**
     * Build the output using the [context].
     */
    fun getOutput(context: Context): List<ItemStack>

    /**
     * The context for building output in [getOutput].
     *
     * @property id the supplied id (for items or tag, for example)
     * @property amount the stack size per item type
     * @property properties a map containing all supplied properties
     */
    data class Context(val id: Identifier, val amount: Int, val properties: Map<String, Any>)

    object TagRandom : SawOutputType {
        override fun getOutput(context: Context): List<ItemStack> {
            return listOf(
                ItemStack(
                    ItemTags.getContainer()[context.id]?.getRandom(ThreadLocalRandom.current()) ?: return emptyList(),
                    context.amount
                )
            )
        }
    }

    object TagAll : SawOutputType {
        override fun getOutput(context: Context) =
            ItemTags.getContainer()[context.id]?.entries()?.flatMap {
                ArrayList<MCItem>().apply { it.build(this) }.map {
                    ItemStack(it, context.amount)
                }
            } ?: emptyList()
    }

    object Item : SawOutputType {
        override fun getOutput(context: Context) =
            listOf(ItemStack(Registry.ITEM[context.id], context.amount))
    }

    /**
     * The `SawOutputType` registry.
     */
    companion object : DefaultMappedRegistry<SawOutputType>("thisandthat:item") {
        init {
            register(Identifier("thisandthat:item"), Item)
            register(Identifier("thisandthat:tag_random"), TagRandom)
            register(Identifier("thisandthat:tag_all"), TagAll)
        }
    }
}
