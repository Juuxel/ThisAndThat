/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.api

data class TransferAmount(val min: Int, val max: Int) {
    companion object {
        fun exact(amount: Int) = TransferAmount(amount, amount)
        fun min(amount: Int) = TransferAmount(amount, Int.MAX_VALUE)
        fun max(amount: Int) = TransferAmount(0, amount)
    }
}
