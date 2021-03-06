@file:Suppress("unused")

package org.openrndr.math

import kotlin.math.max
import kotlin.math.min

/**
 * Linearly maps a value, which is given in the before domain to a value in the after domain
 * @param beforeLeft the left value of the before domain
 * @param beforeRight the right value of the before domain
 * @param afterLeft the left value of the after domain
 * @param afterRight the right value of the after domain
 * @param value the value to map from the before domain to the after domain
 * @return a value in the after domain
 */
fun map(beforeLeft: Double, beforeRight: Double, afterLeft: Double, afterRight: Double, value: Double): Double {
    val n = (value - beforeLeft) / (beforeRight - beforeLeft)
    return afterLeft + n * (afterRight - afterLeft)
}

/**
 * Linearly maps a value, which is given in the before domain to a value in the after domain
 * @param beforeLeft the left value of the before domain
 * @param beforeRight the right value of the before domain
 * @param afterLeft the left value of the after domain
 * @param afterRight the right value of the after domain
 * @return a value in the after domain
 */
@JvmName("doubleMap")
fun Double.map(beforeLeft: Double, beforeRight: Double, afterLeft: Double, afterRight: Double): Double {
    return map(beforeLeft, beforeRight, afterLeft, afterRight, this)
}

fun Vector2.map(beforeLeft: Vector2,
                beforeRight: Vector2,
                afterLeft: Vector2,
                afterRight: Vector2) =
        Vector2(x.map(beforeLeft.x, beforeRight.x, afterLeft.x, afterRight.x),
                y.map(beforeLeft.y, beforeRight.y, afterLeft.y, afterRight.y))

fun Vector3.map(beforeLeft: Vector3,
                beforeRight: Vector3,
                afterLeft: Vector3,
                afterRight: Vector3) =
        Vector3(x.map(beforeLeft.x, beforeRight.x, afterLeft.x, afterRight.x),
                y.map(beforeLeft.y, beforeRight.y, afterLeft.y, afterRight.y),
                z.map(beforeLeft.z, beforeRight.z, afterLeft.z, afterRight.z))

fun Vector4.map(beforeLeft: Vector4,
                beforeRight: Vector4,
                afterLeft: Vector4,
                afterRight: Vector4) =
        Vector4(x.map(beforeLeft.x, beforeRight.x, afterLeft.x, afterRight.x),
                y.map(beforeLeft.y, beforeRight.y, afterLeft.y, afterRight.y),
                z.map(beforeLeft.z, beforeRight.z, afterLeft.z, afterRight.z),
                w.map(beforeLeft.w, beforeRight.w, afterLeft.w, afterRight.w))

fun linearstep(edge0: Double, edge1: Double, x: Double): Double = saturate((x - edge0) / (edge1 - edge0))

/**
 * Smoothstep
 * @param edge0
 * @param edge1
 * @param x
 * @return a mapped value in the interval [0, 1]
 */
fun smoothstep(edge0: Double, edge1: Double, x: Double): Double {
    // Scale, bias and saturate x to 0..1 range
    val u = saturate((x - edge0) / (edge1 - edge0))
    // Evaluate polynomial
    return u * u * (3 - 2 * u)
}

@JvmName("doubleSmoothstep")
fun Double.smoothstep(edge0: Double, edge1: Double) = smoothstep(edge0, edge1, this)

fun Vector2.smoothstep(edge0: Vector2, edge1: Vector2): Vector2 =
        Vector2(this.x.smoothstep(edge0.x, edge1.x),
                this.x.smoothstep(edge0.y, edge1.y))

fun Vector3.smoothstep(edge0: Vector3, edge1: Vector3): Vector3 =
        Vector3(this.x.smoothstep(edge0.x, edge1.x),
                this.x.smoothstep(edge0.y, edge1.y),
                this.x.smoothstep(edge0.z, edge1.z))

fun Vector4.smoothstep(edge0: Vector4, edge1: Vector4): Vector4 =
        Vector4(this.x.smoothstep(edge0.x, edge1.x),
                this.x.smoothstep(edge0.y, edge1.y),
                this.x.smoothstep(edge0.z, edge1.z),
                this.x.smoothstep(edge0.w, edge1.w))

/**
 * Smoothstep
 * @param edge0
 * @param edge1
 * @param x
 * @return a mapped value in the interval [0, 1]
 */
fun smoothstepIn(edge0: Double, edge1: Double, x: Double): Double {
    // Scale, bias and saturate x to 0..1 range
    val u = saturate((x - edge0) / (edge1 - edge0))
    // Evaluate polynomial

    return if (x < 0.5)
        u * u * (3 - 2 * u)
    else {
        u
    }
}

/**
 * Smootherstep
 * @param edge0
 * @param edge1
 * @param x
 * @return a mapped value in the interval [0, 1]
 */
fun smootherstep(edge0: Double, edge1: Double, x: Double): Double {
    // Scale, bias and saturate x to 0..1 range
    val u = saturate((x - edge0) / (edge1 - edge0))
    // Evaluate polynomial
    return u * u * u * (u * (u * 6 - 15) + 10)
}


fun saturate(x: Double) = max(0.0, min(1.0, x))

@JvmName("doubleSaturate")
fun Double.saturate() = saturate(this)

fun Vector2.saturate() = Vector2(x.saturate(), y.saturate())
fun Vector3.saturate() = Vector3(x.saturate(), y.saturate(), z.saturate())
fun Vector4.saturate() = Vector4(x.saturate(), y.saturate(), z.saturate(), w.saturate())

fun mix(left: Double, right: Double, x: Double) = left * (1.0 - x) + right * x