package com.example.s02_crud_josevasquez

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s02_crud_josevasquez.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: StudentsDatabaseHelper
    private lateinit var studentsAdapter: StudentsAdapter

    private var currentSortOption = R.id.sort_by_lastname_desc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        db = StudentsDatabaseHelper(this)
        studentsAdapter = StudentsAdapter(db.getAllStudents(), this)

        binding.studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.studentsRecyclerView.adapter = studentsAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        studentsAdapter.refreshData(db.getAllStudents())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        menu.findItem(currentSortOption)?.isChecked = true
        val sortMenu = menu.findItem(R.id.sort_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        currentSortOption = item.itemId

        return when (item.itemId) {
            R.id.sort_by_code_asc -> {
                studentsAdapter.refreshData(db.getStudentsSorted("codigo", "ASC"))
                true
            }
            R.id.sort_by_code_desc -> {
                studentsAdapter.refreshData(db.getStudentsSorted("codigo", "DESC"))
                true
            }
            R.id.sort_by_lastname_asc -> {
                studentsAdapter.refreshData(db.getStudentsSorted("apellidos", "ASC"))
                true
            }
            R.id.sort_by_lastname_desc -> {
                studentsAdapter.refreshData(db.getStudentsSorted("apellidos", "DESC"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}