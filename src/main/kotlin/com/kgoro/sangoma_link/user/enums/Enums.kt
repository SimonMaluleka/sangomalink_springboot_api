package com.kgoro.sangoma_link.user.enums

enum class UserType {
    Customer,
    Sangoma,
    Admin
}

enum class ApprovalStatus {
    PENDING,
    APPROVED,
    REJECTED
}

enum class VerificationStatus {
    PENDING,
    VERIFIED,
    REJECTED
}

enum class DocumentType {
    ID,
    CERTIFICATION,
    PORTFOLIO
}


enum class BookingStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELLED,
    NO_SHOW
}


enum class PaymentStatus {
    PENDING,
    PAID,
    REFUNDED,
    FAILED
}

enum class ReminderType {
    EMAIL,
    SMS,
    PUSH
}

enum class ReminderTiming {
    _24_HOURS,
    _1_HOUR,
    _30_MINUTES
}

enum class ServiceType {
    IN_PERSON,
    VIDEO_CALL,
    PHONE_CALL
}

enum class WeightUnit {
    G, KG, ML, L
}

enum class VariationType {
    SIZE, COLOR, POTENCY
}

enum class OrderStatus {
    PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}


enum class PaymentMethod {
    CARD, BANK_TRANSFER, CASH, MOBILE_MONEY
}

enum class ShippingStatus {
    PENDING, SHIPPED, IN_TRANSIT, DELIVERED
}



