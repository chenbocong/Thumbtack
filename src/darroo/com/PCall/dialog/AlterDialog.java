package darroo.com.PCall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import darroo.com.PCall.R;

public class AlterDialog {

	public static Dialog showAlertView(Context mContext, String title,
                                       String message, String positiveButton,
                                       final OnAlertViewClickListener positiveListener,
                                       String[] otherButtons,
                                       final OnAlertViewClickListener[] otherButtonListeners) {
        final Dialog dialog = new Dialog(mContext, R.style.alertView);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = (View) inflater.inflate(R.layout.alertview, null);

        TextView titleTextView = (TextView) view
                .findViewById(R.id.titleTextView);
        TextView messageTextView = (TextView) view
                .findViewById(R.id.messageTextView);
        if (null != title && !title.equalsIgnoreCase("")) {
            titleTextView.setText(title);
        } else {
            titleTextView.setVisibility(View.GONE);
        }
        if (null != message && !message.equalsIgnoreCase("")) {
            messageTextView.setText(message);
        } else {
            messageTextView.setVisibility(View.GONE);
        }

        Button pButton = new Button(mContext);
        pButton.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
        pButton.setTextColor(Color.argb(255, 0, 121, 255));

        // 鍔ㄦ�娣诲姞鎸夐挳
        LinearLayout buttonLayout = (LinearLayout) view
                .findViewById(R.id.buttonLayout);

        if (null == otherButtons || otherButtons.length == 0) {
            // 涓�釜鎸夐挳
            buttonLayout.setOrientation(LinearLayout.VERTICAL);

            pButton.setBackgroundResource(R.drawable.alert_bottom_button);

        } else if (null != otherButtons && otherButtons.length == 1) {
            // 涓や釜鎸夐挳
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            pButton.setBackgroundResource(R.drawable.alert_left_button);
        } else {
            // 涓変釜鎴栦笁涓互涓婃寜閽�            buttonLayout.setOrientation(LinearLayout.VERTICAL);
            pButton.setBackgroundResource(R.drawable.alert_middle_button);
        }

        pButton.setText(positiveButton);
        pButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != positiveListener) {
                    positiveListener.OnAlertViewClick();
                }
                dialog.dismiss();

            }

        });

        buttonLayout.addView(pButton);

        if (null != otherButtons && otherButtons.length > 0) {
            for (int i = 0; i < otherButtons.length; i++) {
                final int index = i;
                Button otherButton = new Button(mContext);
                otherButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
                otherButton.setText(otherButtons[i]);
                otherButton.setTextColor(Color.argb(255, 0, 121, 255));
                if (1 == otherButtons.length) {
                    otherButton
                            .setBackgroundResource(R.drawable.alert_right_button);
                } else if (i < (otherButtons.length - 1)) {
                    otherButton
                            .setBackgroundResource(R.drawable.alert_middle_button);
                } else {
                    otherButton
                            .setBackgroundResource(R.drawable.alert_bottom_button);
                }
                otherButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (null != otherButtonListeners
                                && null != otherButtonListeners[index]) {
                            otherButtonListeners[index].OnAlertViewClick();

                        }
                        dialog.dismiss();
                    }

                });

                buttonLayout.addView(otherButton);
            }
        }

        final int viewWidth = dip2px(mContext, 250);
        view.setMinimumWidth(viewWidth);

        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

	public interface OnAlertViewClickListener {
		void OnAlertViewClick();
	}

	private static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
