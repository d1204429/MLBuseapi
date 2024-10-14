package fcu;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class MLBDataApi {

    public static JSONObject getApiResponse(String apiUrl) throws Exception {
        JSONObject jsonResponse = null;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                jsonResponse = new JSONObject(response.toString());
//               JSONObject jsonResponse = new JSONObject(response.toString());
//                System.out.println("Divisions JSONDATA: " + jsonResponse); // 尚未方法化時列印jsonData查看資料樣式
//                List<TeamData> teamDataList = extractTeamData(jsonResponse);

            } else {
                System.out.println("GET request not worked" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return jsonResponse;
    }

//    private static List<TeamData> extractTeamData(JSONObject jsonResponse) {
//        List<TeamData> teamDataList = new ArrayList<>();
//
//        // 提取 records 數組
//        JSONArray records = jsonResponse.getJSONArray("records");
//
//        // 遍歷所有的分區
//        for (int i = 0; i < records.length(); i++) {
//            JSONObject record = records.getJSONObject(i);
//
//            // 提取 teamRecords 陣列
//            JSONArray teamRecords = record.getJSONArray("teamRecords");
//
//            // 遍歷每個 teamRecords 的資料
//            for (int j = 0; j < teamRecords.length(); j++) {
//                JSONObject teamRecord = teamRecords.getJSONObject(j);
//
//                // 提取資料
//                int division = teamRecord.getInt("division");
//                String name = teamRecord.getString("name");
//                String teamCode = teamRecord.getString("shortName");
//                int wins = teamRecord.getInt("wins");
//                int losses = teamRecord.getInt("losses");
//
//                // 勝率格式有時是字串，因此進行處理
//                double pct;
//                try {
//                    pct = teamRecord.getDouble("pct");
//                } catch (Exception e) {
//                    pct = Double.parseDouble(teamRecord.getString("pct"));
//                }
//
//                // 創建 TeamData 對象
//                TeamData teamData = new TeamData(division, name, teamCode, wins, losses, pct);
//                teamDataList.add(teamData);
//            }
//        }
//
//        return teamDataList;
//    }
}




