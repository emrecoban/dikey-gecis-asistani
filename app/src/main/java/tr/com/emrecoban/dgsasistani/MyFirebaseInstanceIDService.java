package tr.com.emrecoban.dgsasistani;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Mr. SHEPHERD on 31.05.2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
        Log.d("TOKEN Verildi", token);
    }

    private void registerToken(String token){

    }}
