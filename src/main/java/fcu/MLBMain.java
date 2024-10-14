package fcu;

import java.util.Map;
import org.json.JSONObject;
import java.util.List;

public class MLBMain {
  private static final String API_URL = "https://statsapi.mlb.com/api/v1/standings?leagueId=103,104&season=2023&standingsTypes=regularSeason&hydrate=division,team(division)";

  public static void main(String[] args) {
    try {
      JSONObject apiResponse = MLBDataApi.getApiResponse(API_URL);
      List<TeamData> teamDataList = MLBDataProcessor.extractTeamData(apiResponse);
      Map<String, List<TeamData>> classifiedTeams = MLBDataProcessor.classifyTeams(teamDataList);
      System.out.println("(AMERICAN LEAGUE)");
      System.out.printf("%-3s %d -----\n", classifiedTeams.get("AL").get(5).getName(), 6); // 第6種子
      System.out.printf("%-3s %d ----- ? -----\n", classifiedTeams.get("AL").get(2).getName(), 3); // 第3種子對第6種子
      System.out.printf("        %-3s %d ----- ?\n", classifiedTeams.get("AL").get(0).getName(), 1); // 第1種子輪空
      System.out.printf("%-3s %d -----\n", classifiedTeams.get("AL").get(4).getName(), 5); // 第5種子
      System.out.printf("%-3s %d ----- ? -----\n", classifiedTeams.get("AL").get(3).getName(), 4); // 第4種子對第5種子
      System.out.printf("        %-3s %d -----\n", classifiedTeams.get("AL").get(1).getName(), 2); // 第2種子
      System.out.println("                               ---- ?");

      System.out.println("(NATIONAL LEAGUE)");
      System.out.printf("%-3s %d -----\n", classifiedTeams.get("NL").get(5).getName(), 6); // 第6種子
      System.out.printf("%-3s %d ----- ? -----\n", classifiedTeams.get("NL").get(2).getName(), 3); // 第3種子對第6種子
      System.out.printf("        %-3s %d ----- ?\n", classifiedTeams.get("NL").get(0).getName(), 1); // 第1種子輪空
      System.out.printf("%-3s %d -----\n", classifiedTeams.get("NL").get(4).getName(), 5); // 第5種子
      System.out.printf("%-3s %d ----- ? -----\n", classifiedTeams.get("NL").get(3).getName(), 4); // 第4種子對第5種子
      System.out.printf("        %-3s %d -----\n", classifiedTeams.get("NL").get(1).getName(), 2); // 第2種子


//      System.out.println("美國聯盟球隊（分區冠軍後跟前三名非冠軍）：");
//      classifiedTeams.get("AL").forEach(System.out::println);
//
//      System.out.println("\n國家聯盟球隊（分區冠軍後跟前三名非冠軍）：");
//      classifiedTeams.get("NL").forEach(System.out::println);
//
//      System.out.println("\n剩餘美國聯盟非冠軍球隊（按勝率排序）：");
//      classifiedTeams.get("ALtemp").stream().skip(3).forEach(System.out::println);
//
//      System.out.println("\n剩餘國家聯盟非冠軍球隊（按勝率排序）：");
//      classifiedTeams.get("NLtemp").stream().skip(3).forEach(System.out::println);
    } catch (Exception e) {
      System.err.println("Error fetching or processing MLB data: " + e.getMessage());
      e.printStackTrace();

      // 印出 JSON 結構以進行除錯
      try {
        JSONObject apiResponse = MLBDataApi.getApiResponse(API_URL);
        System.out.println("API Response Structure:");
        System.out.println(apiResponse.toString(2));
      } catch (Exception e2) {
        System.err.println("Error fetching API response for debugging: " + e2.getMessage());
      }
    }
  }
}