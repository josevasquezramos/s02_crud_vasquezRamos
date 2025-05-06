package com.example.s02_crud_josevasquez

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s02_crud_josevasquez.databinding.ActivityUpdateStudentBinding

class UpdateStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateStudentBinding
    private lateinit var db: StudentsDatabaseHelper
    private var studentID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = StudentsDatabaseHelper(this)

        studentID = intent.getIntExtra("student_id", -1)
        if (studentID == -1) {
            finish()
            return
        }

        val student = db.getStudentByID(studentID)
        binding.updateCodigoEditText.setText(student.codigo)
        binding.updateApellidosEditText.setText(student.apellidos)
        binding.updateNombresEditText.setText(student.nombres)
        binding.updateCorreoEditText.setText(student.correo)

        binding.updateSaveButton.setOnClickListener {
            val newCodigo = binding.updateCodigoEditText.text.toString().trim()
            val newApellidos = binding.updateApellidosEditText.text.toString().trim()
            val newNombres = binding.updateNombresEditText.text.toString().trim()
            val newCorreo = binding.updateCorreoEditText.text.toString().trim()

            if (
                newCodigo.isNotEmpty() && newApellidos.isNotEmpty() &&
                newNombres.isNotEmpty() && newCorreo.isNotEmpty()
            ) {
                val updatedStudent = Student(studentID, newCodigo, newApellidos, newNombres, newCorreo)
                db.updateStudent(updatedStudent)
                finish()
                Toast.makeText(this, "Cambios Guardados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}