package com.example.medicineremindernew.models

data class Pill(
    var id: String? = null,
    var user_id: String? = null,
    var name: String? = null,
    var dosage_value: Int? = null,
    var dosage_unit: String? = null,
    var date_from: String? = null,
    var date_to: String? = null,
    var times: List<String>? = null
)
