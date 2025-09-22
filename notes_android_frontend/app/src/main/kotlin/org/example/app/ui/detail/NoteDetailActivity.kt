package org.example.app.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import org.example.app.R
import org.example.app.model.Note
import org.example.app.viewmodel.NoteViewModel

/**
 PUBLIC_INTERFACE
 NoteDetailActivity
 Displays and edits a single note. If EXTRA_NOTE_ID is provided, edits existing note.
*/
class NoteDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private var currentNoteId: Long? = null

    private lateinit var inputTitle: EditText
    private lateinit var inputContent: EditText
    private lateinit var btnDelete: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_detail)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        inputTitle = findViewById(R.id.inputTitle)
        inputContent = findViewById(R.id.inputContent)
        btnDelete = findViewById(R.id.btnDelete)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        currentNoteId = intent.getLongExtra(EXTRA_NOTE_ID, -1L).takeIf { it != -1L }
        if (currentNoteId != null) {
            supportActionBar?.title = getString(R.string.edit_note)
            viewModel.getById(currentNoteId!!).observe(this) { note ->
                if (note != null) {
                    inputTitle.setText(note.title ?: "")
                    inputContent.setText(note.content ?: "")
                }
            }
        } else {
            supportActionBar?.title = getString(R.string.new_note)
        }

        btnDelete.isVisible = currentNoteId != null
        btnDelete.setOnClickListener {
            currentNoteId?.let { id ->
                viewModel.getByIdOnce(id)?.let {
                    viewModel.delete(it)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.action_save -> { saveNote(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = inputTitle.text?.toString()?.trim().orEmpty()
        val content = inputContent.text?.toString()?.trim().orEmpty()

        if (title.isBlank() && content.isBlank()) {
            Toast.makeText(this, getString(R.string.nothing_to_save), Toast.LENGTH_SHORT).show()
            return
        }

        if (currentNoteId == null) {
            viewModel.insert(Note(title = title, content = content))
        } else {
            viewModel.update(Note(id = currentNoteId!!, title = title, content = content))
        }
        finish()
    }

    companion object {
        const val EXTRA_NOTE_ID = "extra_note_id"
    }
}
