package com.example.vaxforsure.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.vaxforsure.screens.profile.Child
import org.json.JSONArray
import org.json.JSONObject

object ChildManager {
    private const val PREFS_NAME = "children_prefs"
    private const val KEY_CHILDREN_LIST = "children_list"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    /**
     * Save a child to the list of children
     * Removes duplicates based on name + date_of_birth before saving
     */
    fun saveChild(context: Context, child: Child) {
        val children = getAllChildren(context).toMutableList()
        
        // First, remove any existing duplicates based on name + date_of_birth (keep highest ID)
        val childKey = "${child.name.trim().lowercase()}_${child.date_of_birth}"
        val duplicates = children.filter { 
            "${it.name.trim().lowercase()}_${it.date_of_birth}" == childKey &&
            it.user_id == child.user_id
        }
        
        // Remove all duplicates
        children.removeAll(duplicates)
        
        // Check if child with same ID exists, update it
        val existingIndex = children.indexOfFirst { it.id == child.id }
        if (existingIndex >= 0) {
            children[existingIndex] = child
        } else {
            // Check if child with same name + date_of_birth exists (with different ID)
            val existingByName = children.find { 
                "${it.name.trim().lowercase()}_${it.date_of_birth}" == childKey &&
                it.user_id == child.user_id
            }
            
            if (existingByName != null) {
                // Update existing child if new one has higher ID or more complete data
                val existingIndex2 = children.indexOf(existingByName)
                if (child.id > existingByName.id || 
                    (child.birth_weight != null && existingByName.birth_weight == null) ||
                    (child.birth_height != null && existingByName.birth_height == null)) {
                    children[existingIndex2] = child
                }
            } else {
                // Add new child
                children.add(child)
            }
        }
        
        // Save cleaned list
        saveChildrenList(context, children)
    }
    
    /**
     * Remove duplicates from children list based on name and date of birth
     * Keeps the child with the highest ID (most recent)
     */
    fun removeDuplicates(context: Context): List<Child> {
        val allChildren = getAllChildren(context)
        val userId = PreferenceManager.getUserId(context)
        
        // Filter by user and remove invalid entries
        val validChildren = allChildren.filter { 
            it.user_id == userId && 
            it.user_id > 0 && 
            it.name.isNotBlank() && 
            it.date_of_birth.isNotBlank()
        }
        
        // Group by name + date_of_birth combination and keep only the one with highest ID
        val uniqueChildren = validChildren
            .groupBy { "${it.name.trim().lowercase()}_${it.date_of_birth}" }
            .mapValues { (_, children) -> 
                // Keep the one with highest ID (most recent)
                children.maxByOrNull { it.id } ?: children.first()
            }
            .values
            .toList()
        
        // Save cleaned list back
        if (uniqueChildren.size != validChildren.size) {
            saveChildrenList(context, uniqueChildren)
        }
        
        return uniqueChildren.sortedByDescending { it.created_at.ifEmpty { it.id.toString() } }
    }
    
    /**
     * Get all children from SharedPreferences filtered by user ID and remove duplicates
     */
    fun getAllChildren(context: Context, userId: Int = 0): List<Child> {
        val pref = getPrefs(context)
        val childrenJson = pref.getString(KEY_CHILDREN_LIST, null) ?: return emptyList()
        
        // If userId is provided and > 0, filter by user_id
        val currentUserId = if (userId > 0) userId else PreferenceManager.getUserId(context)
        
        return try {
            val jsonArray = JSONArray(childrenJson)
            val children = mutableListOf<Child>()
            
            for (i in 0 until jsonArray.length()) {
                val childObj = jsonArray.getJSONObject(i)
                val childUserId = childObj.getInt("user_id")
                
                // Only include children for the current user
                if (currentUserId > 0 && childUserId != currentUserId) {
                    continue
                }
                
                children.add(
                    Child(
                        id = childObj.getInt("id"),
                        user_id = childUserId,
                        name = childObj.getString("name"),
                        date_of_birth = childObj.getString("date_of_birth"),
                        gender = childObj.getString("gender"),
                        birth_weight = if (childObj.has("birth_weight") && !childObj.isNull("birth_weight")) {
                            childObj.getDouble("birth_weight")
                        } else null,
                        birth_height = if (childObj.has("birth_height") && !childObj.isNull("birth_height")) {
                            childObj.getDouble("birth_height")
                        } else null,
                        blood_group = if (childObj.has("blood_group") && !childObj.isNull("blood_group")) {
                            childObj.getString("blood_group")
                        } else null,
                        created_at = childObj.optString("created_at", ""),
                        updated_at = childObj.optString("updated_at", "")
                    )
                )
            }
            
            // Remove duplicates before returning
            val validChildren = children.filter { 
                it.user_id == currentUserId && 
                it.user_id > 0 && 
                it.name.isNotBlank() && 
                it.date_of_birth.isNotBlank()
            }
            
            // Group by name + date_of_birth and keep only one (with highest ID)
            val uniqueChildren = validChildren
                .groupBy { "${it.name.trim().lowercase()}_${it.date_of_birth}" }
                .mapValues { (_, childrenList) -> 
                    childrenList.maxByOrNull { it.id } ?: childrenList.first()
                }
                .values
                .toList()
                .sortedByDescending { it.created_at.ifEmpty { it.id.toString() } }
            
            // Save cleaned list if duplicates were found
            if (uniqueChildren.size != validChildren.size) {
                saveChildrenList(context, uniqueChildren)
            }
            
            uniqueChildren
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Save list of children to SharedPreferences
     */
    private fun saveChildrenList(context: Context, children: List<Child>) {
        val jsonArray = JSONArray()
        
        children.forEach { child ->
            val childObj = JSONObject().apply {
                put("id", child.id)
                put("user_id", child.user_id)
                put("name", child.name)
                put("date_of_birth", child.date_of_birth)
                put("gender", child.gender)
                put("birth_weight", child.birth_weight ?: JSONObject.NULL)
                put("birth_height", child.birth_height ?: JSONObject.NULL)
                put("blood_group", child.blood_group ?: JSONObject.NULL)
                put("created_at", child.created_at)
                put("updated_at", child.updated_at)
            }
            jsonArray.put(childObj)
        }
        
        getPrefs(context).edit()
            .putString(KEY_CHILDREN_LIST, jsonArray.toString())
            .apply()
    }
    
    /**
     * Delete a child by ID
     */
    fun deleteChild(context: Context, childId: Int) {
        val children = getAllChildren(context).toMutableList()
        children.removeAll { it.id == childId }
        saveChildrenList(context, children)
    }
    
    /**
     * Get child by ID
     */
    fun getChildById(context: Context, childId: Int): Child? {
        return getAllChildren(context).firstOrNull { it.id == childId }
    }
}

