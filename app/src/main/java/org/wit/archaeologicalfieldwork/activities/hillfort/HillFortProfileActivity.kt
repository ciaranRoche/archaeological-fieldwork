package org.wit.archaeologicalfieldwork.activities.hillfort

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.MenuItem
import android.view.Menu
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_hill_fort_profile.toolbarHillfortProfile
import kotlinx.android.synthetic.main.activity_hill_fort_profile.hillfortProfileName
import kotlinx.android.synthetic.main.activity_hill_fort_profile.hillfortProfileDescription
import kotlinx.android.synthetic.main.activity_hill_fort_profile.recyclerCommentView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.adapters.ViewPagerAdapter
import org.wit.archaeologicalfieldwork.views.user.profile.loggeduser
import org.wit.archaeologicalfieldwork.adapters.CommentAdapter
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.comment.CommentsModel
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.location.Location

class HillFortProfileActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    private lateinit var viewPager: ViewPager
    var location = Location(0.0, 0.0, 15f)
    var hillfort = HillfortModel()
    var comment = CommentsModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hill_fort_profile)

        toolbarHillfortProfile.title = "About Hillfort"

        setSupportActionBar(toolbarHillfortProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        if (intent.hasExtra("hillfort")) {
            hillfort = intent.extras.getParcelable<HillfortModel>("hillfort")
            hillfortProfileName.setText(hillfort.name)
            hillfortProfileDescription.setText(hillfort.description)
            location = hillfort.location

            viewPager = findViewById(R.id.view_pager)
            viewPager.adapter = ViewPagerAdapter(hillfort.images)

            val layoutManager = LinearLayoutManager(this)
            recyclerCommentView.layoutManager = layoutManager
            loadComments()
        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            commentDialog()
        }
    }

    private fun loadComments() {
        showComments(app.hillforts.findAllComments(hillfort))
    }

    fun showComments(comments: List<CommentsModel>) {
        recyclerCommentView.adapter = CommentAdapter(comments)
        recyclerCommentView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
//            R.id.item_edit -> startActivityForResult(intentFor<HillfortView>().putExtra("hillfort_edit", hillfort), 0)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun commentDialog() {
        val alert = AlertDialog.Builder(this)
        var editTextComment: EditText? = null

        with(alert) {
            setTitle("Leave a Comment")

            editTextComment = EditText(context)
            editTextComment!!.inputType = InputType.TYPE_CLASS_TEXT

            setPositiveButton("Add") { dialog, whichButton ->
                dialog.dismiss()
                comment.date = getDate()
                comment.comment = editTextComment!!.text.toString()
                comment.user = loggeduser.name
                hillfort.comments += comment
                app.hillforts.update(hillfort.copy())
                startActivityForResult(intentFor<HillFortProfileActivity>().putExtra("hillfort", hillfort), 0)
            }

            setNegativeButton("Ignore") { dialog, whichButton ->
                dialog.dismiss()
            }
        }

        // Dialog
        val dialog = alert.create()
        dialog.setView(editTextComment)
        dialog.show()
    }
}
