package eu.swipefit.swipefit.pinterest;
/**
 * FILE DESCRIPTION
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;

import java.util.ArrayList;
import java.util.List;

import eu.swipefit.swipefit.R;

/** ADD COMMENTS */
public class MyLoginActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinterest);

        //imports

        //inside onCreate
        PDKClient.configureInstance(this,"4908997071448582196");
        PDKClient.getInstance().onConnect(this);

        List scopes = new ArrayList<>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

        final PDKClient pdkClient = new PDKClient();

        /*pdkClient.login(this, scopes, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                //user logged in, use response.getUser() to get PDKUser object
                response.getBoard().getPinsCount();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });*/

        TextView button = (TextView) findViewById(R.id.muie);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PDKCallback pdkCallback = new PDKCallback();
                pdkClient.getBoardPins("487092584637312795","image",pdkCallback);
                Log.d("domething","something");
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PDKClient.getInstance().onOauthResponse(requestCode, resultCode, data);
    }
}
