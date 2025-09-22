package org.example.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.example.app.R
import org.example.app.ui.detail.NoteDetailActivity
import org.example.app.viewmodel.NoteViewModel

/**
 * PUBLIC_INTERFACE
 * MainActivity
 * Entry screen: shows list of notes with add button and optional bottom navigation.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val recycler: RecyclerView = findViewById(R.id.recyclerNotes)
        adapter = NotesAdapter(
            onClick = { note ->
                startActivity(Intent(this, NoteDetailActivity::class.java).apply {
                    putExtra(NoteDetailActivity.EXTRA_NOTE_ID, note.id)
                })
            },
            onLongClick = { note ->
                viewModel.delete(note)
                true
            }
        )
        val lm = LinearLayoutManager(this)
        recycler.layoutManager = lm
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(this, lm.orientation))

        val emptyState: View = findViewById(R.id.root) // from view_empty_state include with id root
        viewModel.notes.observe(this) { notes ->
            adapter.submitList(notes)
            emptyState.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        }

        val fab: FloatingActionButton = findViewById(R.id.fabAdd)
        fab.setOnClickListener {
            startActivity(Intent(this, NoteDetailActivity::class.java))
        }

        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            it.itemId == R.id.nav_notes
        }
        bottomNav.selectedItemId = R.id.nav_notes
    }
}
