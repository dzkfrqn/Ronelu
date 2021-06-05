package com.example.ronelo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.ronelo.retrofit.RetrofitClient
import com.google.android.material.imageview.ShapeableImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import java.io.File


class MedicineActivity : AppCompatActivity() {

    private  val FILE_NAME = "image.jpg"
    private  val REQ_CODE_CAMERA = 120
    private lateinit var imgFile : File
    private lateinit var btnCamera: Button
    private lateinit var ivPic: ShapeableImageView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)

        supportActionBar?.title = "Medicine"
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        btnCamera = findViewById(R.id.btnCamera)
        ivPic = findViewById(R.id.ivPic)

        btnCamera.setOnClickListener {
            val toTakePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imgFile = getImageFile(FILE_NAME)
            btnCamera.setBackgroundColor(R.color.green)

//            toTakePicIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgFile)
            val fileProv = FileProvider.getUriForFile(this,"com.example.ronelo.fileprovider", imgFile )
            toTakePicIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileProv)

            try {
                startActivityForResult(toTakePicIntent,REQ_CODE_CAMERA)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this,"unable :"+e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        createPost()
    }

    private fun getImageFile(fileName: String): File {
        val DirectoryToStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",DirectoryToStorage)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_CAMERA && resultCode == Activity.RESULT_OK){
            val takenPhoto = BitmapFactory.decodeFile(imgFile.absolutePath)
            ivPic.setImageBitmap(takenPhoto)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showPosts(){

    }

    private fun createPost(){

        File file = // initialize file here
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(),
        RequestBody.create(MediaType.parse("image/*"), file));
        Call<MyResponse> call = api.uploadAttachment(filePart);

        RetrofitClient.instance.getDataPost(
            FILE_NAME,
            "Tiap tablet mengandung Amlodipine 5 mg",
            "Pasien yang hipersensitif terhadap amlodipine dan golongan dihydropirydine lainnya.",
        )
    }
//    private fun getUploadImage(){
//        var client = OkHttpClient().newBuilder()
//            .build()
//        var mediaType: MediaType = MediaType.parse("text/plain")!!
//        var body: RequestBody = Builder().setType(MultipartBody.FORM)
//            .addFormDataPart(
//                "image", "metformin.jpeg",
//                create(
//                    MediaType.parse("application/octet-stream"),
//                    File("/home/najie/Documents/ronelo/ronelo-backend/metformin.jpeg")
//                )
//            )
//            .build()
//        var request: Request = Builder()
//            .url("http://cfb6b2f9fd14.ngrok.io/upload_image")
//            .method("POST", body)
//            .build()
//        var response: Response = client.newCall(request).execute()
//    }




}