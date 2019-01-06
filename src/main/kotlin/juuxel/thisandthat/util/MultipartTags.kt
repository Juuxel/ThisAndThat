package juuxel.thisandthat.util

import net.fabricmc.fabric.tags.TagDelegate
import net.minecraft.tag.RegistryTagContainer
import net.minecraft.tag.Tag
import net.minecraft.tag.TagContainer
import net.minecraft.tag.TagManager
import net.minecraft.util.Identifier
import net.shadowfacts.simplemultipart.multipart.Multipart

object MultipartTags {
    @JvmField val containers = mutableMapOf<TagManager, RegistryTagContainer<Multipart>>()
    @JvmStatic var container: TagContainer<Multipart> = TagContainer<Multipart>({ false }, { null }, "", false, "")
        set(value) {
            containerChanges++
            field = value
        }

    private var containerChanges = 0

    fun create(id: Identifier): Tag<Multipart> = TagImpl(id)

    private class TagImpl(id: Identifier) : TagDelegate<Multipart>(id, null) {
        private var instanceContainer: TagContainer<Multipart>? = null

        override fun onAccess() {
            if (instanceContainer != container) {
                instanceContainer = container
                delegate = container.getOrCreate(id)
            }
        }
    }
}
