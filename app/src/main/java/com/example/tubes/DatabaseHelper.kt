package com.example.tubes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context,
        DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_USERNAME + " TEXT,"
                + COL_EMAIL + " TEXT," + COL_PASSWORD + " TEXT" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        //Drop User Table if exist
        db?.execSQL(dropTable)
        // Create tables again
        onCreate(db)
    }

    fun readData(): MutableList<User>{
        var list: MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                var user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.username = result.getString(result.getColumnIndex(COL_USERNAME))
                user.email = result.getString(result.getColumnIndex(COL_EMAIL))
                user.password = result.getString(result.getColumnIndex(COL_PASSWORD))
                list.add(user)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return  list
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_EMAIL, user.email)
        values.put(COL_PASSWORD, user.password)
        // Inserting Row
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_EMAIL, user.email)
        values.put(COL_PASSWORD, user.password)
        // updating row
        db.update(TABLE_NAME, values, "$COL_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }

    fun deleteUser(user: User) {
        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_NAME, "$COL_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }

    fun checkUser(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COL_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COL_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(TABLE_NAME, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }


    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COL_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COL_EMAIL = ? AND $COL_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TABLE_NAME, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }

    companion object {
        // Database Version
        private val DATABASE_VERSION = 1
        // Database Name
        private val DATABASE_NAME = "MyDB"
        // User table name
        val TABLE_NAME = "User"
        // User Table Columns names
        private val COL_ID = "id"
        private val COL_USERNAME = "username"
        private val COL_EMAIL = "email"
        private val COL_PASSWORD = "password"

    }
}