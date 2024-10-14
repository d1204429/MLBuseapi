package fcu;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class MLBDataProcessor {

  public static List<TeamData> extractTeamData(JSONObject jsonResponse) {
    List<TeamData> teamDataList = new ArrayList<>();
    JSONArray records = jsonResponse.getJSONArray("records");

    for (int i = 0; i < records.length(); i++) {
      JSONObject record = records.getJSONObject(i);
      JSONArray teamRecords = record.getJSONArray("teamRecords");

      for (int j = 0; j < teamRecords.length(); j++) {
        JSONObject teamRecord = teamRecords.getJSONObject(j);

        int division = record.getJSONObject("division").getInt("id");
        String name = teamRecord.getJSONObject("team").getString("name");
        String teamCode = teamRecord.getJSONObject("team").getString("abbreviation");

        JSONObject leagueRecord = teamRecord.getJSONObject("leagueRecord");
        int wins = leagueRecord.getInt("wins");
        int losses = leagueRecord.getInt("losses");
        double pct = leagueRecord.getDouble("pct");

        TeamData teamData = new TeamData(division, name, teamCode, wins, losses, pct);
        teamDataList.add(teamData);
      }
    }
    return teamDataList;
  }

  public static Map<String, List<TeamData>> classifyTeams(List<TeamData> teamDataList) {
    Map<String, List<TeamData>> result = new HashMap<>();
    result.put("AL", new ArrayList<>());
    result.put("NL", new ArrayList<>());
    result.put("ALtemp", new ArrayList<>());
    result.put("NLtemp", new ArrayList<>());

    List<TeamData> divisionChampions = findDivisionChampions(teamDataList);

    divisionChampions.sort(Comparator.comparing(TeamData::getPct).reversed());

    for (TeamData champion : divisionChampions) {
      if (isALTeam(champion)) {
        result.get("AL").add(champion);
      } else {
        result.get("NL").add(champion);
      }
    }

    for (TeamData team : teamDataList) {
      if (!divisionChampions.contains(team)) {
        if (isALTeam(team)) {
          result.get("ALtemp").add(team);
        } else {
          result.get("NLtemp").add(team);
        }
      }
    }

    Comparator<TeamData> teamComparator = Comparator.comparing(TeamData::getPct).reversed();

    result.get("ALtemp").sort(teamComparator);
    result.get("NLtemp").sort(teamComparator);

    addTopThreeTeams(result);

    return result;
  }

  private static List<TeamData> findDivisionChampions(List<TeamData> teamDataList) {
    Map<TeamData.MLBDivision, TeamData> divisionChampions = new HashMap<>();

    for (TeamData team : teamDataList) {
      TeamData.MLBDivision division = team.getDivision();
      if (!divisionChampions.containsKey(division) ||
          team.getPct() > divisionChampions.get(division).getPct()) {
        divisionChampions.put(division, team);
      }
    }

    return new ArrayList<>(divisionChampions.values());
  }

  private static void addTopThreeTeams(Map<String, List<TeamData>> result) {
    addTopTeams(result, "AL", 3);
    addTopTeams(result, "NL", 3);
  }

  private static void addTopTeams(Map<String, List<TeamData>> result, String league, int count) {
    List<TeamData> temp = result.get(league + "temp");
    List<TeamData> main = result.get(league);

    for (int i = 0; i < Math.min(count, temp.size()); i++) {
      main.add(temp.get(i));
    }
  }

  private static boolean isALTeam(TeamData team) {
    return team.getDivision() == TeamData.MLBDivision.AL_EAST ||
        team.getDivision() == TeamData.MLBDivision.AL_CENTRAL ||
        team.getDivision() == TeamData.MLBDivision.AL_WEST;
  }
}