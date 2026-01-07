package com.example.vaxforsure.utils

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

object GoogleSignInHelper {
    
    /**
     * Get Google Sign-In Client
     */
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestId()
            .build()
        
        return GoogleSignIn.getClient(context, gso)
    }
    
    /**
     * Get sign-in intent
     */
    fun getSignInIntent(context: Context): Intent {
        val googleSignInClient = getGoogleSignInClient(context)
        return googleSignInClient.signInIntent
    }
    
    /**
     * Handle sign-in result
     */
    fun handleSignInResult(task: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        return try {
            val account = task.getResult(ApiException::class.java)
            account
        } catch (e: ApiException) {
            null
        }
    }
    
    /**
     * Get account from intent result
     */
    fun getAccountFromIntent(data: Intent?): GoogleSignInAccount? {
        if (data == null) return null
        
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        return handleSignInResult(task)
    }
}

