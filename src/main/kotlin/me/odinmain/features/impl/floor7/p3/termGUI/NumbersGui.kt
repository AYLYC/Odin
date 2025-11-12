package me.odinmain.features.impl.floor7.p3.termGUI

import me.odinmain.features.impl.floor7.p3.TerminalSolver
import me.odinmain.utils.equalsOneOf
import me.odinmain.utils.render.Colors
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer

object NumbersGui : TermGui() {

    private val mc: Minecraft = Minecraft.getMinecraft()
    private val fontRenderer: FontRenderer = mc.fontRendererObj

    override fun renderTerminal(slotCount: Int) {
        renderBackground(slotCount, 7)

        for (index in 9..slotCount) {
            if ((index % 9).equalsOneOf(0, 8)) continue

            val amount = TerminalSolver.currentTerm?.items?.get(index)?.stackSize ?: continue
            val solutionIndex = currentSolution.indexOf(index)

            val color = when (solutionIndex) {
                0 -> TerminalSolver.orderColor
                1 -> TerminalSolver.orderColor2
                2 -> TerminalSolver.orderColor3
                else -> Colors.TRANSPARENT
            }

            val (slotX, slotY) = renderSlot(index, color, TerminalSolver.orderColor)
            val slotSize = 55f * TerminalSolver.customTermSize
            val fontSize = 30f * TerminalSolver.customTermSize

            val scale = fontSize / 30f
            val text = amount.toString()

            val textWidth = fontRenderer.getStringWidth(text) * scale
            val textX = slotX + (slotSize - textWidth) / 2f
            val textY = slotY + (slotSize - 8f * scale) / 2f

            if (TerminalSolver.showNumbers && solutionIndex != -1) {
                net.minecraft.client.renderer.GlStateManager.pushMatrix()
                net.minecraft.client.renderer.GlStateManager.scale(scale, scale, scale)
                fontRenderer.drawStringWithShadow(
                    text,
                    textX / scale,
                    textY / scale,
                    0xFFFFFF
                )
                net.minecraft.client.renderer.GlStateManager.popMatrix()
            }
        }
    }
}
