/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.saw

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

object SawRecipes {
    val recipes: Set<SawRecipe> get() = mutRecipes
    private val mutRecipes = HashSet<SawRecipe>()

    internal fun register(recipe: SawRecipe) {
        mutRecipes += recipe
    }

    internal fun clear() {
        mutRecipes.clear()
    }

    fun getOutput(input: BlockState): List<ItemStack> =
        mutRecipes.firstOrNull { it.predicate(input) }?.transform?.invoke(input) ?: emptyList()
}
