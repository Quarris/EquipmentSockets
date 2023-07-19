package dev.quarris.mysticalsockets.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.quarris.mysticalsockets.ModRef;
import dev.quarris.mysticalsockets.capability.ISocketHolder;
import dev.quarris.mysticalsockets.container.SocketTooltip;
import dev.quarris.mysticalsockets.sockets.SocketSlot;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class ClientSocketTooltip implements ClientTooltipComponent {

    private static final ResourceLocation SLOT_TEXTURE = ModRef.res("textures/gui/socket_slot.png");

    private static int SCALE = 3;
    private final ItemStack item;
    private final ISocketHolder socketHolder;

    public ClientSocketTooltip(SocketTooltip tooltip) {
        this.item = tooltip.getItem();
        this.socketHolder = tooltip.getSocketHolder();
    }

    @Override
    public void renderText(Font pFont, int pMouseX, int pMouseY, Matrix4f pMatrix, MultiBufferSource.BufferSource pBufferSource) {

    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGraphics) {
        SCALE = 5;
        PoseStack poseStack = pGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(pX, pY, 0);
        poseStack.pushPose();
        poseStack.scale(SCALE, SCALE, 1);
        pGraphics.renderFakeItem(this.item, 0, 0);
        poseStack.popPose();
        poseStack.scale(SCALE / 6f, SCALE / 6f, 1);
        NonNullList<SocketSlot> sockets = this.socketHolder.getSockets();
        for (int i = 0; i < sockets.size(); i++) {
            SocketSlot socket = sockets.get(i);
            int slotX = 24 + ((i % 2) * 28);
            int slotY = 12 + ((i / 2) * 28);
            renderSocket(pGraphics, socket, slotX, slotY);
        }
        poseStack.popPose();
    }

    private static void renderSocket(GuiGraphics pGraphics, SocketSlot pSocket, int x, int y) {
        PoseStack pose = pGraphics.pose();
        RenderSystem.enableBlend();
        pGraphics.blit(SLOT_TEXTURE, x, y, 155, 0, 0, 20, 20, 256, 256);
        RenderSystem.disableBlend();
        if (!pSocket.isEmpty()) {
            pose.pushPose();
            pose.translate(0, 0, 10); // Render on top of the slot
            pGraphics.renderFakeItem(pSocket.getItem(), x + 2, y + 2);
            pose.popPose();
        }

    }

    private static void blit(PoseStack pose, ResourceLocation texture, int x, int y, int width, int height, int pBlitOffset, float u, float v) {
        int maxX = x + width;
        int maxY = y + height;
        float minU = u / 256f, maxU = (u + width) / 256f;
        float minV = v / 256f, maxV = (v + height) / 256f;

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = pose.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float) x, (float) y, (float)pBlitOffset).uv(minU, minV).endVertex();
        bufferbuilder.vertex(matrix4f, (float) x, (float)maxY, (float)pBlitOffset).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)maxX, (float)maxY, (float)pBlitOffset).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)maxX, (float) y, (float)pBlitOffset).uv(maxU, minV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }

    @Override
    public int getHeight() {
        return 16 * SCALE;
    }

    @Override
    public int getWidth(Font pFont) {
        return 16 * SCALE;
    }
}
