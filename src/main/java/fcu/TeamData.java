package fcu;

public class TeamData {
  //使用枚舉的方式來定義MLB的分區
  public enum MLBDivision {
    AL_EAST(201, "AL East"),
    AL_CENTRAL(202, "AL Central"),
    AL_WEST(200, "AL West"),
    NL_EAST(204, "NL East"),
    NL_CENTRAL(205, "NL Central"),
    NL_WEST(203, "NL West"),
    UNKNOWN(0, "Unknown");

    private final int id;
    private final String name;

    MLBDivision(int id, String name) {
      this.id = id;
      this.name = name;
    }

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public static MLBDivision getById(int id) {
      for (MLBDivision division : values()) {
        if (division.id == id) {
          return division;
        }
      }
      return UNKNOWN;
    }
  }

  private final MLBDivision division;
  private final String name;
  private final String teamCode;
  private final int wins;
  private final int losses;
  private final double pct;

  public TeamData(int division, String name, String teamCode, int wins, int losses, double pct) {
    this.division = MLBDivision.getById(division);
    this.name = name;
    this.teamCode = teamCode;
    this.wins = wins;
    this.losses = losses;
    this.pct = pct;
  }

  public MLBDivision getDivision() {
    return division;
  }

  public String getName() {
    return name;
  }

  public String getTeamCode() {
    return teamCode;
  }

  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return losses;
  }

  public double getPct() {
    return pct;
  }

  @Override
  public String toString() {
    return "TeamData{" +
        "division=" + division +
        ", name='" + name + '\'' +
        ", teamCode='" + teamCode + '\'' +
        ", wins=" + wins +
        ", losses=" + losses +
        ", pct=" + pct +
        '}';
  }
}

