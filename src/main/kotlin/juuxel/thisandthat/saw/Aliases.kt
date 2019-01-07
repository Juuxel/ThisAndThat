package juuxel.thisandthat.saw

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

typealias SawPredicate = (BlockState) -> Boolean
typealias SawTransform = (BlockState) -> List<ItemStack>
