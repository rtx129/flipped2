package cs371m.witchel.fcfragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {
    public interface ImageFragmentInterface {
        void imageFragmentFinish();
    }

    // We can have this member variable because it is initialized in onAttach
    // If the system kills us when memory is low, it will reattach and reinitialize this variable
    private ImageFragmentInterface imageFragmentInterface;

    // If create is false, log in screen and log in action, otherwise create account screen and action
    static ImageFragment newInstance(ImageFragmentInterface ifi, Bitmap bitmap) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle b = new Bundle();
        b.putParcelable("bitmap", bitmap);
        imageFragment.setArguments(b);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_fragment, container, false);
        return v;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            imageFragmentInterface = (ImageFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ImageInterface ");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        // XXX Write me.  I display a bitmap and if clicked, I disappear
        ImageView iv = (ImageView) v.findViewById(R.id.view_image_fragment);
        iv.setImageBitmap((Bitmap) this.getArguments().getParcelable("bitmap"));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PhotoFragment pf =(PhotoFragment) getActivity().getSupportFragmentManager().getFragments().get(0);
                ft.replace(R.id.main_fragment, pf);
                ft.commit();
            }
        });
    }
}
