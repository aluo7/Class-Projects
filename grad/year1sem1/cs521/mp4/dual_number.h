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
    float value() const { return val; }
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
    friend dual_number sin(const dual_number& x) {
      return {std::sin(x.val), x.der * std::cos(x.val)};
    }

    friend dual_number cos(const dual_number& x) {
      return {std::cos(x.val), -1 * x.der * std::sin(x.val)};
    }

    friend dual_number exp(const dual_number& x) {
      return {std::exp(x.val), x.der * std::exp(x.val)};
    }

    friend dual_number ln(const dual_number& x) {
      if (x.val <= 0.0f) throw std::runtime_error("log domain error");
      return {std::log(x.val), x.der * 1 / x.val};
    }

    friend dual_number relu(const dual_number& x) {
      if (x.val > 0.0f) {
        return {x.val, x.der};
      } else {
        return {0.0f, 0.0f};
      }
    }

    friend dual_number sigmoid(const dual_number& x) {
      float sigma = 1.0f / (1.0f + std::exp(-1 * x.val));
      return {sigma, x.der * sigma * (1.0f - sigma)};
    }

    friend dual_number tanh(const dual_number& x) {
      return {std::tanh(x.val), x.der * (1.0f - std::tanh(x.val) * std::tanh(x.val))};
    }
};

using dual_vector = std::vector<dual_number>;

inline dual_vector operator+(const dual_vector& a, const dual_vector& b) {
    if (a.size() != b.size()) throw std::runtime_error("Dimension mismatch");
    dual_vector c;
    c.reserve(a.size());
    for (size_t i=0; i<a.size(); ++i) c.push_back(a[i] + b[i]);
    return c;
}

inline dual_vector operator-(const dual_vector& a, const dual_vector& b) {
    if (a.size() != b.size()) throw std::runtime_error("Dimension mismatch");
    dual_vector c;
    c.reserve(a.size());
    for (size_t i=0; i<a.size(); ++i) c.push_back(a[i] - b[i]);
    return c;
}

inline dual_vector operator*(const dual_vector& a, const dual_vector& b) {
    if (a.size() != b.size()) throw std::runtime_error("Dimension mismatch");
    dual_vector c;
    c.reserve(a.size());
    for (size_t i=0; i<a.size(); ++i) c.push_back(a[i] * b[i]);
    return c;
}

inline dual_vector operator/(const dual_vector& a, const dual_vector& b) {
    if (a.size() != b.size()) throw std::runtime_error("Dimension mismatch");
    dual_vector c;
    c.reserve(a.size());
    for (size_t i=0; i<a.size(); ++i) c.push_back(a[i] / b[i]);
    return c;
}

inline dual_vector sin(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(sin(el));
    return res;
}

inline dual_vector cos(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(cos(el));
    return res;
}

inline dual_vector exp(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(exp(el));
    return res;
}

inline dual_vector ln(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(ln(el));
    return res;
}

inline dual_vector relu(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(relu(el));
    return res;
}

inline dual_vector sigmoid(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(sigmoid(el));
    return res;
}

inline dual_vector tanh(const dual_vector& v) {
    dual_vector res;
    res.reserve(v.size());
    for (const auto& el : v) res.push_back(tanh(el));
    return res;
}

// For print
inline std::ostream& operator<<(std::ostream& os, const dual_number& d) {
    os << d.value() << " + " << d.dual() << "e";
    return os;
}
