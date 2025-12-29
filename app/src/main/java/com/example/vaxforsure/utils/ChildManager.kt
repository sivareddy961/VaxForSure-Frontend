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
     */
    fun saveChild(context: Context, child: Child) {
        val children = getAllChildren(context).toMutableList()
        
        // Check if child with same ID exists, update it; otherwise add new
        val existingIndex = children.indexOfFirst { it.id == child.id }
        if (existingIndex >= 0) {
            children[existingIndex] = child
        } else {
            // Find the highest ID and increment
            val maxId = children.maxOfOrNull { it.id } ?: 0
            val newChild = child.copy(id = maxId + 1)
            children.add(newChild)
        }
        
        saveChildrenList(context, children)
    }
    
    /**
     * Get all children from SharedPreferences
     */
    fun getAllChildren(context: Context): List<Child> {
        val pref = getPrefs(context)
        val childrenJson = pref.getString(KEY_CHILDREN_LIST, null) ?: return emptyList()
        
        return try {
            val jsonArray = JSONArray(childrenJson)
            val children = mutableListOf<Child>()
            
            for (i in 0 until jsonArray.length()) {
                val childObj = jsonArray.getJSONObject(i)
                children.add(
                    Child(
                        id = childObj.getInt("id"),
                        user_id = childObj.getInt("user_id"),
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
            children
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

