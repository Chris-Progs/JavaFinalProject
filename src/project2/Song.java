
package project2;

public class Song {
    private String songName;
    private String filePath;

    public int compareTo(Song song){
        return this.songName.compareTo(song.songName);
    }
    
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }   
}
