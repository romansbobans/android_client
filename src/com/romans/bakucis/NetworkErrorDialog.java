package com.romans.bakucis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class NetworkErrorDialog extends DialogFragment {

//	private String message;

	private DialogAction listener;

	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		
		String message = getResources().getString(R.string.network_error_text);
		String retryText = getResources().getString(R.string.retry_btn);
		String goBackText = getResources().getString(R.string.go_back_btn);
		builder.setMessage(message).setPositiveButton(goBackText,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(getActivity().getBaseContext(),
								MainActivity.class);
						startActivity(i);
					}
				});
		builder.setNegativeButton(retryText,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						if (listener != null) {
							listener.retry();
						}
					}
				});

		return builder.create();

	}

	public static interface DialogAction {
		void retry();
	}

	public void setListener(DialogAction dialogInt) {
		this.listener = dialogInt;
	}
}
