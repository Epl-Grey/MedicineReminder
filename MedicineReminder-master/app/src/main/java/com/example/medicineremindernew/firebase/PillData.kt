package com.example.medicineremindernew.firebase

data class PillData(
        var pillId: String? = null,
        var userId: String? = null,
        var pillName: String? = null,
        var pillValue: String? = null,
        var pillDosage: String? = null,
        var pillDateFrom: String? = null,
        var pillDateTo: String? = null,
        var pillTime1: String? = null,
        var pillTime2: String? = null,
        var pillTime3: String? = null,
        var pillTime4: String? = null,
        var pillTime5: String? = null,
        var pillTime6: String? = null,
        var pillTimesPerDay: String? = null
)
