/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat

import juuxel.thisandthat.lib.ModConfig
import juuxel.thisandthat.util.CloudColorMode
import org.spongepowered.asm.lib.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class TTMixinPlugin : IMixinConfigPlugin {
    override fun onLoad(mixinPackage: String) {
        require(mixinPackage.startsWith(PACKAGE)) {
            "Invalid package: Expected $PACKAGE, found $mixinPackage"
        }
    }

    override fun preApply(target: String?, targetClass: ClassNode?, mixin: String?, info: IMixinInfo?) {}
    override fun postApply(target: String?, targetClass: ClassNode?, mixin: String?, info: IMixinInfo?) {}
    override fun getRefMapperConfig() = null
    override fun getMixins() = null
    override fun acceptTargets(myTargets: MutableSet<String>?, otherTargets: MutableSet<String>?) {}

    override fun shouldApplyMixin(targetClassName: String?, mixin: String) =
        mixinStates[mixin]?.invoke() ?: true

    companion object {
        private const val PACKAGE = "juuxel.thisandthat.mixin"
        private val mixinStates = mapOf(
            "$PACKAGE.client.BiomeMixin" to {
                ModConfig.getInstance().tweaks.changeSkyColor
            },
            "$PACKAGE.client.WorldMixin" to {
                ModConfig.getInstance().tweaks.cloudColorMode != CloudColorMode.Vanilla
            }
        )
    }
}
