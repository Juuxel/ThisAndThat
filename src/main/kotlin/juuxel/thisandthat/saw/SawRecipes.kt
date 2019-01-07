package juuxel.thisandthat.saw

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

object SawRecipes {
    private val recipes = HashSet<SawRecipe>()

    internal fun register(recipe: SawRecipe) {
        recipes += recipe
    }

    fun getOutput(input: BlockState): List<ItemStack> =
        recipes.firstOrNull { it.predicate(input) }?.transform?.invoke(input) ?: emptyList()
}
