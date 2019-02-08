/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.saw

import blue.endless.jankson.Jankson
import blue.endless.jankson.JsonObject
import com.mojang.brigadier.StringReader
import juuxel.thisandthat.ThisAndThat.logger
import juuxel.thisandthat.util.StateBlockProxy
import net.minecraft.command.arguments.BlockArgumentType
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceReloadListener
import net.minecraft.tag.BlockTags
import net.minecraft.util.Identifier

object SawRecipeLoader : ResourceReloadListener {
    private const val directory = "saw_recipes"
    private val suffixes = arrayOf(".json5", ".json", ".hjson")
    private val jankson = Jankson.builder().build()

    override fun onResourceReload(manager: ResourceManager) {
        SawRecipes.clear()

        manager.findResources(directory) {
            for (suffix in suffixes)
                if (it.endsWith(suffix))
                    return@findResources true

            false
        }.map(manager::getResource).forEach {
            SawRecipes.register(
                readRecipe(jankson.load(it.inputStream))
                    ?: run {
                        logger.error("Could not load saw recipe ${it.id}")
                        return@forEach
                    }
            )
        }

        logger.info("Loaded ${SawRecipes.recipes.size} saw recipes")
    }

    private fun readRecipe(obj: JsonObject): SawRecipe? {
        val transform = obj[List::class.java, "output"]?.let(::readTransform) ?: return null
        val predicate = obj.getObject("predicate")?.let(::readPredicate) ?: return null

        return SawRecipe(predicate, transform)
    }

    @Suppress("UNCHECKED_CAST")
    private fun readPredicate(obj: JsonObject): SawPredicate? {
        val type = obj[String::class.java, "type"] ?: return null
        val id = obj[String::class.java, "id"] ?: return null

        return when (type) {
            "tag" -> {{ it.block.matches(BlockTags.getContainer()[Identifier(id)]) }}
            "block" -> {{
                BlockArgumentType.create().method_9654(StringReader(id)).test(
                    StateBlockProxy(it)
                )
            }}
            else -> null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun readTransform(list: List<*>): SawTransform? {
        val functions: List<(() -> List<ItemStack>)?> = list.map {
            val obj = (it as? Map<String, Any> ?: return@map null).let(jankson::toJson) as? JsonObject ?: return null
            val id = Identifier(obj[String::class.java, "id"] ?: return@map null)
            val amount = obj[Int::class.javaObjectType, "amount"] ?: 1
            val type = Identifier(obj[String::class.java, "type"] ?: return@map null)

            return@map {
                SawOutputType[type].getOutput(SawOutputType.Context(id, amount, obj))
            }
        }

        return {
            functions.flatMap { it?.invoke() ?: emptyList() }
        }
    }
}
