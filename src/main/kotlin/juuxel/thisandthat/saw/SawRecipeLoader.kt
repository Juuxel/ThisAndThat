/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.saw

import com.mojang.brigadier.StringReader
import juuxel.jay.JsonObject
import juuxel.jay.Parser
import juuxel.thisandthat.util.StateBlockProxy
import net.minecraft.block.pattern.BlockProxy
import net.minecraft.command.arguments.BlockStateArgumentType
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceReloadListener
import net.minecraft.tag.BlockTags
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

object SawRecipeLoader : ResourceReloadListener {
    private const val directory = "saw_recipes"
    private val suffixes = arrayOf(".json5", ".json")
    private val logger = LogManager.getLogger()

    override fun onResourceReload(manager: ResourceManager) {
        SawRecipes.clear()

        manager.findResources(directory) {
            for (suffix in suffixes)
                if (it.endsWith(suffix))
                    return@findResources true

            false
        }.map(manager::getResource).forEach {
            SawRecipes.register(
                readRecipe(Parser.parseObj(it.inputStream.bufferedReader().lineSequence().joinToString(separator = "\n")))
                    ?: run {
                        logger.error("[ThisAndThat] Could not load saw recipe ${it.id}")
                        return@forEach
                    }
            )
        }

        logger.info("[ThisAndThat] Loaded ${SawRecipes.recipes.size} saw recipes")
    }

    private fun readRecipe(obj: JsonObject): SawRecipe? {
        val transform = obj.list("output")?.let(::readTransform) ?: return null
        val predicate = obj.obj("predicate")?.let(::readPredicate) ?: return null

        return SawRecipe(predicate, transform)
    }

    @Suppress("UNCHECKED_CAST")
    private fun readPredicate(obj: JsonObject): SawPredicate? {
        val type = obj.string("type") ?: return null
        val id = obj.string("id") ?: return null

        return when (type) {
            "tag" -> {{ it.block.matches(BlockTags.getContainer()[Identifier(id)]) }}
            "block" -> {{
                BlockStateArgumentType.create().method_9654(StringReader(id)).method_9493(
                    StateBlockProxy(it)
                )
            }}
            else -> null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun readTransform(list: List<Any>): SawTransform? {
        val functions: List<(() -> List<ItemStack>)?> = list.map {
            val obj = (it as? Map<String, Any> ?: return@map null).let(::JsonObject)
            val id = Identifier(obj.string("id") ?: return@map null)
            val amount = obj.int("amount") ?: 1
            val type = Identifier(obj.string("type") ?: return@map null)

            return@map {
                SawOutputType[type].getOutput(SawOutputType.Context(id, amount, obj.asMap))
            }
        }

        return {
            functions.flatMap { it?.invoke() ?: emptyList() }
        }
    }
}
