#pragma once
#include <cmath>
#include <vector>
#include <stdexcept>
#include <iostream>

struct dual_number {
    float val, der;

    // constructors
    dual_number() : val(0.0f), der(0.0f) {}
    dual_number(float v) : val(v), der(0.0f) {}
    dual_number(float v, float d) : val(v), der(d) {}

    // getters
    float primal() const { return val; }
    float dual() const { return der; }

    // basic operations
    dual_number operator+(const dual_number& other) const {
        return {val + other.val, der + other.der};
    }

    dual_number operator-(const dual_number& other) const {
        return {val - other.val, der - other.der};
    }

    dual_number operator*(const dual_number& other) const {
        return {val * other.val, der * other.val + val * other.der};
    }

    dual_number operator/(const dual_number& other) const {
        if (other.val == 0.0f) {
            throw std::domain_error("Division by zero");
        }
        return {val / other.val, (der * other.val - val * other.der) / (other.val * other.val)};
    }

    dual_number operator-() const {
        return {-val, -der};
    }

    // math functions (defined as friend fcns for math syntax)

}
