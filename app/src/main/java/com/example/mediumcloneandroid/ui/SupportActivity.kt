package com.example.mediumcloneandroid.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediumcloneandroid.R
import com.example.mediumcloneandroid.adapters.WriterAdapter
import com.example.mediumcloneandroid.data.Writer
import kotlinx.android.synthetic.main.activity_support.*

class SupportActivity : AppCompatActivity() {

    private var black: Boolean = true

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)


        toolbar_writer.title = "Support Writers"
        setSupportActionBar(toolbar_writer)

        val listView = writer_list

        val authorList: ArrayList<Writer> = ArrayList()

        authorList.add(Writer("Ernest Hemingway", "https://www.biography.com/.image/t_share/MTIwNjA4NjMzODM5NjUwMzE2/ernest-hemingway-9334498-1-402.jpg"))
        authorList.add(Writer("Mark Twain", "https://bloximages.chicago2.vip.townnews.com/buffalonews.com/content/tncms/assets/v3/editorial/a/37/a37e5514-1c93-11eb-9ce4-f3974f90afe6/5ef2ea9547a84.image.jpg?resize=1200%2C1200"))
        authorList.add(Writer("Virginia Wolf", "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE4MDAzNDEwNzI4NzQ4NTU4/virginia-woolf-9536773-1-402.jpg"))
        authorList.add(Writer("Charles Dickens", "https://www.theschooloflife.com/thebookoflife/wp-content/uploads/2016/04/Dickens_Gurney_head.jpg"))
        authorList.add(Writer("William Faulkner", "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE5NDg0MDU0OTYxNzUxNTY3/william-faulkner-9292252-1-402.jpg"))
        authorList.add(Writer("Jane Austen", "https://images.gr-assets.com/authors/1588941810p8/1265.jpg"))
        authorList.add(Writer("James Joyce", "https://topofthetent.files.wordpress.com/2013/10/joyce.jpg"))
        authorList.add(Writer("George Orwell", "https://ctl.s6img.com/society6/img/hTwci3xnx46TylC8r0k6QrW0bG0/w_700/prints/~artwork/s6-0035/a/16244875_5658308/~~/george-orwell-526-prints.jpg"))
        authorList.add(Writer("John Steinbeck", "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTgwMzIxMzQxNzQyNzg1Njg2/gettyimages-613513778.jpg"))
        authorList.add(Writer("Agatha Cristie", "https://www.hallplace.org.uk/wp-content/uploads/2019/09/Agatha-Christie-Talk.jpg"))
        authorList.add(Writer("Kurt Vonnegut", "https://harvardreview.org/wp-content/uploads/2019/03/kurt-vonnegut-9520329-1-402.jpg"))
        authorList.add(Writer("Maya Angelou", "https://shereads.com/wp-content/uploads/2021/04/maya_angelou-800x800.jpg"))
        authorList.add(Writer("J. K. Rowling", "https://thumbor.forbes.com/thumbor/fit-in/416x416/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5ed56d114231c70006c8f37b%2F0x0.jpg%3Fbackground%3D000000%26cropX1%3D0%26cropX2%3D654%26cropY1%3D96%26cropY2%3D750"))


        listView.layoutManager = LinearLayoutManager(applicationContext)
        listView.setHasFixedSize(true)
        listView.adapter = WriterAdapter(authorList)



    }

}