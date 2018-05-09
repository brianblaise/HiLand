package com.example.brianb.demo1;

import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RenterSingleView extends AppCompatActivity {
    private final int THUMBSIZE = 96;
    ImageView renterViewImage;
    TextView renterViewName, renterViewAddress, renterViewDate, renterViewEmail, renterViewPhoneNo, renterViewHseNumber, imageString;

    private Renter renter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_single_view);


        renterViewImage = (ImageView)findViewById(R.id.renterViewImage);
        renterViewName = (TextView)findViewById(R.id.renterViewName);
        renterViewAddress = (TextView)findViewById(R.id.renterViewAddress);
        renterViewDate = (TextView)findViewById(R.id.renterViewDate);
        renterViewEmail = (TextView)findViewById(R.id.renterViewEmail);
        renterViewPhoneNo = (TextView)findViewById(R.id.renterViewPhoneNo);
        renterViewHseNumber = (TextView)findViewById(R.id.renterViewHseNumber);
        imageString = (TextView)findViewById(R.id.imageString);

        DbRenter dbRenter = new DbRenter(this);
        dbRenter.open();

        renter = dbRenter.getRenter(getIntent().getIntExtra("ID", 0)); //Does same task as getRenterItem

        String houseNumber = Integer.toString(renter.getrHouseNumber());

        renterViewImage.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(renter.getrPath()),
                THUMBSIZE, THUMBSIZE));
        imageString.setText(renter.getrPath());
        renterViewName.setText(renter.getrName());
        renterViewAddress.setText(renter.getrAddress());
        renterViewDate.setText(renter.getrDate());
        renterViewEmail.setText(renter.getrEmail());
        renterViewPhoneNo.setText(renter.getrPhone());
        renterViewHseNumber.setText(houseNumber);

        dbRenter.close();
    }
}
