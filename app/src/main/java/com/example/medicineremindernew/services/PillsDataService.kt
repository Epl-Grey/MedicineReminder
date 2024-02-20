package com.example.medicineremindernew.services

import com.example.medicineremindernew.models.Pill
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PillsDataService @Inject constructor(
    private val supabase: SupabaseClient
) : IPillsDataService {

    private val TABLE_NAME = "Pills"

    override suspend fun getPills(): List<Pill> {
        return withContext(Dispatchers.IO) {
             supabase.from(TABLE_NAME).select().decodeList<Pill>()
        }
    }

    override suspend fun createPill(pill: Pill) {
        withContext(Dispatchers.IO) {
            supabase.from(TABLE_NAME).insert(pill)
        }
    }

    override suspend fun updatePill(pill: Pill) {
        withContext(Dispatchers.IO) {
            supabase.from(TABLE_NAME).update(
                {
                    Pill::name setTo pill.name
                    Pill::dosage_value setTo pill.dosage_value
                    Pill::dosage_unit setTo pill.dosage_unit
                    Pill::date_from setTo pill.date_from
                    Pill::date_to setTo pill.date_to
                    Pill::times setTo pill.times
                }
            ) {
                filter {
                    Pill::id eq pill.id
                }
            }
        }
    }

    override suspend fun deletePill(pill: Pill) {
        withContext(Dispatchers.IO) {
            supabase.from(TABLE_NAME).delete {
                filter {
                    Pill::id eq pill.id
                }
            }
        }
    }

}