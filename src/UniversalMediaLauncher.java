interface LauncherPlayable {
    String play();
    String play(int fromSecond);
    String pause();
}
abstract class LauncherMediaFile {
    private static int fileCounter = 1000;
    private final String fileId;
    public LauncherMediaFile() {
        fileCounter++;
        fileId = "MF-" + fileCounter;
    }
    public String getFileId() {
        return fileId;
    }
    public abstract String getFormatInfo();
}
class LauncherAudioFile extends LauncherMediaFile implements LauncherPlayable {
    private String title;
    public LauncherAudioFile(String title) {
        super();
        this.title = title;
    }
    @Override
    public String play() {
        return "Playing audio: " + title;
    }
    @Override
    public String play(int fromSecond) {
        int minutes = fromSecond / 60;
        int seconds = fromSecond % 60;
        return "Playing audio: " + title + " from " + minutes + ":" + String.format("%02d", seconds);
    }
    @Override
    public String pause() {
        return "Paused audio: " + title;
    }
    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }}
class LauncherPodcast implements LauncherPlayable {
    private String showName;
    private int episodeNumber;
    public LauncherPodcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }
    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }
    @Override
    public String play(int fromSecond) {
        return "Streaming episode " + episodeNumber + " of " + showName + " from " + fromSecond + " seconds";
    }
    @Override
    public String pause() {
        return "Paused podcast: " + showName;
    }
}
public class UniversalMediaLauncher {
    static void launchAll(LauncherPlayable[] items) {
        for (LauncherPlayable item : items) {
            System.out.println(item.play());
        }
    }
    public static void main(String[] args) {
        LauncherAudioFile audio = new LauncherAudioFile("Morning Jazz");
        System.out.println(audio.play());
        System.out.println(audio.play(30));
        System.out.println(audio.getFormatInfo());
        LauncherPodcast podcast = new LauncherPodcast("Tech Talk", 12);
        System.out.println(podcast.play());
        // Upcasting:
        LauncherPlayable ref = audio;
        System.out.println(ref.play());
        System.out.println();
        launchAll(new LauncherPlayable[] {
                        ref, podcast}
        );
    }
}


