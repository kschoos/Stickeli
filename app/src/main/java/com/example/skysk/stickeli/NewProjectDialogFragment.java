package com.example.skysk.stickeli;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewProjectDialogFragment extends DialogFragment implements CameraHelper.CameraListener {

    NewProjectDialogFragmentListener mListener;
    AlertDialog mDialog;
    boolean mPictureTaken = false;

    @Override
    public void onPictureSuccess() {
        Bitmap thumb = CameraHelper.getInstance().GetThumbnailFromLastPicture(250);
        ImageView thumbImage = mDialog.findViewById(R.id.new_project_dialog_picture);
        thumbImage.setPadding(1, 1, 1, 1);
        thumbImage.setImageBitmap(thumb);
        mPictureTaken = true;
    }

    public interface NewProjectDialogFragmentListener {
        public void onDialogPositiveClick(DialogFragment pDialog, String pName, long pCount, boolean pPictureTaken);
        public void onDialogNegativeClick(DialogFragment pDialog);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (NewProjectDialogFragmentListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() +
            " must implement NewProjectDialogFragmentListener");
        }
    }


    /***
     * We override onResume in order to modifiy the default onClickHandler which dismisses
     * the Dialog every time a button is clicked.
     * Now we can dismiss whenever we are ready, wooo <3
     */
    @Override
    public void onResume()
    {
       super.onResume();
       mDialog = (AlertDialog) getDialog();


       if(mDialog != null)
       {
           Button photoButton = (Button) mDialog.getButton(Dialog.BUTTON_NEUTRAL);
           photoButton.setTextColor(getResources().getColor(R.color.colorPrimary));

           photoButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View pView) {
                   CameraHelper.TakePicture(NewProjectDialogFragment.this.getActivity());
               }
           });

           Button positiveButton = (Button) mDialog.getButton(Dialog.BUTTON_POSITIVE);
           positiveButton.setTextColor(getResources().getColor(R.color.colorPrimary));

           positiveButton.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View pView) {
                   EditText nameEditText = mDialog.findViewById(R.id.new_project_dialog_project_name);
                   EditText countEditText = mDialog.findViewById(R.id.new_project_dialog_count);

                   String name = nameEditText.getText().toString();
                   String countString = countEditText.getText().toString();
                   long count = 0;


                   if (name.length() == 0) {
                       Toast.makeText(getContext(), getContext().getString(R.string.toast_enter_name), Toast.LENGTH_LONG).show();
                       return;
                   }

                   if (countString.length() > 0)
                   {
                       count = Long.parseLong(countEditText.getText().toString());
                   }

                   mListener.onDialogPositiveClick(NewProjectDialogFragment.this,
                           nameEditText.getText().toString(),
                           count,
                           mPictureTaken
                           );

                   mDialog.dismiss();
               }
           });
       }
    }

    @Override
    public Dialog onCreateDialog(Bundle pSavedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.new_project_title));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_new_project_dialog, null));

        // The actual onClickListener will be set later.
        builder.setPositiveButton(R.string.ok, null);
        builder.setNeutralButton(R.string.take_photo, null);
        return builder.create();
    }
}
