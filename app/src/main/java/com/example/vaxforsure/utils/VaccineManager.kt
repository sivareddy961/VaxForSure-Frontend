package com.example.vaxforsure.utils

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject
import org.json.JSONArray

object VaccineManager {
    private const val PREFS_NAME = "vaccines_prefs"
    private const val KEY_VACCINE_STATUS = "vaccine_status"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    /**
     * Mark a vaccine as completed for a specific child
     * @param context Android context
     * @param childId ID of the child
     * @param vaccineName Name of the vaccine (e.g., "BCG", "Hepatitis B (1st dose)")
     * @param dateAdministered Date when vaccine was administered
     * @param healthcareFacility Healthcare facility name
     * @param batchLotNumber Batch/Lot number
     * @param notes Additional notes
     */
    fun markVaccineCompleted(
        context: Context,
        childId: Int,
        vaccineName: String,
        dateAdministered: String,
        healthcareFacility: String = "",
        batchLotNumber: String = "",
        notes: String = ""
    ) {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        
        try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (!statusObj.has(childKey)) {
                statusObj.put(childKey, JSONObject())
            }
            
            val childVaccines = statusObj.getJSONObject(childKey)
            val vaccineKey = vaccineName.replace(" ", "_").replace("(", "").replace(")", "")
            
            val vaccineData = JSONObject().apply {
                put("status", "completed")
                put("date_administered", dateAdministered)
                put("healthcare_facility", healthcareFacility)
                put("batch_lot_number", batchLotNumber)
                put("notes", notes)
                put("completed_at", System.currentTimeMillis())
            }
            
            childVaccines.put(vaccineKey, vaccineData)
            statusObj.put(childKey, childVaccines)
            
            pref.edit()
                .putString(KEY_VACCINE_STATUS, statusObj.toString())
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Check if a vaccine is completed for a specific child
     * @param context Android context
     * @param childId ID of the child
     * @param vaccineName Name of the vaccine
     * @return true if completed, false otherwise
     */
    fun isVaccineCompleted(context: Context, childId: Int, vaccineName: String): Boolean {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        
        return try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (!statusObj.has(childKey)) {
                return false
            }
            
            val childVaccines = statusObj.getJSONObject(childKey)
            val vaccineKey = vaccineName.replace(" ", "_").replace("(", "").replace(")", "")
            
            if (!childVaccines.has(vaccineKey)) {
                return false
            }
            
            val vaccineData = childVaccines.getJSONObject(vaccineKey)
            vaccineData.getString("status") == "completed"
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Get vaccine status for a specific child
     * @param context Android context
     * @param childId ID of the child
     * @param vaccineName Name of the vaccine
     * @return VaccineStatus object with status and details, or null if not found
     */
    fun getVaccineStatus(
        context: Context,
        childId: Int,
        vaccineName: String
    ): VaccineStatus? {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        
        return try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (!statusObj.has(childKey)) {
                return VaccineStatus("pending", "", "", "", "")
            }
            
            val childVaccines = statusObj.getJSONObject(childKey)
            val vaccineKey = vaccineName.replace(" ", "_").replace("(", "").replace(")", "")
            
            if (!childVaccines.has(vaccineKey)) {
                return VaccineStatus("pending", "", "", "", "")
            }
            
            val vaccineData = childVaccines.getJSONObject(vaccineKey)
            VaccineStatus(
                status = vaccineData.optString("status", "pending"),
                dateAdministered = vaccineData.optString("date_administered", ""),
                healthcareFacility = vaccineData.optString("healthcare_facility", ""),
                batchLotNumber = vaccineData.optString("batch_lot_number", ""),
                notes = vaccineData.optString("notes", "")
            )
        } catch (e: Exception) {
            VaccineStatus("pending", "", "", "", "")
        }
    }
    
    /**
     * Get all completed vaccines for a specific child
     * @param context Android context
     * @param childId ID of the child
     * @return List of vaccine names that are completed
     */
    fun getCompletedVaccines(context: Context, childId: Int): List<String> {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        val completedVaccines = mutableListOf<String>()
        
        return try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (!statusObj.has(childKey)) {
                return emptyList()
            }
            
            val childVaccines = statusObj.getJSONObject(childKey)
            val keys = childVaccines.keys()
            
            while (keys.hasNext()) {
                val vaccineKey = keys.next()
                val vaccineData = childVaccines.getJSONObject(vaccineKey)
                
                if (vaccineData.optString("status", "pending") == "completed") {
                    // Convert key back to readable name
                    val vaccineName = vaccineKey.replace("_", " ")
                        .replace("1st", "(1st)")
                        .replace("2nd", "(2nd)")
                        .replace("3rd", "(3rd)")
                        .replace("dose", "dose)")
                    completedVaccines.add(vaccineName)
                }
            }
            
            completedVaccines
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Get all pending vaccines for a specific child (based on standard schedule)
     * @param context Android context
     * @param childId ID of the child
     * @param allVaccines List of all vaccines in the schedule
     * @return List of vaccine names that are pending
     */
    fun getPendingVaccines(
        context: Context,
        childId: Int,
        allVaccines: List<String>
    ): List<String> {
        val completedVaccines = getCompletedVaccines(context, childId)
        return allVaccines.filter { vaccine ->
            !completedVaccines.contains(vaccine) && 
            !isVaccineCompleted(context, childId, vaccine)
        }
    }
    
    /**
     * Get all vaccination records for a specific child
     * @param context Android context
     * @param childId ID of the child
     * @return List of VaccinationRecord objects
     */
    fun getVaccinationRecords(context: Context, childId: Int): List<VaccinationRecord> {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        val records = mutableListOf<VaccinationRecord>()
        
        return try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (!statusObj.has(childKey)) {
                return emptyList()
            }
            
            val childVaccines = statusObj.getJSONObject(childKey)
            val keys = childVaccines.keys()
            
            while (keys.hasNext()) {
                val vaccineKey = keys.next()
                val vaccineData = childVaccines.getJSONObject(vaccineKey)
                
                if (vaccineData.optString("status", "pending") == "completed") {
                    // Convert key back to readable name
                    val vaccineName = vaccineKey.replace("_", " ")
                        .replace("1st", "(1st)")
                        .replace("2nd", "(2nd)")
                        .replace("3rd", "(3rd)")
                        .replace("dose", "dose)")
                        .replace("Booster", "(Booster)")
                        .replace("Final", "(Final)")
                    
                    records.add(
                        VaccinationRecord(
                            childId = childId,
                            vaccineName = vaccineName,
                            dateAdministered = vaccineData.optString("date_administered", ""),
                            healthcareFacility = vaccineData.optString("healthcare_facility", ""),
                            batchLotNumber = vaccineData.optString("batch_lot_number", ""),
                            notes = vaccineData.optString("notes", ""),
                            completedAt = vaccineData.optLong("completed_at", 0)
                        )
                    )
                }
            }
            
            // Sort by completion date (newest first)
            return records.sortedByDescending { it.completedAt }
        } catch (e: Exception) {
            return emptyList()
        }
    }
    
    /**
     * Get all vaccination records for all children
     * @param context Android context
     * @return Map of childId to list of VaccinationRecord objects
     */
    fun getAllVaccinationRecords(context: Context): Map<Int, List<VaccinationRecord>> {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        val recordsMap = mutableMapOf<Int, List<VaccinationRecord>>()
        
        return try {
            val statusObj = JSONObject(statusJson)
            val keys = statusObj.keys()
            
            while (keys.hasNext()) {
                val childKey = keys.next()
                if (childKey.startsWith("child_")) {
                    val childId = childKey.replace("child_", "").toIntOrNull() ?: continue
                    recordsMap[childId] = getVaccinationRecords(context, childId)
                }
            }
            
            recordsMap
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    /**
     * Delete a vaccination record for a specific child
     * @param context Android context
     * @param childId ID of the child
     * @param vaccineName Name of the vaccine to delete
     */
    fun deleteVaccinationRecord(context: Context, childId: Int, vaccineName: String) {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        
        try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (!statusObj.has(childKey)) {
                return
            }
            
            val childVaccines = statusObj.getJSONObject(childKey)
            val vaccineKey = vaccineName.replace(" ", "_").replace("(", "").replace(")", "")
            
            if (childVaccines.has(vaccineKey)) {
                childVaccines.remove(vaccineKey)
                statusObj.put(childKey, childVaccines)
                
                pref.edit()
                    .putString(KEY_VACCINE_STATUS, statusObj.toString())
                    .apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Delete all vaccination records for a specific child
     * @param context Android context
     * @param childId ID of the child
     */
    fun deleteAllVaccinationRecordsForChild(context: Context, childId: Int) {
        val pref = getPrefs(context)
        val statusJson = pref.getString(KEY_VACCINE_STATUS, "{}") ?: "{}"
        
        try {
            val statusObj = JSONObject(statusJson)
            val childKey = "child_$childId"
            
            if (statusObj.has(childKey)) {
                statusObj.remove(childKey)
                
                pref.edit()
                    .putString(KEY_VACCINE_STATUS, statusObj.toString())
                    .apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * Data class to hold vaccine status information
 */
data class VaccineStatus(
    val status: String, // "pending" or "completed"
    val dateAdministered: String,
    val healthcareFacility: String,
    val batchLotNumber: String,
    val notes: String
)

/**
 * Data class to hold vaccination record information
 */
data class VaccinationRecord(
    val childId: Int,
    val vaccineName: String,
    val dateAdministered: String,
    val healthcareFacility: String,
    val batchLotNumber: String,
    val notes: String,
    val completedAt: Long
)

