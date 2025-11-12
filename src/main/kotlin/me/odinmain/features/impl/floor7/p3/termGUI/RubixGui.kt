package me.odinmain.features.impl.floor7.p3.termGUI

import me.odinmain.features.impl.floor7.p3.TerminalSolver
import me.odinmain.utils.render.Color
import me.odinmain.utils.render.Colors
import me.odinmain.utils.ui.rendering.NVGRenderer
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL11

object RubixGui : TermGui() {

    override fun renderTerminal(slotCount: Int) {
        renderBackground(slotCount, 3)

        val mc = Minecraft.getMinecraft()
        val fontRenderer = mc.fontRendererObj

        currentSolution.distinct().forEach { index ->
            val amount = currentSolution.count { it == index }
            val clicksRequired = if (amount < 3) amount else amount - 5
            if (clicksRequired == 0) return@forEach

            val (slotX, slotY) = renderSlot(
                index,
                getColor(clicksRequired),
                getColor(if (amount < 3) clicksRequired + 1 else clicksRequired - 1)
            )

            val slotSize = 55f * TerminalSolver.customTermSize
            val text = clicksRequired.toString()

            // ---- NanoVGを一時停止 ----
            NVGRenderer.endFrame()

            // ---- Minecraft標準フォントで描画 ----
            GL11.glPushMatrix()
            GL11.glScalef(TerminalSolver.customTermSize, TerminalSolver.customTermSize, 1f)

            val textWidth = fontRenderer.getStringWidth(text)
            val scaledX = slotX / TerminalSolver.customTermSize
            val scaledY = slotY / TerminalSolver.customTermSize
            val textX = scaledX + (slotSize / TerminalSolver.customTermSize - textWidth) / 2f
            val textY = scaledY + (slotSize / TerminalSolver.customTermSize - 8f) / 2f // フォント高さ8px前提

            fontRenderer.drawStringWithShadow(text, textX, textY, 0xFFFFFF)
            GL11.glPopMatrix()

            // ---- NanoVGを再開 ----
            NVGRenderer.beginFrame(mc.displayWidth.toFloat(), mc.displayHeight.toFloat())
        }
    }

    private fun getColor(clicksRequired: Int): Color = when (clicksRequired) {
        1 -> TerminalSolver.rubixColor1
        2 -> TerminalSolver.rubixColor2
        -1 -> TerminalSolver.oppositeRubixColor1
        else -> TerminalSolver.oppositeRubixColor2
    }
}
