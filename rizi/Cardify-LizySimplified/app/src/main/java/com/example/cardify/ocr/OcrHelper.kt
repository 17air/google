package com.example.cardify.ocr

import android.content.Context
import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import kotlinx.coroutines.tasks.await

/**
 * Helper object that runs ML Kit text recognition on a bitmap and extracts
 * common business card fields using simple heuristics.
 */
object OcrHelper {
    data class Result(
        val name: String = "",
        val phone: String = "",
        val email: String = "",
        val company: String = "",
        val position: String = "",
        val etc: String = ""
    )

    suspend fun recognize(context: Context, bitmap: Bitmap): Result {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
        val result = recognizer.process(image).await()
        val text = result.text
        return parseText(text)
    }

    private fun parseText(text: String): Result {
        var name = ""
        var phone = ""
        var email = ""
        var company = ""
        var position = ""
        val lines = text.lines()
        for (line in lines) {
            val trimmed = line.trim()
            when {
                trimmed.contains("@") -> email = trimmed
                trimmed.matches(Regex(".*\\d{2,3}-\\d{3,4}-\\d{4}.*")) -> phone = trimmed
                trimmed.contains("회사") || trimmed.contains("Inc", true) -> company = trimmed
                trimmed.contains("대표") || trimmed.contains("직책") -> position = trimmed
                name.isEmpty() -> name = trimmed
            }
        }
        val used = setOf(name, phone, email, company, position)
        val others = lines.filter { it.trim().isNotEmpty() && it.trim() !in used }
        return Result(name, phone, email, company, position, others.joinToString("\n"))
    }
}
