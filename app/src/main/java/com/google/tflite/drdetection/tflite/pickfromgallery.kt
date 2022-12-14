package com.google.tflite.drdetection.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.tflite.catvsdog.R
import com.google.tflite.catvsdog.tflite.Classifier
import kotlinx.android.synthetic.main.activity_pick.*
import java.util.jar.Manifest

class PickImgFromGallery : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick)

        val textView = findViewById<TextView>(R.id.Result_text)
        //BUTTON CLICK
        gall_pick_button.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //Show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission granted
                    pickImageFromGallery()
                }
            }
            else{
                //system is < Marshmallow
                pickImageFromGallery()

            }
            textView.setText("").toString()
        }
       
        initViews()

    }

    private fun pickImageFromGallery() {
        //INTENT TO PICK IMAGE
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    companion object {
        //image pick  code
        private val IMAGE_PICK_CODE = 1000;
        //permission code
        private  val PERMISSION_CODE = 1001;
    }

    //handle permission result

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this,"Permission Denied!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            gall_img.setImageURI(data?.data)
        }
    
    }
    }


}
