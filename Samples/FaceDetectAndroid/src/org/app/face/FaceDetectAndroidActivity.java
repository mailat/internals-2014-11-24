package org.app.face;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FaceDetectAndroidActivity extends Activity 
{
	private static final int TAKE_PICTURE_CODE = 100;
	private static final int MAX_FACES = 5;
    public static final int GALLERY_REQUEST_CODE = 101;
	private Bitmap cameraBitmap = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
    }
    
    public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(0, 0, 0, "About").setIcon(R.drawable.about);
		return true;
	}
    
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case 0:
				displayAbout();
				return true;
			default:
				return false;
		}
	}

	private void displayAbout()
	{
		AboutDialog aboutDialog = new AboutDialog(this, this);
		aboutDialog.show();	
	}

	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    { 
		super.onActivityResult(requestCode, resultCode, data);
	
		if(resultCode == Activity.RESULT_OK)
		{
			if(TAKE_PICTURE_CODE == requestCode)
			{
				processCameraImage(data);
			}
			else 
				if(requestCode == GALLERY_REQUEST_CODE)  
		        {
					processGalleryImage(data);
		        } 
		}
	}

    private void openCamera()
    {
       	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(intent, TAKE_PICTURE_CODE);
    }
    
    private void openGallery()
    {
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
      
    private void processCameraImage(Intent intent)
    {
    	setContentView(R.layout.detectlayout);
    	ImageView imageView = (ImageView)findViewById(R.id.image_view);
    	cameraBitmap = (Bitmap)intent.getExtras().get("data");
    	imageView.setImageBitmap(cameraBitmap);
    }
    
    private void processGalleryImage(Intent intent)
    {
    	setContentView(R.layout.detectlayout);
    	ImageView imageView = (ImageView)findViewById(R.id.image_view);
    	Uri selectedImageUri = intent.getData();
    	String selectedImagePath = getPath(selectedImageUri);
    	BitmapFactory.Options options = new BitmapFactory.Options();
    	options.inSampleSize = 4;  
    	cameraBitmap = BitmapFactory.decodeFile( selectedImagePath, options );
    	imageView.setImageBitmap(cameraBitmap);
    }
   
    public String getPath(Uri uri)
	{
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
	public void btnChooseAPhoto_onClick(View v)
	{
		CustomizeDialog customizeDialog = new CustomizeDialog(this);
		customizeDialog.show();
	}
	
	public void btnRestart_onClick(View v)
	{
		CustomizeDialog customizeDialog = new CustomizeDialog(this);
		customizeDialog.show();
	}
	
	public void btnDetectFace_onClick(View v)
	{
		detectFaces();
	}
	
    private void detectFaces()
    {
    	if(null != cameraBitmap)
    	{
    		int width = cameraBitmap.getWidth();
    		int height = cameraBitmap.getHeight();
    		
    		FaceDetector detector = new FaceDetector(width, height, FaceDetectAndroidActivity.MAX_FACES);
    		Face[] faces = new Face[FaceDetectAndroidActivity.MAX_FACES];
    		
    		Bitmap bitmap565 = Bitmap.createBitmap(width, height, Config.RGB_565);
    		Paint ditherPaint = new Paint();
    		Paint drawPaint = new Paint();
    		  
    		ditherPaint.setDither(true);
    		drawPaint.setColor(Color.RED); 
    		drawPaint.setStyle(Paint.Style.STROKE);
    		drawPaint.setStrokeWidth(2);
    		   
    		Canvas canvas = new Canvas();
    		canvas.setBitmap(bitmap565);
    		canvas.drawBitmap(cameraBitmap, 0, 0, ditherPaint);
    		
    		int facesFound = detector.findFaces(bitmap565, faces);
    		
    		PointF midPoint = new PointF();
    		float eyeDistance = 0.0f;
    		float confidence = 0.0f;
    		
    		ArrayList<String> listConfidence  = new ArrayList<String>();
			ArrayList<String> listEyeDistances = new ArrayList<String>();
			ArrayList<String> listPoints = new ArrayList<String>(); 
		
			
    		if(facesFound > 0)
    		{
	    		for(int index=0; index<facesFound; ++index)
	    		{
	    			faces[index].getMidPoint(midPoint);
	    			eyeDistance = faces[index].eyesDistance();
	    			confidence = faces[index].confidence();
	    			
	    			Log.i("FaceDetector", 
	    					"Confidence: " + confidence + 
	    					", Eye distance: " + eyeDistance + 
	    					", Mid Point: (" + midPoint.x + ", " + midPoint.y + ")");
	    			
	    			listConfidence.add(String.format("%.3f", confidence) + "");
	    			listEyeDistances.add(String.format("%.3f", eyeDistance) + "");
	    			listPoints.add("(" + String.format("%.3f", midPoint.x) + ", " +String.format("%.3f", midPoint.y) + ")\n");
	    			
	    			// Draw a small rectangle frame around the eye 
	    			canvas.drawRect((int)midPoint.x - eyeDistance , 
	    							(int)midPoint.y - eyeDistance , 
	    							(int)midPoint.x + eyeDistance, 
	    							(int)midPoint.y + eyeDistance, drawPaint);
	    		}
    		}
    		else
    		{
    			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    	    	alertDialog.setTitle("Alert");
    	       	alertDialog.setMessage("No faces found!");
    	       	alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
    	       	{
    	       	    public void onClick(DialogInterface dialog, int which){}
    	     	});
    	       	alertDialog.setIcon(R.drawable.ic_app);
    	       	alertDialog.show();
    		}
    		
    		String filepath = Environment.getExternalStorageDirectory() + "/facedetect" + System.currentTimeMillis() + ".jpg";
    		Log.e("file path", filepath);
			try 
			{
				FileOutputStream fos = new FileOutputStream(filepath);
				
				bitmap565.compress(CompressFormat.JPEG, 90, fos);
				
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ImageView imageView = (ImageView)findViewById(R.id.image_view);
   		 	imageView.setImageBitmap(bitmap565);
			
			DetailsDialog detailsDialog = new DetailsDialog(this, this, facesFound + " faces", listConfidence.toString(), listEyeDistances.toString(), listPoints);
			detailsDialog.show();
			
    	}
    }
	

	public class CustomizeDialog extends Dialog implements OnClickListener 
	{
			Button cancelButton;
			Button takePhotoButton;
			Button chooseLibraryButton;
			 
			public CustomizeDialog(Context context) 
			{ 
				super(context);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.custom_dialog_photo);
				
				cancelButton = (Button) findViewById(R.id.btnCancel);
				cancelButton.setOnClickListener(this);
				
				takePhotoButton = (Button) findViewById(R.id.btnTakePhoto);
				takePhotoButton.setOnClickListener(this);
				
				chooseLibraryButton = (Button) findViewById(R.id.btnChoseLibrary);
				chooseLibraryButton.setOnClickListener(this);
			}

			public void onClick(View v) 
			{
				if (v == cancelButton)
				{
					dismiss();
				}  
				else
					if(v == takePhotoButton)
					{
						openCamera();
		                dismiss();
					}
					else 
						if(v == chooseLibraryButton) 
						{		
							openGallery();
                            dismiss();
						}
			}
	}
}