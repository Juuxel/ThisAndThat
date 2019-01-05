package juuxel.thisandthat.lib

import juuxel.thisandthat.multipart.PlatformMultipart
import juuxel.thisandthat.multipart.PostMultipart
import juuxel.thisandthat.util.BlockVariant

object ModMultiparts : ModRegistry() {
    fun init() {
        registerMultipart(PostMultipart(BlockVariant.Wood.Oak))
        registerMultipart(PlatformMultipart(BlockVariant.Wood.Oak))
    }
}
