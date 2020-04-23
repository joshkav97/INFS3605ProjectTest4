package com.example.infs3605projecttest4.database;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.infs3605projecttest4.Model.Alphabet;
import com.example.infs3605projecttest4.Model.Sentence;
import com.example.infs3605projecttest4.Model.TestType;
import com.example.infs3605projecttest4.Model.Word;
import com.example.infs3605projecttest4.Model.WordGroup;
import com.example.infs3605projecttest4.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private static Database db;
    private static ArrayList<Alphabet> alphabetArrayList = null;
    private static ArrayList<Word> wordsByCurrType = null;
    private static Word serWord = null;
    private static List<Word> serWordList = null;
    private static Map<String, TestType> typesMap = new HashMap<>();

    public static void startDatabase(final Context context) {
        Warehouse.db = Room.databaseBuilder(context, Database.class, "mydb")
                .build();
    }

    public static void setAllData(final SharedPreferences sharedPreferences) {
        new Thread() {
            @Override
            public void run() {
                if (sharedPreferences.getInt("dbifcreated", 0) == 0) {
                    // insert the alphabets into database
                    insertAllAlphabets();

                    // insert all words to database
                    insertAllWords();

                    // insert the wordgroups into database
                    db.wordGroupDao().insertWordGroup(new WordGroup(900, 901, "Family"));
                    db.wordGroupDao().insertWordGroup(new WordGroup(902, 903, "Family"));
                    db.wordGroupDao().insertWordGroup(new WordGroup(904, 905, "Family"));
                    sharedPreferences.edit().putInt("dbifcreated", 1).apply();
                }

                alphabetArrayList = (ArrayList<Alphabet>) db.alphabetDao().getAlphabets();

                // set the types map
                typesMap.put("Family", new TestType("Family"));
                typesMap.put("Adjective", new TestType("Adjective"));
                typesMap.put("Animal", new TestType("Animal"));
                typesMap.put("Body Part", new TestType("Body Part"));
                typesMap.put("Colour", new TestType("Colour"));
                typesMap.put("Conversation", new TestType("Conversation"));
                typesMap.put("Direction", new TestType("Direction"));
                typesMap.put("Emotion", new TestType("Emotion"));
                typesMap.put("Number", new TestType("Number"));
                typesMap.put("Object", new TestType("Object"));
                typesMap.put("Place", new TestType("Place"));
                typesMap.put("Verb", new TestType("Verb"));
                typesMap.put("Weather", new TestType("Weather"));
                typesMap.put("Other", new TestType("Other"));
                // set the wordslist AND the wordgroupslist to each types
                for (TestType x : typesMap.values()) {
                    x.setWordList((ArrayList<Word>) db.wordDao().getWordsByType(x.getName()));
                    String type = x.getName().toLowerCase();
                    x.setWordGroupList((ArrayList<WordGroup>) db.wordGroupDao().getWordGroupsByType(type));
                    setWordsToWordGroups(x.getWordGroupList());
                }

                // set the sentences
                // 4 wordlists ---> sentences
                ArrayList<Word> s1 = new ArrayList<>();
                ArrayList<Word> s2 = new ArrayList<>();
                ArrayList<Word> s3 = new ArrayList<>();
                ArrayList<Word> s4 = new ArrayList<>();
                s1.add(new Word("Boy1", R.drawable.boy, "NopA"));
                s1.add(new Word("Boy1", R.drawable.boy, "NopB"));
                s1.add(new Word("Boy1", R.drawable.boy, "NopC"));
                s2.add(new Word("Boy2", R.drawable.boy, "NopC"));
                s2.add(new Word("Boy2", R.drawable.boy, "NopB"));
                s2.add(new Word("Boy2", R.drawable.boy, "NopA"));
                s3.add(new Word("Boy3", R.drawable.boy, "NopA"));
                s3.add(new Word("Boy3", R.drawable.boy, "NopC"));
                s3.add(new Word("Boy3", R.drawable.boy, "NopB"));
                s4.add(new Word("Boy4", R.drawable.boy, "NopB"));
                s4.add(new Word("Boy4", R.drawable.boy, "NopA"));
                s4.add(new Word("Boy4", R.drawable.boy, "NopC"));
                typesMap.get("Family").getSentences().add(new Sentence(1, s1, "A B C"));
                typesMap.get("Family").getSentences().add(new Sentence(2, s2, "C B A"));
                typesMap.get("Family").getSentences().add(new Sentence(3, s3, "A C B"));
                typesMap.get("Family").getSentences().add(new Sentence(4, s4, "B A C"));
            }
        }.start();
    }

    public static ArrayList<Word> getWordsByType(final String type) {
        new Thread() {
            @Override
            public void run() {
                wordsByCurrType = (ArrayList<Word>) db.wordDao().getWordsByType(type);
            }
        }.start();
        return wordsByCurrType;
    }

    public static Database getDb() {
        return db;
    }

    public static ArrayList<Alphabet> getAlphabetArrayList() {
        return alphabetArrayList;
    }

    public static ArrayList<Word> getWordsByCurrType() {
        return wordsByCurrType;
    }

    public static Map<String, TestType> getTypesMap() {
        return typesMap;
    }

    public static List<Word> searchAllWords() {
        new Thread() {
            public void run() {
                serWordList = db.wordDao().getWords();
            }
        }.start();
        return serWordList;
    }

    public static void setSerWord(Word serWord) {
        Warehouse.serWord = serWord;
    }

    public static Word getSerWord() {
        return serWord;
    }


    public static void insertAllWords() {
        db.wordDao().insertWord(new Word(1, "Boy", R.drawable.boy, "Nop", "Family"));
        db.wordDao().insertWord(new Word(2, "Girl", R.drawable.girl, "Koort", "Family"));
        db.wordDao().insertWord(new Word(3, "Man", R.drawable.man, "Noongar", "Family"));
        db.wordDao().insertWord(new Word(4, "Woman", R.drawable.woman, "Yoka", "Family"));
        db.wordDao().insertWord(new Word(75, "Wet", R.drawable.wet, "Kippilly", "Adjective"));
        db.wordDao().insertWord(new Word(78, "Hungry", R.drawable.hungry, "Kobble Weert", "Adjective"));
        db.wordDao().insertWord(new Word(95, "Beautiful", R.drawable.beautiful, "Kwobadak", "Adjective"));
        db.wordDao().insertWord(new Word(113, "Sweet", R.drawable.sweet, "Ngoonyung", "Adjective"));
        db.wordDao().insertWord(new Word(129, "Itchy", R.drawable.itchy, "Nyindee", "Adjective"));
        db.wordDao().insertWord(new Word(140, "Weak", R.drawable.weak, "Weern", "Adjective"));
        db.wordDao().insertWord(new Word(137, "Big", R.drawable.big, "Wappalyung", "Adjective"));
        db.wordDao().insertWord(new Word(9, "Pig", R.drawable.pig, "Beark", "Animal"));
        db.wordDao().insertWord(new Word(16, "Ant", R.drawable.ant, "Bidit", "Animal"));
        db.wordDao().insertWord(new Word(17, "Gecko", R.drawable.gecko, "Bidjul", "Animal"));
        db.wordDao().insertWord(new Word(18, "Red Kangaroo", R.drawable.red_kangaroo, "Bigurida", "Animal"));
        db.wordDao().insertWord(new Word(27, "Lizard", R.drawable.lizard, "Bungarrah", "Animal"));
        db.wordDao().insertWord(new Word(38, "Bird", R.drawable.bird, "Djert", "Animal"));
        db.wordDao().insertWord(new Word(39, "Grasshoper", R.drawable.grasshopper, "Djidaarly", "Animal"));
        db.wordDao().insertWord(new Word(40, "Fish", R.drawable.fish, "Djildjit", "Animal"));
        db.wordDao().insertWord(new Word(47, "Snake", R.drawable.snake, "Dugatch", "Animal"));
        db.wordDao().insertWord(new Word(49, "Parrot", R.drawable.parrot, "Dumarlark", "Animal"));
        db.wordDao().insertWord(new Word(50, "Dog", R.drawable.dog, "Dwert", "Animal"));
        db.wordDao().insertWord(new Word(54, "Duck", R.drawable.duck, "Gwinnen", "Animal"));
        db.wordDao().insertWord(new Word(79, "Owl", R.drawable.owl, "Koobeeaju", "Animal"));
        db.wordDao().insertWord(new Word(82, "Sheep", R.drawable.sheep, "Kookanjerrie", "Animal"));
        db.wordDao().insertWord(new Word(84, "Magpie", R.drawable.magpie, "Koolbardie", "Animal"));
        db.wordDao().insertWord(new Word(86, "Possum", R.drawable.possum, "Koomal", "Animal"));
        db.wordDao().insertWord(new Word(116, "Horse", R.drawable.horse, "Ngoort", "Animal"));
        db.wordDao().insertWord(new Word(127, "Flies", R.drawable.flies, "Noort", "Animal"));
        db.wordDao().insertWord(new Word(130, "Echidna", R.drawable.echidna, "Nyingarn", "Animal"));
        db.wordDao().insertWord(new Word(134, "Emu", R.drawable.emu, "Waitch/Waitj", "Animal"));
        db.wordDao().insertWord(new Word(139, "Crow", R.drawable.crow, "Warrdong", "Animal"));
        db.wordDao().insertWord(new Word(141, "Rabbit", R.drawable.rabbit, "Wilbra", "Animal"));
        db.wordDao().insertWord(new Word(144, "Turtle", R.drawable.turtle, "Yarginy", "Animal"));
        db.wordDao().insertWord(new Word(158, "Spiders", R.drawable.spiders, "Kar", "Animal"));
        db.wordDao().insertWord(new Word(12, "Nails", R.drawable.nails, "Beerr", "Body Part"));
        db.wordDao().insertWord(new Word(30, "Ribs", R.drawable.ribs, "Coong", "Body Part"));
        db.wordDao().insertWord(new Word(31, "Heart", R.drawable.heart, "Coort", "Body Part"));
        db.wordDao().insertWord(new Word(33, "Mouth", R.drawable.mouth, "Dar", "Body Part"));
        db.wordDao().insertWord(new Word(48, "Tongue", R.drawable.tongue, "Dulong", "Body Part"));
        db.wordDao().insertWord(new Word(53, "Armpit", R.drawable.armpit, "Gnayl", "Body Part"));
        db.wordDao().insertWord(new Word(57, "Foot", R.drawable.foot, "Jen", "Body Part"));
        db.wordDao().insertWord(new Word(59, "Eyebrows", R.drawable.eyebrows, "Jennt", "Body Part"));
        db.wordDao().insertWord(new Word(60, "Hair", R.drawable.hair, "Joiny", "Body Part"));
        db.wordDao().insertWord(new Word(61, "Bone", R.drawable.bone, "Juerl", "Body Part"));
        db.wordDao().insertWord(new Word(67, "Head", R.drawable.head, "Kart", "Body Part"));
        db.wordDao().insertWord(new Word(77, "Stomach", R.drawable.stomach, "Kobal", "Body Part"));
        db.wordDao().insertWord(new Word(96, "Wrist", R.drawable.wrist, "Kwoliny", "Body Part"));
        db.wordDao().insertWord(new Word(99, "Hand", R.drawable.hand, "Marra", "Body Part"));
        db.wordDao().insertWord(new Word(103, "Nose", R.drawable.nose, "Mooly", "Body Part"));
        db.wordDao().insertWord(new Word(114, "Blood", R.drawable.blood, "Ngoopo", "Body Part"));
        db.wordDao().insertWord(new Word(117, "Teeth", R.drawable.teeth, "Ngorluk", "Body Part"));
        db.wordDao().insertWord(new Word(118, "The elbow", R.drawable.the_elbow, "Ngoyung", "Body Part"));
        db.wordDao().insertWord(new Word(119, "Beard", R.drawable.beard, "Ngunoar", "Body Part"));
        db.wordDao().insertWord(new Word(131, "Brain", R.drawable.brain, "Nyoondeeak", "Body Part"));
        db.wordDao().insertWord(new Word(143, "Skull", R.drawable.skull, "Wubbert", "Body Part"));
        db.wordDao().insertWord(new Word(145, "Bone", R.drawable.bone, "Yatch", "Body Part"));
        db.wordDao().insertWord(new Word(146, "Jaw", R.drawable.jaw, "Yet", "Body Part"));
        db.wordDao().insertWord(new Word(58, "Grey", R.drawable.grey, "Jendal", "Colour"));
        db.wordDao().insertWord(new Word(104, "Black", R.drawable.black, "Moonie", "Colour"));
        db.wordDao().insertWord(new Word(138, "Welcome", R.drawable.welcome, "Wanju", "Conversation"));
        db.wordDao().insertWord(new Word(147, "No", R.drawable.no, "Yuwart", "Conversation"));
        db.wordDao().insertWord(new Word(170, "Why", R.drawable.why, "Nadjil", "Conversation"));
        db.wordDao().insertWord(new Word(70, "Hello/Thank you", R.drawable.thank_you, "Kaya", "Conversation"));
        db.wordDao().insertWord(new Word(74, "Yes", R.drawable.yes, "Kia", "Conversation"));
        db.wordDao().insertWord(new Word(88, "West", R.drawable.west, "Koony Uk", "Direction"));
        db.wordDao().insertWord(new Word(151, "South", R.drawable.south, "Boongarl", "Direction"));
        db.wordDao().insertWord(new Word(152, "Inside", R.drawable.inside, "Bwora", "Direction"));
        db.wordDao().insertWord(new Word(68, "Anger/Angry", R.drawable.anger, "Karrung", "Emotion"));
        db.wordDao().insertWord(new Word(69, "Mad", R.drawable.mad, "Kart Warrah", "Emotion"));
        db.wordDao().insertWord(new Word(155, "Happy", R.drawable.happy, "Djerap-Djerap", "Emotion"));
        db.wordDao().insertWord(new Word(167, "Jealous", R.drawable.jealous, "Mirn", "Emotion"));
        db.wordDao().insertWord(new Word(173, "Cry", R.drawable.cry, "Ngay", "Emotion"));
        db.wordDao().insertWord(new Word(29, "Uncle", R.drawable.uncle, "Conk", "Family"));
        db.wordDao().insertWord(new Word(35, "Grandmother", R.drawable.grandmother, "Demma", "Family"));
        db.wordDao().insertWord(new Word(83, "Kids", R.drawable.kids, "Koolangka", "Family"));
        db.wordDao().insertWord(new Word(89, "Friends", R.drawable.friends, "Koorda", "Family"));
        db.wordDao().insertWord(new Word(105, "Family", R.drawable.family, "Moort", "Family"));
        db.wordDao().insertWord(new Word(112, "Brother", R.drawable.brother, "Ngoon", "Family"));
        db.wordDao().insertWord(new Word(123, "Little child", R.drawable.little_child, "Noobaritch", "Family"));
        db.wordDao().insertWord(new Word(124, "People", R.drawable.people, "Noongar", "Family"));
        db.wordDao().insertWord(new Word(156, "Sister", R.drawable.sister, "Djook", "Family"));
        db.wordDao().insertWord(new Word(163, "Father", R.drawable.father, "Maam", "Family"));
        db.wordDao().insertWord(new Word(165, "Child/baby son", R.drawable.child, "Maawit", "Family"));
        db.wordDao().insertWord(new Word(166, "Child(Second Eldest)", R.drawable.child_second_eldest, "Mardidjit", "Family"));
        db.wordDao().insertWord(new Word(174, "Elder Brother", R.drawable.elder_brother, "Ngoont", "Family"));
        db.wordDao().insertWord(new Word(80, "One", R.drawable.one, "Kaen(keny)", "Number"));
        db.wordDao().insertWord(new Word(81, "Two", R.drawable.two, "Koojal", "Number"));
        db.wordDao().insertWord(new Word(98, "Five", R.drawable.five, "Mar", "Number"));
        db.wordDao().insertWord(new Word(102, "Three", R.drawable.three, "Mo", "Number"));
        db.wordDao().insertWord(new Word(14, "Paper", R.drawable.paper, "Bibal", "Object"));
        db.wordDao().insertWord(new Word(21, "Clothing", R.drawable.clothing, "Bok", "Object"));
        db.wordDao().insertWord(new Word(23, "Rifle", R.drawable.rifle, "Boorlba", "Object"));
        db.wordDao().insertWord(new Word(24, "Rock/stone", R.drawable.rock, "Boya", "Object"));
        db.wordDao().insertWord(new Word(26, "Bushes", R.drawable.bushes, "Bujep", "Object"));
        db.wordDao().insertWord(new Word(34, "Knife", R.drawable.knife, "Darp", "Object"));
        db.wordDao().insertWord(new Word(42, "Star", R.drawable.star, "Djinang", "Object"));
        db.wordDao().insertWord(new Word(43, "Dust", R.drawable.dust, "Dooka", "Object"));
        db.wordDao().insertWord(new Word(51, "Meat", R.drawable.meat, "Dytch", "Object"));
        db.wordDao().insertWord(new Word(56, "Grass", R.drawable.grass, "Jeerp", "Object"));
        db.wordDao().insertWord(new Word(71, "Water", R.drawable.water, "Kayibort", "Object"));
        db.wordDao().insertWord(new Word(76, "Spear", R.drawable.spear, "Kitj", "Object"));
        db.wordDao().insertWord(new Word(100, "Food", R.drawable.food, "Marany", "Object"));
        db.wordDao().insertWord(new Word(106, "The Sun", R.drawable.the_sun, "Ngank", "Object"));
        db.wordDao().insertWord(new Word(126, "Egg", R.drawable.egg, "Noorak", "Object"));
        db.wordDao().insertWord(new Word(148, "Money", R.drawable.money, "Boorndoong", "Object"));
        db.wordDao().insertWord(new Word(154, "Shoe", R.drawable.shoe, "Djen Bwoka", "Object"));
        db.wordDao().insertWord(new Word(157, "Car", R.drawable.car, "Kaditj-Kaditj", "Object"));
        db.wordDao().insertWord(new Word(164, "Moon", R.drawable.moon, "Maant", "Object"));
        db.wordDao().insertWord(new Word(15, "Swamp", R.drawable.swamp, "Bibool", "Place"));
        db.wordDao().insertWord(new Word(22, "Gound", R.drawable.gound, "Boojarra", "Place"));
        db.wordDao().insertWord(new Word(25, "Country", R.drawable.country, "Budjar", "Place"));
        db.wordDao().insertWord(new Word(65, "Home", R.drawable.home, "Karlak/Karluk", "Place"));
        db.wordDao().insertWord(new Word(153, "Forest", R.drawable.forest, "Djaril-mari", "Place"));
        db.wordDao().insertWord(new Word(159, "Hill", R.drawable.hill, "Kard", "Place"));
        db.wordDao().insertWord(new Word(168, "Sea", R.drawable.sea, "MoomBoyet", "Place"));
        db.wordDao().insertWord(new Word(177, "Cave", R.drawable.cave, "Yorakal", "Place"));
        db.wordDao().insertWord(new Word(6, "Jump/fly/step", R.drawable.jump, "Bardang", "Verb"));
        db.wordDao().insertWord(new Word(7, "Biting", R.drawable.biting, "Barkanyin", "Verb"));
        db.wordDao().insertWord(new Word(8, "Hopping", R.drawable.hopping, "Barlanginy", "Verb"));
        db.wordDao().insertWord(new Word(10, "Strangle", R.drawable.strangle, "Bearn", "Verb"));
        db.wordDao().insertWord(new Word(11, "Walk", R.drawable.walk, "Barn", "Verb"));
        db.wordDao().insertWord(new Word(19, "Sleep", R.drawable.sleep, "Bijaarr", "Verb"));
        db.wordDao().insertWord(new Word(20, "Sleeping", R.drawable.sleeping, "Bitgarra", "Verb"));
        db.wordDao().insertWord(new Word(32, "Running", R.drawable.running, "Daarlnyininy", "Verb"));
        db.wordDao().insertWord(new Word(37, "Swim", R.drawable.swim, "Djabaly", "Verb"));
        db.wordDao().insertWord(new Word(41, "Look/see", R.drawable.look, "Djinang", "Verb"));
        db.wordDao().insertWord(new Word(44, "Knocking", R.drawable.knocking, "Dorll Dorliny", "Verb"));
        db.wordDao().insertWord(new Word(52, "Eat", R.drawable.eat, "Gnarn", "Verb"));
        db.wordDao().insertWord(new Word(62, "Laughing", R.drawable.laughing, "Ka Ka Winning", "Verb"));
        db.wordDao().insertWord(new Word(72, "Sing", R.drawable.sing, "Keape", "Verb"));
        db.wordDao().insertWord(new Word(73, "Dance", R.drawable.dance, "keniny", "Verb"));
        db.wordDao().insertWord(new Word(90, "Throw", R.drawable.throw_, "Koordidj", "Verb"));
        db.wordDao().insertWord(new Word(91, "Go", R.drawable.go, "Koorl", "Verb"));
        db.wordDao().insertWord(new Word(94, "Thinking", R.drawable.thinking, "Kuttajinoong", "Verb"));
        db.wordDao().insertWord(new Word(111, "A Cry", R.drawable.a_cry, "Ngiy", "Verb"));
        db.wordDao().insertWord(new Word(115, "Bleeding", R.drawable.bleeding, "Ngoonpulung", "Verb"));
        db.wordDao().insertWord(new Word(120, "Listen", R.drawable.listen, "Ni", "Verb"));
        db.wordDao().insertWord(new Word(132, "Talk", R.drawable.talk, "Waangkiny", "Verb"));
        db.wordDao().insertWord(new Word(133, "Play", R.drawable.play, "Wabiny", "Verb"));
        db.wordDao().insertWord(new Word(999, "Doing", R.drawable.doing, "Warrdint", "Verb"));
        db.wordDao().insertWord(new Word(162, "Wait", R.drawable.wait, "Kwidi", "Verb"));
        db.wordDao().insertWord(new Word(175, "Sit", R.drawable.sit, "Nyin", "Verb"));
        db.wordDao().insertWord(new Word(13, "Summer", R.drawable.summer, "Beeruk", "Weather"));
        db.wordDao().insertWord(new Word(28, "Rain", R.drawable.rain, "Burong/Djart", "Weather"));
        db.wordDao().insertWord(new Word(36, "Spring", R.drawable.spring, "Dirdong", "Weather"));
        db.wordDao().insertWord(new Word(45, "Mist/Fog", R.drawable.mist, "Dudja/Djindi", "Weather"));
        db.wordDao().insertWord(new Word(55, "Night", R.drawable.night, "Jadulukmaradony", "Weather"));
        db.wordDao().insertWord(new Word(66, "Hot/Hot Weather", R.drawable.hot_weather, "Karlawooliny/Karlawoorliny", "Weather"));
        db.wordDao().insertWord(new Word(92, "Cloud", R.drawable.cloud, "Koornden", "Weather"));
        db.wordDao().insertWord(new Word(93, "Thunder", R.drawable.thunder, "Koorndilla", "Weather"));
        db.wordDao().insertWord(new Word(107, "Sunrise", R.drawable.sunrise, "Ngank Barlunginy", "Weather"));
        db.wordDao().insertWord(new Word(108, "Sunset", R.drawable.sunset, "Ngank Weerdiny", "Weather"));
        db.wordDao().insertWord(new Word(135, "Rainbow", R.drawable.rainbow, "Walken", "Weather"));
        db.wordDao().insertWord(new Word(150, "Daylight", R.drawable.daylight, "Birayit", "Weather"));
        db.wordDao().insertWord(new Word(160, "Day", R.drawable.day, "Kedalak", "Weather"));
        db.wordDao().insertWord(new Word(161, "Night", R.drawable.night, "Kedalaka", "Weather"));
        db.wordDao().insertWord(new Word(169, "Black Night", R.drawable.black_night, "Moonawooliny", "Weather"));
        db.wordDao().insertWord(new Word(176, "Sky", R.drawable.sky, "Worl", "Weather"));
        db.wordDao().insertWord(new Word(5, "Lightning", R.drawable.lightning, "Babanginy", "Other"));
        db.wordDao().insertWord(new Word(46, "Song", R.drawable.song, "Dudjarak", "Other"));
        db.wordDao().insertWord(new Word(63, "Smile", R.drawable.smile, "Kar", "Other"));
        db.wordDao().insertWord(new Word(64, "Fire", R.drawable.fire, "Karl", "Other"));
        db.wordDao().insertWord(new Word(85, "Liar", R.drawable.liar, "Koolyyumit", "Other"));
        db.wordDao().insertWord(new Word(87, "Side", R.drawable.side, "Koonga", "Other"));
        db.wordDao().insertWord(new Word(101, "Sick", R.drawable.sick, "Mindich", "Other"));
        db.wordDao().insertWord(new Word(109, "I/Me", R.drawable.i, "Ngany", "Other"));
        db.wordDao().insertWord(new Word(110, "Who", R.drawable.who, "Ngeean", "Other"));
        db.wordDao().insertWord(new Word(122, "Here", R.drawable.here, "Nitcha", "Other"));
        db.wordDao().insertWord(new Word(121, "This", R.drawable.this_, "Ngeean", "Other"));
        db.wordDao().insertWord(new Word(125, "You", R.drawable.you, "Noonuk", "Other"));
        db.wordDao().insertWord(new Word(128, "What", R.drawable.what, "Nygar", "Other"));
        db.wordDao().insertWord(new Word(142, "Where", R.drawable.where, "Winjar", "Other"));
        db.wordDao().insertWord(new Word(149, "Tomorrow", R.drawable.tomorrow, "Benang", "Other"));
        db.wordDao().insertWord(new Word(171, "We", R.drawable.we, "Ngalak", "Other"));
        db.wordDao().insertWord(new Word(172, "Our", R.drawable.our, "Ngalang", "Other"));
    }

    public static void insertAllAlphabets() {
        db.alphabetDao().insert(new Alphabet("A", "a is always as in father\naa as in Kaat"
                , "father", "tjak/kaat/maat"));
        db.alphabetDao().insert(new Alphabet("B/P", "b and p are interchangeable"
                , "-", "balyat/palyat"));
        db.alphabetDao().insert(new Alphabet("D/T", "d and t are interchangeable. Also used with j to give a softer version of the 'ch' sound"
                , "", "tjuditj/djuditj"));
        db.alphabetDao().insert(new Alphabet("E", "e is always as in fetch"
                , "fetch", "dwert/ketj"));
        db.alphabetDao().insert(new Alphabet("G", "g and k are interchangeable"
                , "-", "yonga/yonka"));
        db.alphabetDao().insert(new Alphabet("I", "i is always as in fatigue"
                , "fatigue", "nirnam/mirl"));
        db.alphabetDao().insert(new Alphabet("J", "j has a softer sound than the English version, as in joint, more linke the j in banjo"
                , "banjo", "djak"));
        db.alphabetDao().insert(new Alphabet("K", "k and g are interchangeable"
                , "-", "karda/garda"));
        db.alphabetDao().insert(new Alphabet("KW(gw)", "kw is never qu or cw since c and q do not exist"
                , "as in quit or Gwenda", "kwabadak/gwabadak"));
        db.alphabetDao().insert(new Alphabet("N", "n is always as in not"
                , "not", "nunuk/noort"));
        db.alphabetDao().insert(new Alphabet("NG", "ng appears at the beginning of words(unlike in English where it never appears at the beginning) and has the same sound as in ing sound of sing, never as in finger"
                , "sing", "ganiny/nganjima"));
        db.alphabetDao().insert(new Alphabet("NY", "ny as in canyon, never as in pony cr nyal"
                , "canyon", "yingarn/nyitang"));
        db.alphabetDao().insert(new Alphabet("O", "always o as in pop"
                , "pop", "ngot"));
        db.alphabetDao().insert(new Alphabet("OO", "always oo, as in book. Also interchangeable with u, depending on the word"
                , "book", "noonook/nunuk"));
        db.alphabetDao().insert(new Alphabet("P/B", "p and b are interchangeable"
                , "-", "palyat/balyat"));
        db.alphabetDao().insert(new Alphabet("R", "r is always as in rake. It does not, however, appear at the beginning of words"
                , "rake", "kara/maar"));
        db.alphabetDao().insert(new Alphabet("RD", "pronounced with an accent on the r, as in American accents, never with the preceding consonant or vowel"
                , "hard", "karda/noort"));
        db.alphabetDao().insert(new Alphabet("RL", "pronounced with an accent on the r, as in American accents, never with the preceding consonant or vowel"
                , "burley", "karla/marlak"));
        db.alphabetDao().insert(new Alphabet("RN", "pronounced with an accent on the r, as in American accents, never with the preceding consonant or vowel"
                , "born", "boorn/nyingarn"));
        db.alphabetDao().insert(new Alphabet("RR", "slightly trilled, as in a Scottish accent"
                , "sporran", "warrkaly"));
        db.alphabetDao().insert(new Alphabet("RT", "pronounced with an accent on the r, as in American accents, never with the preceding consonant or vowel"
                , "start", "koort/dwert"));
        db.alphabetDao().insert(new Alphabet("T/D", "t and d are interchangeable. Also used with j to give a softer version of the 'ch' sound"
                , "-", "tjuditj/djuditj"));
        db.alphabetDao().insert(new Alphabet("U", "u is always as in put. Also interchangeable with oo, depending on the word"
                , "put", "Nyungar/Noongar"));
        db.alphabetDao().insert(new Alphabet("W", "w is always as in water"
                , "water", "wetj/wirlo"));
        db.alphabetDao().insert(new Alphabet("Y", "y is always as in yellow"
                , "yellow", "yooran/yandjet"));
    }


    public static void setWordsToWordGroups(ArrayList<WordGroup> wordsToWordGroups) {
        for (WordGroup x : wordsToWordGroups) {
            x.setWord1(db.wordDao().getWordsById(x.getWord1_id()));
            x.setWord2(db.wordDao().getWordsById(x.getWord2_id()));
        }
    }

}
