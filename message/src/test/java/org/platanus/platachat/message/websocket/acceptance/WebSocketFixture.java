package org.platanus.platachat.message.websocket.acceptance;

import org.json.JSONException;
import org.json.JSONObject;

public class WebSocketFixture {
    public static String getStandaloneSubscribeRequest() {
        return "{\"command\" : \"SUBSCRIBE\"," +
                "\"identifier\" : {\"channel\" : \"ROOM_ID\" , \"memberId\" : \"TEST_ID\", \"nickname\" : \"TEST1\", \"token\" : \"ABCD1234\"}" +
                "}";
    }

    public static String getStandaloneMessageRequest() {
        return "{\"command\" : \"MESSAGE\"," +
                "\"message\" : \"안녕하세요\"," +
                "\"identifier\" : {\"channel\" : \"ROOM_ID\" , \"memberId\" : \"TEST_ID\", \"nickname\" : \"TEST1\", \"token\" : \"ABCD1234\"}" +
                "}";
    }

    public static Object getStringFromJsonObject(JSONObject jsonObject, final String path) {
        try {
            return jsonObject.get(path);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getJsonObject(String payload) {
        try {
            return new JSONObject(payload);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSimpleSubscribeRequest() {
        return "{\"command\" : \"SUBSCRIBE\"," +
                "\"identifier\" : {\"channel\" : \"TEST_CHANNEL\" , \"nickname\" : \"TEST1\"}" +
                "}";
    }

    public static String getSimpleMessageRequest() {
        return "{\"command\" : \"MESSAGE\"," +
                "\"message\" : \"안녕하세요\"," +
                "\"identifier\" : {\"channel\" : \"TEST_CHANNEL\" , \"nickname\" : \"TEST1\"}" +
                "}";
    }
}
