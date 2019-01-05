/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import juuxel.thisandthat.multipart.PlatformMultipart
import juuxel.thisandthat.multipart.PostMultipart
import juuxel.thisandthat.util.BlockVariant

object ModMultiparts : ModRegistry() {
    fun init() {
        if (!exists("net.shadowfacts.simplemultipart.SimpleMultipart") || !ModConfig.multiparts) return

        for (variant in BlockVariant.Wood.values()) {
            registerMultipart(PostMultipart(variant))
            registerMultipart(PlatformMultipart(variant))
        }
    }

    private fun exists(clazz: String): Boolean =
        try {
            Class.forName(clazz)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
}
