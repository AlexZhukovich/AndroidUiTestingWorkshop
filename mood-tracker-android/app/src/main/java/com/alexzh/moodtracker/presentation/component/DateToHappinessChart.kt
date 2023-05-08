package com.alexzh.moodtracker.presentation.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.alexzh.moodtracker.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateToHappinessChart(
    data: List<Pair<LocalDate, Float>>,
    modifier: Modifier,
    axisColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    showYAxis: Boolean = false,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    iconColor: Color = MaterialTheme.colorScheme.onSurface,
    chartIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    iconSize: Dp = 20.dp,
    chartIndicatorTopCornerRadius: Dp = 4.dp,
) {
    val horizontalPadding = 30f
    val verticalPadding = 20f
    val betweenColumnPadding = 20f
    val happinessLevelDescriptionWidth = 100f
    val timeDescriptionHeight = 60f

    val density = LocalDensity.current
    val iconSizeInPx = density.run { iconSize.toPx() }
    val textPaint = remember(density) {
        Paint().apply {
            color = textColor.toArgb()
                .let { android.graphics.Color.argb(it.alpha, it.red, it.green, it.blue) }
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    val iconPainterAngryIcon = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.ic_emotion_angry)
    )
    val iconPainterConfusedIcon =
        rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_emotion_confused))
    val iconPainterNeutralIcon =
        rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_emotion_neutral))
    val iconPainterHappyIcon =
        rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_emotion_happy))
    val iconPainterExcitedIcon =
        rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_emotion_excited))

    Canvas(
        modifier = modifier
    ) {
        val chartHeight = size.height - verticalPadding - verticalPadding
        val chartWidth =
            size.width - happinessLevelDescriptionWidth - horizontalPadding - horizontalPadding

        // Happiness Level
        if (showYAxis) {
            drawLine(
                start = Offset(
                    happinessLevelDescriptionWidth,
                    size.height - verticalPadding - timeDescriptionHeight
                ),
                end = Offset(happinessLevelDescriptionWidth, 0f + verticalPadding),
                color = axisColor,
                strokeWidth = 3f
            )
        }

        (1..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                val painter = when (i) {
                    1 -> iconPainterAngryIcon
                    2 -> iconPainterConfusedIcon
                    3 -> iconPainterNeutralIcon
                    4 -> iconPainterHappyIcon
                    else -> iconPainterExcitedIcon
                }
                translate(
                    left = ((happinessLevelDescriptionWidth) / 2f) - verticalPadding,
                    top = size.height - timeDescriptionHeight - (i * chartHeight / 6f) - iconSizeInPx
                ) {
                    with(painter) {
                        draw(
                            size = Size(iconSizeInPx, iconSizeInPx),
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            }
        }

        // Time
        val columnWidth = (chartWidth - (data.size * betweenColumnPadding)) / data.size

        data.onEachIndexed { index, dateToHappiness ->

            val topCornerRadiusInPx = density.run { chartIndicatorTopCornerRadius.toPx() }
            val cornerRadius = CornerRadius(
                topCornerRadiusInPx,
                topCornerRadiusInPx
            )
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            Offset(
                                horizontalPadding + happinessLevelDescriptionWidth + (betweenColumnPadding * (index)) + (columnWidth * index),
                                size.height - timeDescriptionHeight - (dateToHappiness.second * chartHeight / 6f) - (iconSizeInPx / 2f) + 10
                            ),
                            Size(
                                columnWidth,
                                (dateToHappiness.second * chartHeight / 6f)
                            )
                        ),
                        topLeft = cornerRadius,
                        topRight = cornerRadius,
                    )
                )
            }
            drawPath(
                path = path,
                color = chartIndicatorColor
            )
        }

        drawLine(
            start = Offset(
                happinessLevelDescriptionWidth,
                size.height - verticalPadding - timeDescriptionHeight
            ),
            end = Offset(
                size.width - horizontalPadding,
                size.height - verticalPadding - timeDescriptionHeight
            ),
            color = axisColor,
            strokeWidth = 5f
        )


        data.onEachIndexed { index, dateToHappiness ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    dateToHappiness.first.format(DateTimeFormatter.ofPattern("E")),
                    (happinessLevelDescriptionWidth + horizontalPadding + (betweenColumnPadding * (index)) + (columnWidth * index)) + (columnWidth / 2),
                    chartHeight + verticalPadding,
                    textPaint
                )
            }
        }
    }
}