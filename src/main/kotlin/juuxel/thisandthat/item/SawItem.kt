package juuxel.thisandthat.item

import juuxel.thisandthat.saw.SawRecipes
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.util.ActionResult

class SawItem : ModItem("saw", Settings().itemGroup(ItemGroup.TOOLS).durability(80), hasDescription = true) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val pos = context.pos
        val output = SawRecipes.getOutput(world.getBlockState(pos))

        if (output.isEmpty()) return super.useOnBlock(context)

        if (!world.isClient) {
            for (stack in output)
                world.spawnEntity(ItemEntity(world, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, stack))

            world.breakBlock(pos, false)
            context.itemStack.applyDamage(1, context.player)
        }

        return ActionResult.SUCCESS
    }

    override fun canRepair(self: ItemStack, repairItem: ItemStack): Boolean {
        return repairIngredient.matches(repairItem) || super.canRepair(self, repairItem)
    }

    companion object {
        private val repairIngredient = Ingredient.ofItems(Items.IRON_INGOT)
    }
}
