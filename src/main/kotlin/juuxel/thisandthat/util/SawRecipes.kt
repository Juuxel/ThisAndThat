package juuxel.thisandthat.util

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

// TODO JSONs / JSON5s?
object SawRecipes {
    private val recipes = HashSet<Recipe>()

    fun register(recipe: Recipe) {
        recipes += recipe
    }

    fun getOutput(input: BlockState): List<ItemStack> =
        recipes.firstOrNull { it.predicate(input) }?.transform?.invoke(input) ?: emptyList()

    data class Recipe(val predicate: (BlockState) -> Boolean, val transform: (BlockState) -> List<ItemStack>)
}
