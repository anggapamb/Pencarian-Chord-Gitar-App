package com.anggapambudi.chordqu.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.anggapambudi.chordqu.R
import com.anggapambudi.chordqu.databinding.ActivityMainBinding
import com.anggapambudi.chordqu.model.ChordResponse
import com.anggapambudi.chordqu.retrofit.ApiService
import com.github.akshay_naik.texthighlighterapi.TextHighlighter
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null
    private lateinit var editTextJudul: EditText
    private lateinit var tvKunciGitar: TextView
    private lateinit var tvImageClear: ImageView
    private var resultAsli: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val v = binding.root
        setContentView(v)

        //set color kunci gitar
        val highlighter = TextHighlighter()

        highlighter.setColorForTheToken("C", "red")
        highlighter.setColorForTheToken("Cm", "red")
        highlighter.setColorForTheToken("C#", "blue")
        highlighter.setColorForTheToken("C#m", "blue")
        highlighter.setColorForTheToken("D", "red")
        highlighter.setColorForTheToken("Dm", "red")
        highlighter.setColorForTheToken("D#", "blue")
        highlighter.setColorForTheToken("D#m", "blue")
        highlighter.setColorForTheToken("E", "red")
        highlighter.setColorForTheToken("Em", "red")
        highlighter.setColorForTheToken("F", "red")
        highlighter.setColorForTheToken("Fm", "red")
        highlighter.setColorForTheToken("F#", "blue")
        highlighter.setColorForTheToken("F#m", "blue")
        highlighter.setColorForTheToken("G", "red")
        highlighter.setColorForTheToken("Gm", "red")
        highlighter.setColorForTheToken("G#", "blue")
        highlighter.setColorForTheToken("G#m", "blue")
        highlighter.setColorForTheToken("A", "red")
        highlighter.setColorForTheToken("Am", "red")
        highlighter.setColorForTheToken("A#", "blue")
        highlighter.setColorForTheToken("A#m", "blue")
        highlighter.setColorForTheToken("B", "red")
        highlighter.setColorForTheToken("Bm", "red")


        //set image clear
        if (binding.editTextJudulLagu.text.isEmpty()) {
            binding.imgClear.visibility = View.GONE
        } else {
            binding.imgClear.visibility = View.VISIBLE
        }
        binding.imgClear.onClick {
            binding.tvKunciGitar.visibility = View.GONE
            binding.editTextJudulLagu.text.clear()
        }


        //set progress diaolog
        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon tunggu...")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang menampilkan data")


        //button search
        binding.btnCariChord.onClick {
            val judulLagu = binding.editTextJudulLagu.text.toString()
            if (judulLagu.trim().isEmpty()) {
                toast("Judul lagu harus diisi")
            } else {

                editTextJudul = findViewById(R.id.editTextJudulLagu)
                tvKunciGitar = findViewById(R.id.tvKunciGitar)
                tvImageClear = findViewById(R.id.imgClear)

                val judulLagu = editTextJudul.text.trim().toString()

                progressDialog?.show()
                ApiService.instance.getChord(judulLagu).enqueue(object : Callback<ChordResponse> {
                    override fun onResponse(call: Call<ChordResponse>, response: Response<ChordResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            progressDialog?.dismiss()
                            val result = response.body()?.result

                            //get color kunci gitar
                            resultAsli = highlighter.getHighlightedText(result.toString())

                            tvKunciGitar.text = Html.fromHtml(resultAsli)

                            tvKunciGitar.visibility = View.VISIBLE
                            tvImageClear.visibility = View.VISIBLE

                            if (result.isNullOrEmpty()) {
                                toast("Chord tidak ditemukan")
                            }

                        }
                    }

                    override fun onFailure(call: Call<ChordResponse>, t: Throwable) {
                        progressDialog?.dismiss()
                        toast("Oops, internet anda bermasalah")
                    }

                })
                
            }
        }


    }
    

}



