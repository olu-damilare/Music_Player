package africa.semicolon.mp3;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static africa.semicolon.mp3.MusicState.*;
import static org.junit.jupiter.api.Assertions.*;

public class MusicPlayerTest {
    MusicPlayer myMp3Player;
    @BeforeEach
    void setUp() {
         myMp3Player = new MusicPlayer();
    }
    @AfterEach
    void tearDown(){
        myMp3Player = null;
    }

    @Test
    void mp3Player_CanBeCreated(){
        assertNotNull(myMp3Player);
    }
    @Test
    void testThatMp3PlayerCanTurnOnAndOffWithAButton(){
        myMp3Player.toggleSwitch();
        boolean isOn = myMp3Player.isOn();
        assertTrue(isOn);

        myMp3Player.toggleSwitch();
        assertFalse(myMp3Player.isOn());
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
    }

    @Test
    void testThatMp3PlayerCanDownloadMusicWhenTurnedOn(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music music = new Music();
        Music secondMusic = new Music();

        myMp3Player.download(music);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());
        myMp3Player.download(music);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());
        myMp3Player.download(secondMusic);
        assertEquals(2, myMp3Player.getTotalNumberOfMusic());
    }
    @Test
    void testThatMp3PlayerCantDownloadMusicWhenTurnedOff(){
        assertFalse(myMp3Player.isOn());
        Music music = new Music();
        Music secondMusic = new Music();

        myMp3Player.download(music);
        assertEquals(0, myMp3Player.getTotalNumberOfMusic());
        myMp3Player.download(secondMusic);
        assertEquals(0, myMp3Player.getTotalNumberOfMusic());
    }
    @Test
    void testThatMp3PlayerCanDeleteMusicWhenTurnedOn(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music music = new Music();
        Music secondMusic = new Music();

        myMp3Player.download(music);
        myMp3Player.download(secondMusic);
        assertEquals(2, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.deleteMusic(music);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

    }
    @Test
    void testThatMp3PlayerCannotDeleteMusicThatDoesNotExist(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music music = new Music();
        Music secondMusic = new Music();

        myMp3Player.download(music);
        myMp3Player.download(secondMusic);
        assertEquals(2, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.deleteMusic(music);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());
        myMp3Player.deleteMusic(music);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());


    }
    @Test
    void testThatMp3PlayerCannotDeleteMusicWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music music = new Music();
        Music secondMusic = new Music();

        myMp3Player.download(music);
        myMp3Player.download(secondMusic);
        assertEquals(2, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.toggleSwitch();
        myMp3Player.deleteMusic(music);
        assertEquals(2, myMp3Player.getTotalNumberOfMusic());

    }
    @Test
    void testThatMp3CanPlayMusic(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }

    @Test
    void testThatMp3CannotPlayMusicWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.toggleSwitch();
        assertFalse(myMp3Player.isOn());
        myMp3Player.playMusic(azonto);
        assertFalse(myMp3Player.isPlayingMusic());
        assertNotEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatMp3CannotPlayMusicThatIsNotDownloaded(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        assertEquals(0, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertFalse(myMp3Player.isPlayingMusic());
        assertNotEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }

    @Test
    void testThatMp3CanPauseMusic(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertTrue(myMp3Player.isPlayingMusic());
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());

        myMp3Player.playOrPauseMusic();
        assertEquals(PAUSED, myMp3Player.getCurrentMusicState());
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatMp3CannotPauseMusicWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertTrue(myMp3Player.isPlayingMusic());
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());

        myMp3Player.toggleSwitch();
        myMp3Player.playOrPauseMusic();
        assertNotEquals(PAUSED, myMp3Player.getCurrentMusicState());
    }

    @Test
    void testThatMp3CanStopMusic(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertTrue(myMp3Player.isPlayingMusic());
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());

        myMp3Player.playOrPauseMusic();
        assertEquals(PAUSED, myMp3Player.getCurrentMusicState());
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());

        myMp3Player.stopMusic();
        assertEquals(STOPPED, myMp3Player.getCurrentMusicState());
        assertNotEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatMp3CannotStopMusicWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertTrue(myMp3Player.isPlayingMusic());
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());

        myMp3Player.playOrPauseMusic();
        assertEquals(PAUSED, myMp3Player.getCurrentMusicState());
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());

        myMp3Player.toggleSwitch();
        myMp3Player.stopMusic();
        assertNotEquals(STOPPED, myMp3Player.getCurrentMusicState());
        assertNotEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatButtonCanPlayOrPauseMusic(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(1, myMp3Player.getTotalNumberOfMusic());

        myMp3Player.playMusic(azonto);
        assertTrue(myMp3Player.isPlayingMusic());
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());

        myMp3Player.playOrPauseMusic();
        assertEquals(PAUSED, myMp3Player.getCurrentMusicState());
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());

        myMp3Player.playOrPauseMusic();
        assertEquals(PLAYING, myMp3Player.getCurrentMusicState());
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatDefaultVolumeIsSetWhenMp3IsTurnedOn(){
        myMp3Player.toggleSwitch();
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);

        assertEquals(10, myMp3Player.getVolume());
    }
    @Test
    void testThatVolumeCannotBeGottenWhenMp3IsTurnedOff(){
        assertFalse(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        assertEquals(0, myMp3Player.getTotalNumberOfMusic());
        myMp3Player.playMusic(azonto);
        assertFalse(myMp3Player.isPlayingMusic());

        assertEquals(0, myMp3Player.getVolume());
    }

    @Test
    void testThatVolumeCanBeIncreased(){
        myMp3Player.toggleSwitch();
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);

        myMp3Player.increaseVolume();
        assertEquals(11, myMp3Player.getVolume());
    }
    @Test
    void testThatVolumeCannotBeIncreasedWhenMp3IsTurnedOff(){
        assertFalse(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);

        myMp3Player.increaseVolume();
        assertEquals(0, myMp3Player.getVolume());
    }
    @Test
    void testThatVolumeCanBeDecreased(){
        myMp3Player.toggleSwitch();
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);

        myMp3Player.decreaseVolume();
        assertEquals(9, myMp3Player.getVolume());
    }
    @Test
    void testThatVolumeCannotBeDecreasedWhenMp3IsTurnedOff(){
        assertFalse(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);

        myMp3Player.decreaseVolume();
        assertEquals(0, myMp3Player.getVolume());
    }
    @Test
    void testThatMinimumVolumeIsZero(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);

        myMp3Player.decreaseVolume();
        assertEquals(9, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(8, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(7, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(6, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(5, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(4, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(3, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(2, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(1, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(0, myMp3Player.getVolume());
        myMp3Player.decreaseVolume();
        assertEquals(0, myMp3Player.getVolume());
    }
    @Test
    void testMp3PlayerCanMuteOrUnmuteVolume(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);
        assertEquals(10, myMp3Player.getVolume());

        myMp3Player.muteOrUnmuteVolume();
        assertTrue(myMp3Player.isMute());

        assertEquals(0, myMp3Player.getVolume());
        myMp3Player.muteOrUnmuteVolume();
        assertFalse(myMp3Player.isMute());
    }
    @Test
    void testMp3PlayerCannotMuteOrUnmuteVolumeWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);
        assertEquals(10, myMp3Player.getVolume());


        myMp3Player.muteOrUnmuteVolume();
        assertTrue(myMp3Player.isMute());
        assertEquals(0, myMp3Player.getVolume());
        myMp3Player.toggleSwitch();
        myMp3Player.muteOrUnmuteVolume();
        assertEquals(0, myMp3Player.getVolume());

    }
    @Test
    void testMp3PlayerVolumeReturnsToPreviousVolumeAfterUnmute(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);
        assertEquals(10, myMp3Player.getVolume());
        myMp3Player.muteOrUnmuteVolume();
        assertTrue(myMp3Player.isMute());
        assertEquals(0, myMp3Player.getVolume());

        myMp3Player.muteOrUnmuteVolume();
        assertFalse(myMp3Player.isMute());
        assertEquals(10, myMp3Player.getVolume());

    }
    @Test
    void testMp3PlayerVolumeIncreasesFromPreviousVolumeBeforeMute(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);
        assertEquals(10, myMp3Player.getVolume());
        myMp3Player.muteOrUnmuteVolume();
        assertTrue(myMp3Player.isMute());
        assertEquals(0, myMp3Player.getVolume());

        myMp3Player.increaseVolume();
        assertFalse(myMp3Player.isMute());
        assertEquals(11, myMp3Player.getVolume());

    }
    @Test
    void testMp3PlayerVolumeDecreasesFromPreviousVolumeBeforeMute(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        myMp3Player.download(azonto);
        myMp3Player.playMusic(azonto);
        assertEquals(10, myMp3Player.getVolume());
        myMp3Player.muteOrUnmuteVolume();
        assertTrue(myMp3Player.isMute());
        assertEquals(0, myMp3Player.getVolume());

        myMp3Player.decreaseVolume();
        assertFalse(myMp3Player.isMute());
        assertEquals(9, myMp3Player.getVolume());

    }
    @Test
    void testThatMp3PlayerCanPlayNextMusic(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        Music shoki = new Music();
        myMp3Player.download(azonto);
        myMp3Player.download(shoki);
        myMp3Player.playMusic(azonto);

        myMp3Player.playNextTrack();

        assertEquals(shoki, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatMp3PlayerCannotPlayNextMusicWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        Music shoki = new Music();
        myMp3Player.download(azonto);
        myMp3Player.download(shoki);
        myMp3Player.playMusic(azonto);

        myMp3Player.toggleSwitch();
        myMp3Player.playNextTrack();
        assertNull(myMp3Player.getCurrentPlayingMusic());
    }

    @Test
    void testThatMp3PlayerCanPlayPreviousMusic(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        Music shoki = new Music();
        myMp3Player.download(azonto);
        myMp3Player.download(shoki);
        myMp3Player.playMusic(azonto);

        myMp3Player.playNextTrack();
        assertEquals(shoki, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.playPreviousTrack();
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());

    }
    @Test
    void testThatMp3PlayerCannotPlayPreviousMusicWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        Music shoki = new Music();
        myMp3Player.download(azonto);
        myMp3Player.download(shoki);

        myMp3Player.playMusic(shoki);
        myMp3Player.toggleSwitch();
        myMp3Player.playPreviousTrack();

        assertNull(myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatMp3PlayerNextTrackButtonLoops(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        Music shoki = new Music();
        Music gbedu = new Music();
        myMp3Player.download(azonto);
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);
        myMp3Player.playMusic(azonto);

        myMp3Player.playNextTrack();
        assertEquals(shoki, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.playNextTrack();
        assertEquals(gbedu, myMp3Player.getCurrentPlayingMusic());

        myMp3Player.playNextTrack();
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());

    }
    @Test
    void testThatMp3PlayerPreviousTrackButtonLoops(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        Music azonto = new Music();
        Music shoki = new Music();
        Music gbedu = new Music();
        myMp3Player.download(azonto);
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);
        myMp3Player.playMusic(gbedu);

        myMp3Player.playPreviousTrack();
        assertEquals(shoki, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.playPreviousTrack();
        assertEquals(azonto, myMp3Player.getCurrentPlayingMusic());

        myMp3Player.playPreviousTrack();
        assertEquals(gbedu, myMp3Player.getCurrentPlayingMusic());
    }
    @Test
    void testThatMp3CanCreatePlaylist(){
        myMp3Player.toggleSwitch();
        ArrayList<Music> playlist = new ArrayList<>();

        myMp3Player.createPlaylist(playlist);

        assertEquals(1, myMp3Player.getTotalNumberOfPlaylist());
    }
    @Test
    void testThatMp3CannotCreatePlaylistWhenSwitchedOff(){
       assertFalse(myMp3Player.isOn());
        ArrayList<Music> playlist = new ArrayList<>();

        myMp3Player.createPlaylist(playlist);

        assertEquals(0, myMp3Player.getTotalNumberOfPlaylist() );
    }
    @Test
    void testThatMp3CanAddMusicToPlaylist(){
        myMp3Player.toggleSwitch();
        ArrayList<Music> playlist = new ArrayList<>();
        Music shoki = new Music();
        Music gbedu = new Music();
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);

        myMp3Player.createPlaylist(playlist);
        myMp3Player.addMusicToPlaylist(shoki, playlist);
        assertEquals(1, myMp3Player.numberOfMusicInPlaylist(playlist));
    }
    @Test
    void testThatMp3CannotAddMusicToPlaylistWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        assertTrue(myMp3Player.isOn());
        ArrayList<Music> playlist = new ArrayList<>();
        Music shoki = new Music();
        Music gbedu = new Music();
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);
        myMp3Player.createPlaylist(playlist);

        myMp3Player.toggleSwitch();
        assertFalse(myMp3Player.isOn());
        myMp3Player.addMusicToPlaylist(shoki, playlist);

        assertEquals(0, myMp3Player.numberOfMusicInPlaylist(playlist));
    }
    @Test
    void testThatMp3CanRemoveMusicFromPlaylist(){
        myMp3Player.toggleSwitch();
        ArrayList<Music> playlist = new ArrayList<>();
        Music shoki = new Music();
        Music gbedu = new Music();
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);

        myMp3Player.createPlaylist(playlist);
        myMp3Player.addMusicToPlaylist(shoki, playlist);
        assertEquals(1, myMp3Player.numberOfMusicInPlaylist(playlist));
        myMp3Player.addMusicToPlaylist(gbedu, playlist);
        assertEquals(2, myMp3Player.numberOfMusicInPlaylist(playlist));

        myMp3Player.removeMusicFromPlaylist(shoki, playlist);
        assertEquals(1, myMp3Player.numberOfMusicInPlaylist(playlist));
        myMp3Player.removeMusicFromPlaylist(gbedu, playlist);
        assertEquals(0, myMp3Player.numberOfMusicInPlaylist(playlist));


    }
    @Test
    void testThatMp3CannotRemoveMusicFromPlaylistWhenTurnedOff(){
        myMp3Player.toggleSwitch();
        ArrayList<Music> playlist = new ArrayList<>();
        Music shoki = new Music();
        Music gbedu = new Music();
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);

        myMp3Player.createPlaylist(playlist);
        myMp3Player.addMusicToPlaylist(shoki, playlist);
        assertEquals(1, myMp3Player.numberOfMusicInPlaylist(playlist));
        myMp3Player.addMusicToPlaylist(gbedu, playlist);
        assertEquals(2, myMp3Player.numberOfMusicInPlaylist(playlist));
        myMp3Player.toggleSwitch();
        myMp3Player.removeMusicFromPlaylist(shoki, playlist);
        assertEquals(2, myMp3Player.numberOfMusicInPlaylist(playlist));
    }
    @Test
    void testThatMp3CanSkipNextMusic(){
        myMp3Player.toggleSwitch();
        Music shoki = new Music();
        Music gbedu = new Music();
        Music alanta = new Music();
        myMp3Player.download(shoki);
        myMp3Player.download(gbedu);
        myMp3Player.download(alanta);

        myMp3Player.playMusic(shoki);
        assertEquals(shoki, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.skipNextMusic();
        assertEquals(alanta, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.playMusic(alanta);
        assertEquals(alanta, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.skipNextMusic();
        assertEquals(gbedu, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.playMusic(gbedu);
        assertEquals(gbedu, myMp3Player.getCurrentPlayingMusic());
        myMp3Player.skipNextMusic();
        assertEquals(shoki, myMp3Player.getCurrentPlayingMusic());
    }

}
