package juuxel.thisandthat.saw

import a2u.tn.utils.json.JsonParser
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceReloadListener
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ThreadLocalRandom

object SawRecipeLoader : ResourceReloadListener {
    private const val directory = "saw_recipes"
    private const val suffix = ".json5"
    private val logger = LogManager.getLogger()

    override fun onResourceReload(manager: ResourceManager) {
        manager.findResources(directory) {
            it.endsWith(suffix)
        }.map(manager::getResource).forEach {
            SawRecipes.register(
                readRecipe(JsonParser.parse(it.inputStream.bufferedReader().lineSequence().joinToString(separator = "\n")))
                    ?: run {
                        logger.error("[ThisAndThat] Could not load saw recipe ${it.id}")
                        return@forEach
                    }
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun readRecipe(map: Map<String, Any>): SawRecipe? {
        val transform = (map["output"] as? List<Any>)?.let(::readTransform) ?: return null
        val predicate = (map["predicate"] as? Map<String, Any>)?.let(::readPredicate) ?: return null

        return SawRecipe(predicate, transform)
    }

    private fun readPredicate(map: Map<String, Any>): SawPredicate? {
        val type = map["type"]?.toString() ?: return null
        val id = Identifier(map["id"]?.toString() ?: return null)

        return when (type) {
            "tag" -> {{ it.block.matches(BlockTags.getContainer()[id]) }}
            "block" -> {{ Registry.BLOCK.getId(it.block) == id }}
            else -> null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun readTransform(list: List<Any>): SawTransform? {
        val functions = list.map {
            val map = it as? Map<String, Any> ?: return@map null
            val id = Identifier(map["id"]?.toString() ?: return@map null)
            val amount = map["amount"]?.toString()?.toIntOrNull() ?: 1

            when (map["type"]?.toString() ?: return@map null) {
                "tag_all" -> {{
                    ItemTags.getContainer()[id]?.entries()?.flatMap {
                        ArrayList<Item>().apply { it.build(this) }.map {
                            ItemStack(it, amount)
                        }
                    } ?: emptyList()
                }}

                "tag_random" -> {{
                    listOf(ItemStack(ItemTags.getContainer()[id]?.getRandom(ThreadLocalRandom.current()), amount))
                }}

                "item" -> {{
                    listOf(ItemStack(Registry.ITEM[id], amount))
                }}

                // :thonk:
                else -> null as (() -> List<ItemStack>)?
            }
        }

        return {
            functions.flatMap { it?.invoke() ?: emptyList() }
        }
    }
}
