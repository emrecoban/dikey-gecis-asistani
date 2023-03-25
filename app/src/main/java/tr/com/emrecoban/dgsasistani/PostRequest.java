package tr.com.emrecoban.dgsasistani;

/**
 * Created by Mr. SHEPHERD on 11/26/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://dgs.emrecoban.com.tr/comment/comRegister.php";
    private Map<String, String> params;

    public PostRequest(String username, String userpass, String yorum, String quoteID, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("post_userName", username);
        params.put("post_userPass", userpass);
        params.put("post_yorum", yorum);
        params.put("post_quoteID", quoteID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}