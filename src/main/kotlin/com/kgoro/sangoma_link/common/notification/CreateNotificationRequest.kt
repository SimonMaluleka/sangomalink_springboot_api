package com.kgoro.sangoma_link.common.notification

data class CreateNotificationRequest(
    val userId: Long,
    val title: String,
    val message: String,
    val notificationType: NotificationType? = null,
    val relatedEntityType: String? = null,
    val relatedEntityId: Long? = null,
    val actionUrl: String? = null,
    val metadata: Map<String, Any>? = null
){
//    companion object {
//        fun fromBooking(booking: Booking): CreateNotificationRequest{
//            return CreateNotificationRequest(
//                userId = booking.userId,
//                title = booking.service.name,
//                message = ,
//                notificationType = TODO(),
//                relatedEntityType = TODO(),
//                relatedEntityId = TODO(),
//                actionUrl = TODO(),
//                metadata = TODO()
//            )
//        }
//    }
}