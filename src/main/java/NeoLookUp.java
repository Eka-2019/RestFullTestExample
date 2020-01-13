
import org.json.*;

public class NeoLookUp {
    JSONObject obj = new JSONObject();
    String self = obj.getJSONObject("links").getString("self");

    JSONArray arr = obj.getJSONArray("estimated_diameter");
//    for (int i=0; i < arr.length(); i++){
//        String kilometers = arr.getJSONObject(i).getString("kilometers");
//
//    }


    public NeoLookUp() throws JSONException {
    }
}
