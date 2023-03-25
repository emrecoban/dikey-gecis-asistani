package tr.com.emrecoban.dgsasistani;

/**
 * Created by Mr. SHEPHERD on 11/26/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class userRegister extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://dgs.emrecoban.com.tr/comment/userRegister.php";
    private Map<String, String> params;

    public userRegister(String buttonType, String userMAC, String TokenID, int Avatar, String userName, String userPass, String userRName, String userMail, String userCity, String userDep, String userPoint, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("post_button", buttonType);
        params.put("post_userMAC", userMAC);
        params.put("post_TokenID", TokenID);
        params.put("post_Avatar", Avatar + "");
        params.put("post_userName", userName);
        params.put("post_userPass", userPass);
        params.put("post_userRName", userRName);
        params.put("post_userMail", userMail);
        params.put("post_userCity", userCity);
        params.put("post_userDep", userDep);
        params.put("post_userPoint", userPoint);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}