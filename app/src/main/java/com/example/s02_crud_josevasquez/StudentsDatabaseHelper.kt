package com.example.s02_crud_josevasquez

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentsDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "crudapp.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CODIGO = "codigo"
        private const val COLUMN_APELLIDOS = "apellidos"
        private const val COLUMN_NOMBRES = "nombres"
        private const val COLUMN_CORREO = "correo"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_CODIGO TEXT," +
                "$COLUMN_APELLIDOS TEXT," +
                "$COLUMN_NOMBRES TEXT," +
                "$COLUMN_CORREO TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val droptableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(droptableQuery)
        onCreate(db)
    }

    fun insertStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CODIGO, student.codigo)
            put(COLUMN_APELLIDOS, student.apellidos)
            put(COLUMN_NOMBRES, student.nombres)
            put(COLUMN_CORREO, student.correo)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllStudents(): List<Student> {
        val studentsList = mutableListOf<Student>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_APELLIDOS DESC"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val codigo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CODIGO))
            val apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDOS))
            val nombres = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRES))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO))

            val student = Student(id, codigo, apellidos, nombres, correo)
            studentsList.add(student)
        }
        cursor.close()
        db.close()
        return studentsList
    }

    fun updateStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CODIGO, student.codigo)
            put(COLUMN_APELLIDOS, student.apellidos)
            put(COLUMN_NOMBRES, student.nombres)
            put(COLUMN_CORREO, student.correo)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(student.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getStudentByID(studentID: Int): Student {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $studentID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val codigo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CODIGO))
        val apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDOS))
        val nombres = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRES))
        val correo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO))

        cursor.close()
        db.close()

        return Student(id, codigo, apellidos, nombres, correo)
    }

    fun deleteStudent(studentID: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(studentID.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getStudentsSorted(column: String, order: String): List<Student> {
        val studentsList = mutableListOf<Student>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $column $order"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val codigo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CODIGO))
            val apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDOS))
            val nombres = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRES))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO))

            studentsList.add(Student(id, codigo, apellidos, nombres, correo))
        }
        cursor.close()
        db.close()
        return studentsList
    }
}