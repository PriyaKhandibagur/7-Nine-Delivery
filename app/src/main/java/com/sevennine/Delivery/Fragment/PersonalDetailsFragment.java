package com.sevennine.Delivery.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sevennine.Delivery.Bean.User;
import com.sevennine.Delivery.R;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.sevennine.Delivery.general.FirebaseConstants.TABLE_USERS;


//Our class extending fragment
public class PersonalDetailsFragment extends Fragment {

    LinearLayout pick_up_arrow,back_feed,reached_loc;
    Fragment selectedFragment;
    TextView customer_address,name,mobile_no,joining_date;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private FirebaseUser mActiveUser;


    public static PersonalDetailsFragment newInstance() {
        PersonalDetailsFragment itemOnFragment = new PersonalDetailsFragment();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_details, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        name=view.findViewById(R.id.pname);
        mobile_no=view.findViewById(R.id.pmobile_no);
        joining_date=view.findViewById(R.id.pjoining_date);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ProfileFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhsksw");
                transaction.commit();
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("ONBACK", "keyCodezzzzzzzzzq  : " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("ONBACK", "onKey Back listener is working!!!");
                    selectedFragment = ProfileFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("dhsksw");
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });
        mFirebaseAuth = FirebaseAuth.getInstance();
        mActiveUser = mFirebaseAuth.getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

       // DatabaseReference databaseReference = mFirebaseDatabase.getReference(mFirebaseAuth.getUid());
        DatabaseReference databaseReference = mFirebaseDatabase.getReference("EUsa0q4TyXY3B027u4NmALKoBXs1");
     //  updateProfileDetails();

        if(mFirebaseAuth.getCurrentUser()!=null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User userProfile = dataSnapshot.child(TABLE_USERS).getValue(User.class);

                    name.setText(userProfile.getFirstName());
                    joining_date.setText(userProfile.getJoiningDate());
                    mobile_no.setText(userProfile.getMobileNumber());
                  //  mTextEmailAddress.setText(mActiveUser.getEmail());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // onError(databaseError.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }else{

        }

        return view;
        
    }
   /* View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSaveButton:
                    updateProfileDetails();
                    break;
                case R.id.update_imageView:
                    pickImageFromGallery();
                    break;
            }
        }
    };*/

    private void updateProfileDetails() {
//        showLoading();
       /* String name = mEditFirstName.getText().toString().trim();
        String joiningdate = mEditJoiningDate.getText().toString().trim();
        String mobile = mEditMobile.getText().toString().trim();*/
        User user = new User("Priyanka", "08-10-2020", "8861948875");

        mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child(TABLE_USERS).setValue(user);

        Toast.makeText(getActivity(), "Personal details updated successfully", Toast.LENGTH_SHORT).show();
        /*if (mSelectedImageUri == null) {
         //   showMessage(R.string.info_user_details_updated);
            Toast.makeText(getActivity(),"User details updated successfully",Toast.LENGTH_SHORT).show();
           // navigateHomeScreen();
            return;
        }*/

       /* StorageReference imageReference = mStorageReference.child(mFirebaseAuth.getUid()).child(DIRECTORY_PROFILE_PIC)
                .child(DEFAULT_PROFILE_PIC_NAME);
        UploadTask uploadTask = imageReference.putFile(mSelectedImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
               // onError(R.string.error_user_profile_pic_not_uploaded);
                Toast.makeText(getActivity(),"User Profile Pic not updated",Toast.LENGTH_SHORT).show();
               // navigateHomeScreen();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              //  showMessage(R.string.info_user_profile_pic_updated);
                Toast.makeText(getActivity(),"User Profile Pic updated successfully",Toast.LENGTH_SHORT).show();
               // navigateHomeScreen();
            }
        });*/
    }
}
