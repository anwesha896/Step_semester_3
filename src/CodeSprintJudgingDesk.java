import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Scoring abstraction
interface SprintScoringRule {
    double calculateScore(double idea, double execution, double presentation);
    String getTrackName();
}

// Innovation track
class InnovationTrackRule implements SprintScoringRule {

    public double calculateScore(double idea, double execution, double presentation) {
        return idea * 0.50 + execution * 0.30 + presentation * 0.20;
    }

    public String getTrackName() {
        return "Innovation";
    }
}

// Open track
class OpenTrackRule implements SprintScoringRule {

    public double calculateScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }

    public String getTrackName() {
        return "Open";
    }
}

// Student participating in the hackathon
class SprintParticipant {
    private String name;

    public SprintParticipant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Judge
class SprintJudge {
    private String name;

    public SprintJudge(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Score given by a judge
class SprintScore {
    private double idea;
    private double execution;
    private double presentation;

    public SprintScore(double idea, double execution, double presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public double getIdea() {
        return idea;
    }

    public double getExecution() {
        return execution;
    }

    public double getPresentation() {
        return presentation;
    }
}

// Submitted project
class SprintProject {
    private String projectName;
    private SprintScore score;
    private SprintJudge judge;

    public SprintProject(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public boolean hasScore() {
        return score != null;
    }

    public void recordScore(SprintJudge judge, SprintScore score) {
        this.judge = judge;
        this.score = score;
    }

    public SprintScore getScore() {
        return score;
    }
}

// Hackathon team
class SprintTeam {
    private String teamName;
    private List<SprintParticipant> members;
    private SprintScoringRule scoringRule;
    private SprintProject project;

    public SprintTeam(
            String teamName,
            List<SprintParticipant> members,
            SprintScoringRule scoringRule) {

        this.teamName = teamName;
        this.members = new ArrayList<>(members);
        this.scoringRule = scoringRule;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<SprintParticipant> getMembers() {
        return members;
    }

    public SprintScoringRule getScoringRule() {
        return scoringRule;
    }

    public boolean hasProject() {
        return project != null;
    }

    public void submitProject(SprintProject project) {
        this.project = project;
    }

    public SprintProject getProject() {
        return project;
    }

    public double calculateFinalScore() {
        SprintScore score = project.getScore();

        return scoringRule.calculateScore(
                score.getIdea(),
                score.getExecution(),
                score.getPresentation()
        );
    }
}

// Hackathon state
enum SprintState {OPEN, JUDGING, PUBLISHED}

// Hackathon
class CodeSprintHackathon {

    private String hackathonName;
    private List<SprintTeam> teams;
    private Set<SprintParticipant> registeredParticipants;
    private SprintState state;

    public CodeSprintHackathon(String hackathonName) {
        this.hackathonName = hackathonName;
        this.teams = new ArrayList<>();
        this.registeredParticipants = new HashSet<>();
        this.state = SprintState.OPEN;
    }

    public void registerTeam(SprintTeam team) {

        if (state != SprintState.OPEN) {
            System.out.println("Registration failed: Registration is closed.");
            return;
        }

        int memberCount = team.getMembers().size();

        if (memberCount < 2 || memberCount > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return;
        }

        for (SprintParticipant participant : team.getMembers()) {

            if (registeredParticipants.contains(participant)) {
                System.out.println("Registration failed: Student " + participant.getName() + " already belongs to a team.");
                return;
            }
        }

        teams.add(team);

        registeredParticipants.addAll(team.getMembers());

        System.out.println("Team " + team.getTeamName() + " registered (" + memberCount + " members, " + team.getScoringRule().getTrackName() + " track).");
    }

    public void startJudging() {
        if (state == SprintState.OPEN) {
            state = SprintState.JUDGING;
        }
    }

    public void submitProject(
            SprintTeam team,
            SprintProject project) {

        if (!teams.contains(team)) {
            System.out.println("Submission rejected: Team is not registered.");
            return;
        }

        if (team.hasProject()) {
            System.out.println("Submission rejected: A team can submit only one project.");
            return;
        }

        team.submitProject(project);

        System.out.println("Project '" + project.getProjectName() + "' submitted by " + team.getTeamName() + ".");
    }

    public void recordScore(
            SprintTeam team,
            SprintJudge judge,
            double idea,
            double execution,
            double presentation) {

        if (state == SprintState.PUBLISHED) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        if (state != SprintState.JUDGING) {
            System.out.println("Score rejected: Judging has not started.");
            return;
        }

        if (!team.hasProject()) {
            System.out.println("Score rejected: Project has not been submitted.");
            return;
        }

        if (team.getProject().hasScore()) {
            System.out.println("Score rejected: Project has already been scored.");
            return;
        }

        if (idea < 0 || idea > 10 || execution < 0 || execution > 10 || presentation < 0 || presentation > 10) {
            System.out.println("Score rejected: Ratings must be between 0 and 10.");
            return;
        }

        SprintScore score = new SprintScore(idea, execution, presentation);

        team.getProject().recordScore(judge, score);

        System.out.println("Score recorded for '" + team.getProject().getProjectName() + "'.");
    }

    public void publishResults() {

        if (state != SprintState.JUDGING) {
            System.out.println("Results cannot be published at this stage.");
            return;
        }

        state = SprintState.PUBLISHED;

        for (SprintTeam team : teams) {

            if (team.hasProject() && team.getProject().hasScore()) {
                System.out.printf("Final score: %.2f%n", team.calculateFinalScore());
            }
        }

        System.out.println("Results published.");
    }
}

// Main class
public class CodeSprintJudgingDesk {

    public static void main(String[] args) {

        CodeSprintHackathon hackathon = new CodeSprintHackathon("Code Sprint");

        SprintParticipant asha = new SprintParticipant("Asha");

        SprintParticipant ravi = new SprintParticipant("Ravi");

        SprintParticipant neha = new SprintParticipant("Neha");

        SprintParticipant kiran = new SprintParticipant("Kiran");

        SprintScoringRule innovation = new InnovationTrackRule();

        SprintScoringRule open = new OpenTrackRule();

        // ByteBusters
        List<SprintParticipant> byteBustersMembers = new ArrayList<>();

        byteBustersMembers.add(asha);
        byteBustersMembers.add(ravi);
        byteBustersMembers.add(neha);

        SprintTeam byteBusters = new SprintTeam("ByteBusters", byteBustersMembers, innovation);

        hackathon.registerTeam(byteBusters);

        // SoloCoder
        List<SprintParticipant> soloCoderMembers = new ArrayList<>();

        soloCoderMembers.add(kiran);

        SprintTeam soloCoder = new SprintTeam("SoloCoder", soloCoderMembers, open);

        hackathon.registerTeam(soloCoder);

        // Project submission
        SprintProject smartAttend = new SprintProject("SmartAttend");

        hackathon.submitProject(byteBusters, smartAttend);

        // Start judging
        hackathon.startJudging();

        // Judge scores project
        SprintJudge judge = new SprintJudge("Judge 1");

        hackathon.recordScore(byteBusters, judge, 8, 7, 9);

        // Publish results
        hackathon.publishResults();

        // Attempt to rescore
        hackathon.recordScore(byteBusters, judge, 10, 7, 9);
    }
}