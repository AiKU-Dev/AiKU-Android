package com.hyunjung.aiku.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.theme.LocalAikuContentColor

@Composable
fun AikuIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalAikuContentColor.current
) {
    AikuIcon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun AikuIcon(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalAikuContentColor.current
) {
    val painter = remember(bitmap) { BitmapPainter(bitmap) }
    AikuIcon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun AikuIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalAikuContentColor.current
) {
    val colorFilter =
        remember(tint) { if (tint == Color.Unspecified) null else ColorFilter.tint(tint) }
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }
    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeFor(painter)
            .paint(painter, colorFilter = colorFilter, contentScale = ContentScale.Fit)
            .then(semantics)
    )
}

@Composable
fun AikuIcon(
    painter: Painter,
    tint: ColorProducer?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }

    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeForColorProducer(painter)
            .drawBehind {
                with(painter) {
                    draw(size = size, colorFilter = tint?.invoke()?.let { ColorFilter.tint(it) })
                }
            }
            .then(semantics)
    )
}

private fun Modifier.defaultSizeForColorProducer(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            DefaultIconSizeModifier
        } else {
            val intrinsicSize = painter.intrinsicSize
            val srcWidth = intrinsicSize.width

            val srcHeight = intrinsicSize.height

            Modifier.layout { measurable, _ ->
                val placeable =
                    measurable.measure(Constraints.fixed(srcWidth.toInt(), srcHeight.toInt()))
                layout(placeable.width, placeable.height) { placeable.place(0, 0) }
            }
        }
    )

private fun Modifier.defaultSizeFor(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            DefaultIconSizeModifier
        } else {
            Modifier
        }
    )

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()

private val DefaultIconSize = 16.dp
private val DefaultIconSizeModifier = Modifier.size(DefaultIconSize)