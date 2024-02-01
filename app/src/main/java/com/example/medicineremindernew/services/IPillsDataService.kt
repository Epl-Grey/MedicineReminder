package com.example.medicineremindernew.services

import com.example.medicineremindernew.models.Pill

interface IPillsDataService {
    suspend fun getPills(): List<Pill>
    suspend fun createPill(pill: Pill)
    suspend fun updatePill(pill: Pill)
    suspend fun deletePill(pill: Pill)
}