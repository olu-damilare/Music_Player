package africa.semicolon.mp3;

import java.util.ArrayList;

import static africa.semicolon.mp3.MusicState.*;

public class MusicPlayer {
    private boolean isOn;
    private final Music[] musicList = new Music[50];
    private int totalNumberOfMusic;
    private Music currentPlayingMusic;
    private boolean isPlayingMusic;
    private MusicState currentMusicState;
    private int volume;
    private boolean isMute;
    private int volumeBeforeMute;
    private final ArrayList<ArrayList<Music>> playlists = new ArrayList<>();

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        volume = 10;
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
        currentPlayingMusic = null;
    }

    public void toggleSwitch() {
        if (isOn)
            turnOff();
        else
            turnOn();
    }

    public void download(Music music) {
        boolean musicExists = false;
        if (isOn) {
            int nextDownloadPosition = musicDownloadPosition();
            if (totalNumberOfMusic > 0) {
                for (Music value : musicList) {
                    if (value != null && value.equals(music)) {
                        musicExists = true;
                        break;
                    }
                }
            }
            if (!musicExists) {
                musicList[nextDownloadPosition] = music;
                totalNumberOfMusic++;
            }
        }
    }

    public int getTotalNumberOfMusic() {
        return totalNumberOfMusic;
    }

    private int musicDownloadPosition() {
        int nextDownloadPosition = 0;

        for (int i = 0; i < musicList.length; i++)
            if (musicList[i] == null) {
                nextDownloadPosition = i;
                break;

            }
        return nextDownloadPosition;
    }

    public void deleteMusic(Music music) {
        if (isOn) {
            try {
                for (int i = 0; i < musicList.length; i++) {

                    if (musicList[i].equals(music)) {
                        musicList[i] = null;
                        totalNumberOfMusic--;
                        break;
                    }
                }
            } catch (NullPointerException exception) {
                System.out.println("The song you want to delete does not exist in your collection");
            }
            fillNullIndicesBetweenSongs();
        }

    }

    public void playMusic(Music azonto) {
        if (isOn) {
            for (Music music : musicList) {
                if (music != null && music.equals(azonto)) {
                    currentPlayingMusic = azonto;
                    currentMusicState = PLAYING;
                    isPlayingMusic = true;
                    break;
                }
            }
        }
    }

    public boolean isPlayingMusic() {
        return isPlayingMusic;
    }

    public Music getCurrentPlayingMusic() {
        return currentPlayingMusic;
    }

    public void playOrPauseMusic() {
        if (isOn) {
            if (currentMusicState == PLAYING) {
                currentMusicState = PAUSED;
                isPlayingMusic = false;
            } else if (currentMusicState == PAUSED) {
                currentMusicState = PLAYING;
                isPlayingMusic = true;
            }

        }
    }

    public MusicState getCurrentMusicState() {
        return currentMusicState;
    }

    public void stopMusic() {
        if (isOn) {
            if (currentMusicState == PLAYING || currentMusicState == PAUSED) {
                isPlayingMusic = false;
                currentMusicState = STOPPED;
                currentPlayingMusic = null;
            }
        }
    }

    public void increaseVolume() {
        if (isOn)
            if (isMute) {
                volume = volumeBeforeMute;
                volume++;
                isMute = false;
            } else if (volume < 100)
                volume++;
    }

    public int getVolume() {
        return volume;
    }

    public void decreaseVolume() {
        if (isOn)
            if (isMute) {
                volume = volumeBeforeMute;
                volume--;
                isMute = false;
            } else if (volume > 0)
                volume--;
    }

    public void muteOrUnmuteVolume() {
        if (isOn)
            if (!isMute) {
                volumeBeforeMute = volume;
                volume = 0;
                isMute = true;
            } else {
                volume = volumeBeforeMute;
                isMute = false;
            }
    }

    public boolean isMute() {
        return isMute;
    }

    public void playNextTrack() {
        if (isOn) {
            for (int i = 0; i < musicList.length; i++) {
                if (musicList[i] == currentPlayingMusic) {
                    if (i + 1 == totalNumberOfMusic || musicList[i + 1] == null) {
                        currentPlayingMusic = musicList[0];
                    } else {
                        currentPlayingMusic = musicList[i + 1];
                    }
                    break;

                }
            }
        }
    }

    public void playPreviousTrack() {
        if (isOn) {
            for (int i = 0; i < musicList.length; i++) {
                if (musicList[i] == currentPlayingMusic) {
                    if (i == 0) {
                        currentPlayingMusic = musicList[totalNumberOfMusic - 1];
                    } else {
                        currentPlayingMusic = musicList[i - 1];
                    }
                    break;
                }
            }
        }
    }

    private void fillNullIndicesBetweenSongs() {
        for (int i = 1; i < musicList.length; i++) {
            if (musicList[i - 1] == null) {
                musicList[i - 1] = musicList[i];
                musicList[i] = null;
            }
        }
    }

    public void createPlaylist(ArrayList<Music> playlistName) {
        if (isOn)
            playlists.add(playlistName);
    }


    public int getTotalNumberOfPlaylist() {
        return playlists.size();
    }

    public void addMusicToPlaylist(Music musicName, ArrayList<Music> playlistName) {
        if(isOn())
         for (ArrayList<Music> musics : playlists) {
            if ((musics.equals(playlistName))) {
                musics.add(musicName);
            }

        }
    }

    public int numberOfMusicInPlaylist(ArrayList<Music> playlistName) {
        int playlistMusics = 0;
        for (ArrayList<Music> playlist : playlists) {
            if (playlist == playlistName)
                playlistMusics = playlist.size();
        }
    return playlistMusics;
    }

    public void removeMusicFromPlaylist(Music musicName, ArrayList<Music> playlistName) {
        if(isOn())
            for (ArrayList<Music> musics : playlists) {
                if ((musics.equals(playlistName))) {
                    musics.remove(musicName);
                }
            }
    }

    public void skipNextMusic() {
        for (int i = 0; i < musicList.length; i++) {
            if (musicList[i] == currentPlayingMusic) {
                verifyMusicPositionToSkip(i);
                break;
            }
        }

    }

    private void verifyMusicPositionToSkip(int position) {
        if(currentPlayingMusic.equals(musicList[totalNumberOfMusic - 2]))
            currentPlayingMusic = musicList[0];
        else if(currentPlayingMusic == musicList[totalNumberOfMusic - 1])
            currentPlayingMusic = musicList[1];
        else
        currentPlayingMusic = musicList[position + 2];
    }
}