package juuxel.thisandthat.lib

import io.github.juuxel.polyester.registry.PolyesterRegistry
import juuxel.thisandthat.ThisAndThat
import juuxel.thisandthat.item.StoneRodItem
import net.minecraft.util.registry.Registry

object ModItems : PolyesterRegistry(ThisAndThat.ID) {
    val STONE_ROD = register(Registry.ITEM, StoneRodItem())

    fun init() {}
}
