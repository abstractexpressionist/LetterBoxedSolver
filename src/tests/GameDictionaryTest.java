package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.*;
import DictionaryCleaner.GameDictionaryCleaner;

public class GameDictionaryTest {
    GameDictionaryCleaner test_game_dictionary;
    TestData test_data;
    
    @Before
    public void Setup() throws IOException, FileNotFoundException {
        test_data = new TestData();
        this.test_game_dictionary = new GameDictionaryCleaner(test_data.test_sides, test_data.test_game_dictionary_destination);
    }

    @Test
    public void testClean() throws IOException {
        test_game_dictionary.clean();
    }
}
