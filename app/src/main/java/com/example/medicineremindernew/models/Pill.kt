package com.example.medicineremindernew.models


import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Pill(
    var created_at: String? = null,
    var id: Long? = null,
    var user_id: String? = null,
    var name: String? = null,
    var dosage_value: Int? = null,
    var dosage_unit: String? = null,
    var date_from: String? = null,
    var date_to: String? = null,
    var times: List<String>? = null
): java.io.Serializable
