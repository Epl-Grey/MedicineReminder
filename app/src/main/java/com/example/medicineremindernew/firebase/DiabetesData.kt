package com.example.medicineremindernew.firebase

data class DiabetesData(
        var userId: String? = null,
        var diabetesMorning: String? = null,
        var diabetesAfternoon: String? = null,
        var diabetesEvening: String? = null,
        var diabetesFChI: String? = null
)
