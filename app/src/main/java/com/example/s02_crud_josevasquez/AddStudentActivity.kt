package com.example.s02_crud_josevasquez

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s02_crud_josevasquez.databinding.ActivityAddStudentBinding

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private lateinit var db: StudentsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = StudentsDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val codigo = binding.codigoEditText.text.toString().trim()
            val apellidos = binding.apellidosEditText.text.toString().trim()
            val nombres = binding.nombresEditText.text.toString().trim()
            val correo = binding.correoEditText.text.toString().trim()
            val student = Student(0, codigo, apellidos, nombres, correo)

            if (
                codigo.isNotEmpty() && apellidos.isNotEmpty() &&
                nombres.isNotEmpty() && correo.isNotEmpty()
            ) {
                db.insertStudent(student)
                finish()
                Toast.makeText(this, "Alumno Guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
