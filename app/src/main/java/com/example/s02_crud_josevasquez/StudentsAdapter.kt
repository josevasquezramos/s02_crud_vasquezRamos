package com.example.s02_crud_josevasquez

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class StudentsAdapter(private var students: List<Student>, context: Context) :
    RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    private val db: StudentsDatabaseHelper = StudentsDatabaseHelper(context)
    private val context: Context = context

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codigoTextView: TextView = itemView.findViewById(R.id.codigoTextView)
        val apellidosTextView: TextView = itemView.findViewById(R.id.apellidosTextView)
        val nombresTextView: TextView = itemView.findViewById(R.id.nombresTextView)
        val correoTextView: TextView = itemView.findViewById(R.id.correoTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.codigoTextView.text = student.codigo
        holder.apellidosTextView.text = student.apellidos
        holder.nombresTextView.text = student.nombres
        holder.correoTextView.text = student.correo
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateStudentActivity::class.java).apply {
                putExtra("student_id", student.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(student.id)
        }
    }

    private fun showDeleteConfirmationDialog(studentId: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar este alumno?")

        builder.setPositiveButton("Eliminar") { dialog, which ->
            db.deleteStudent(studentId)
            refreshData(db.getAllStudents())
            Toast.makeText(context, "Alumno eliminado", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun refreshData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }
}