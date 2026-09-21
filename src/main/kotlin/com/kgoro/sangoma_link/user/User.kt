package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.common.address.Address
import com.kgoro.sangoma_link.user.booking.Booking
import com.kgoro.sangoma_link.market.cart.Cart
import com.kgoro.sangoma_link.common.notification.Notification
import com.kgoro.sangoma_link.market.order.Order
import com.kgoro.sangoma_link.market.product.ProductReview
import com.kgoro.sangoma_link.common.review.Review
import com.kgoro.sangoma_link.sangoma.profile.Profile
import com.kgoro.sangoma_link.storage.MediaMetadata
import com.kgoro.sangoma_link.support_ticket.SupportTicket
import com.kgoro.sangoma_link.user.enums.UserType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_users_email", columnList = "email"),
        Index(name = "idx_users_user_type", columnList = "userType"),
        Index(name = "idx_users_is_active", columnList = "isActive")
    ],
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"])
    ]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 100)
    val email: String,

    @Column(name = "password_hash", nullable = false, length = 255)
    val passwordHash: String,

    @Column(name = "first_name", nullable = false, length = 100)
    val firstName: String,

    @Column(name = "last_name", nullable = false, length = 100)
    val lastName: String,

    @Column(name = "phone_number", length = 20)
    val phoneNumber: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 20)
    val userType: UserType,

    @Column(name = "profile_image_url", length = 500)
    val profileImageUrl: String? = null,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "last_login_at")
    val lastLoginAt: LocalDateTime? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    // Relationships
    @OneToOne(mappedBy = "ownedBy", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var userMedia: MediaMetadata? = null,
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var profile: Profile? = null,
    @Transient
    val addresses: List<Address> = emptyList(),
    @Transient
    val bookings: List<Booking> = emptyList(),
    @Transient
    val orders: List<Order> =emptyList(),
    @Transient
    var notifications: List<Notification> = emptyList(),
    @Transient
    val supportTickets: List<SupportTicket> = emptyList(),
    @Transient
    val reviews: List<Review> = emptyList(),
    @Transient
    val productReviews: List<ProductReview> = emptyList(),

    @OneToOne(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val cart: Cart? = null
) {
    // Business logic methods
    fun getFullName(): String = "$firstName $lastName"

    fun isSangoma(): Boolean = userType == UserType.Sangoma

    fun isCustomer(): Boolean = userType == UserType.Customer

    fun markAsVerified(): User = this.copy(isVerified = true)

    fun deactivate(): User = this.copy(isActive = false)

    fun activate(): User = this.copy(isActive = true)

    fun updateLastLogin(): User = this.copy(lastLoginAt = LocalDateTime.now())

    fun updateProfile(
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profileImageUrl: String? = null
    ): User = this.copy(
        firstName = firstName ?: this.firstName,
        lastName = lastName ?: this.lastName,
        phoneNumber = phoneNumber ?: this.phoneNumber,
        profileImageUrl = profileImageUrl ?: this.profileImageUrl,
        updatedAt = LocalDateTime.now()
    )

//    // Helper methods for relationship management
//    fun addAddress(address: Address): User {
//        if (address.userid != this) {
//            address.user = this
//        }
//        if (!addresses.contains(address)) {
//            addresses.add(address)
//        }
//        return this
//    }
//
//    fun removeAddress(address: Address): User {
//        addresses.remove(address)
//        address.user = null
//        return this
//    }

    fun getPrimaryAddress(): Address? = addresses.find { it.isPrimary }

    // Validation methods
    fun validateForRegistration(): Boolean {
        return email.isNotBlank() &&
                passwordHash.isNotBlank() &&
                firstName.isNotBlank() &&
                lastName.isNotBlank()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (email != other.email) return false
        if (id != null && other.id != null && id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, email='$email', firstName='$firstName', lastName='$lastName', userType=$userType)"
    }

    companion object {
        fun createSangoma(
            email: String,
            passwordHash: String,
            firstName: String,
            lastName: String,
            phoneNumber: String? = null,
            profileImageUrl: String? = null
        ): User {
            return User(
                email = email,
                passwordHash = passwordHash,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                userType = UserType.Sangoma,
                profileImageUrl = profileImageUrl,
                isVerified = false,
                isActive = true
            )
        }

        fun createCustomer(
            email: String,
            passwordHash: String,
            firstName: String,
            lastName: String,
            phoneNumber: String? = null,
            profileImageUrl: String? = null
        ): User {
            return User(
                email = email,
                passwordHash = passwordHash,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                userType = UserType.Customer,
                profileImageUrl = profileImageUrl,
                isVerified = false,
                isActive = true
            )
        }

        fun createAdmin(
            email: String,
            passwordHash: String,
            firstName: String,
            lastName: String,
            phoneNumber: String?,
            profileImageUrl: String?
        ): User {
            return User(
                email = email,
                passwordHash = passwordHash,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                userType = UserType.Admin,
                profileImageUrl = profileImageUrl,
                isVerified = false,
                isActive = true
            )
        }

        fun toPublicUserDto(user: User): PublicUserDto{
            return PublicUserDto(
                id = user.id!!,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                phoneNumber = user.phoneNumber!!,
                profileImageUrl = user.profileImageUrl,
                userType = user.userType
            )
        }
    }
}