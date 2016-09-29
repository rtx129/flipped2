package cs371m.witchel.fcfragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoFragment extends Fragment {
    protected Button takeAPictureButton;
    // for holding photos
    protected ImageView[] imageViews;
    // Request code for camera
    private static final int CAMERA_REQUEST = 1888;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the root view and cache references to vital UI elements
        View v = inflater.inflate(R.layout.photo_fragment, container, false);
        takeAPictureButton = (Button) v.findViewById(R.id.takeAPictureButton);
        imageViews = new ImageView[4];

        imageViews[0] = (ImageView)v.findViewById(R.id.image0);
        imageViews[1] = (ImageView)v.findViewById(R.id.image1);
        imageViews[2] = (ImageView)v.findViewById(R.id.image2);
        imageViews[3] = (ImageView)v.findViewById(R.id.image3);
        return v;
    }

    protected void imageToImageButton(final ImageView imageView ) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable d = (BitmapDrawable)((ImageView)v).getDrawable();
                if (d == null)
                    return;
                Bitmap bitmap = d.getBitmap();
                // XXX write me.  Something about an ImageFragment

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ImageFragment ifg = ImageFragment.newInstance((ImageFragment.ImageFragmentInterface) getActivity(), bitmap);
                ft.add(R.id.main_fragment, ifg);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        takeAPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureButtonPressed(v);
            }
        });

        imageToImageButton(imageViews[0]);
        imageToImageButton(imageViews[1]);
        imageToImageButton(imageViews[2]);
        imageToImageButton(imageViews[3]);
    }

    private void rotate_images(Bitmap photo) {
        for (int i = imageViews.length - 1; i > 0; i--)
            imageViews[i].setImageDrawable(imageViews[i-1].getDrawable());
        imageViews[0].setImageBitmap(photo);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            rotate_images(photo);
        }
    }

    public void pictureButtonPressed(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
}
