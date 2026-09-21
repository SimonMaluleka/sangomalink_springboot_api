package com.kgoro.sangoma_link.sangoma.profile

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import com.kgoro.sangoma_link.user.User
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sangoma_profiles", schema = "public")
class Profile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "accepts_new_clients", nullable = false)
    val acceptsNewClients: Boolean,

    @Column(name = "approval_status")
    val approvalStatus: ApprovalStatus,

    @Column(name = "average_rating", nullable = false)
    val averageRating: Double,

    @Column(name = "biography", length = 1024)
    val biography: String?,

    @Column(name = "consultation_approach", length = 1024)
    val consultationApproach: String?,

    @Column(name = "created_at", columnDefinition = "timestamp(6) without time zone")
    val createdAt: LocalDateTime?,

    @Column(name = "healing_specialty", length = 255)
    val healingSpecialty: String?,

    @Column(name = "is_featured", nullable = false)
    val isFeatured: Boolean,

    // Maps the PostgreSQL character varying(255)[] array type
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.ARRAY)
    @Column(name = "languages_spoken", columnDefinition = "text[]")
    val languagesSpoken: List<String>?,

    @Column(name = "profile_approved_at", columnDefinition = "timestamp(6) without time zone")
    val profileApprovedAt: LocalDateTime?,

    @Column(name = "total_reviews", nullable = false)
    val totalReviews: Long,

    @Column(name = "traditional_lineage", length = 255)
    val traditionalLineage: String?,

    @Column(name = "updated_at", columnDefinition = "timestamp(6) without time zone")
    val updatedAt: LocalDateTime?,

    @Column(name = "years_of_experience", nullable = false)
    val yearsOfExperience: Long,

    // One-to-One relationship mapping the UNIQUE foreign key to public.users
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    val user: User
)


@Converter
class StringListConverter : AttributeConverter<List<String>?, Array<String>?> {
    override fun convertToDatabaseColumn(attribute: List<String>?): Array<String>? {
        return attribute?.toTypedArray()
    }

    override fun convertToEntityAttribute(dbData: Array<String>?): List<String>? {
        return dbData?.toList()
    }
}
