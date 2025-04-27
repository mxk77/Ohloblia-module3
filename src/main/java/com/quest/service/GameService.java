package com.quest.service;

import com.quest.service.model.Option;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;

import java.util.*;

public class GameService {
    // ────────────────────────────────
    // 1) Константи для ID питань
    // ────────────────────────────────
    public static final String ID_START            = "start";
    public static final String ID_FOREST           = "forest";
    public static final String ID_FOREST_BERRIES   = "forestBerries";
    public static final String ID_FOREST_MUSHROOMS = "forestMushrooms";
    public static final String ID_MUSHROOM_SAFE    = "mushroomSafe";
    public static final String ID_MUSHROOM_RISKY   = "mushroomRisky";
    public static final String ID_FOREST_FISH      = "forestFish";
    public static final String ID_DEEP_FOREST      = "deepForest";
    public static final String ID_WOLF_ENCOUNTER   = "wolfEncounter";

    public static final String ID_ALLEY            = "alley";
    public static final String ID_CATCH_SUCCESS    = "catchSuccess";
    public static final String ID_CATCH_FAIL       = "catchFail";
    public static final String ID_STEAL_FISH       = "stealFish";
    public static final String ID_HOME_MEOW        = "homeMeow";
    public static final String ID_ALLEY_ESCAPE     = "alleyEscape";

    public static final String ID_ROOF             = "roof";
    public static final String ID_PIGEON_CHASE     = "pigeonChase";
    public static final String ID_ATTIC            = "attic";
    public static final String ID_ATTIC_EAT        = "atticEat";
    public static final String ID_ATTIC_EXIT       = "atticExit";

    public static final String ID_GARDEN           = "garden";
    public static final String ID_GARDEN_MILK      = "gardenMilk";
    public static final String ID_GARDEN_HERBS     = "gardenHerbs";

    public static final String ID_VICTORY_HOME     = "victoryHome";

    // ────────────────────────────────
    // 2) Константи для ID опцій
    // ────────────────────────────────
    public static final String OPT_GO_FOREST       = "goForest";
    public static final String OPT_GO_ALLEY        = "goAlley";
    public static final String OPT_GO_ROOF         = "goRoof";
    public static final String OPT_GO_GARDEN       = "goGarden";

    public static final String OPT_EAT_BERRIES     = "eatBerries";
    public static final String OPT_PICK_MUSHROOMS  = "pickMushrooms";
    public static final String OPT_CATCH_FISH      = "catchFish";

    public static final String OPT_EAT_SAFE        = "eatSafe";
    public static final String OPT_EAT_RISKY       = "eatRisky";

    public static final String OPT_RUN_AWAY        = "runAway";
    public static final String OPT_HIDE            = "hide";

    public static final String OPT_SNEAK           = "sneak";
    public static final String OPT_POUNCE          = "pounce";
    public static final String OPT_STEAL_FISH      = "stealFish";
    public static final String OPT_DISTRACT        = "distract";
    public static final String OPT_FLEE            = "flee";

    public static final String OPT_CHASE_PIGEONS   = "chasePigeons";
    public static final String OPT_ENTER_WINDOW    = "enterWindow";

    public static final String OPT_EAT_CAT_FOOD    = "eatCatFood";
    public static final String OPT_SEARCH_EXIT     = "searchExit";

    public static final String OPT_DRINK_MILK      = "drinkMilk";
    public static final String OPT_EAT_HERBS       = "eatHerbs";

    // ────────────────────────────────
    // 3) Колекція питань
    // ────────────────────────────────
    private final Map<String, Question> questions = new HashMap<>();

    public GameService() {
        initQuestions();
    }

    private void initQuestions() {
        // стартове питання
        questions.put(ID_START, new Question(
                ID_START,
                "Ви — маленький котик, прокинулися вранці голодні. Куди вирушити шукати їжу?",
                Arrays.asList(
                        new Option(OPT_GO_FOREST,    "Вирушити до лісу",      ID_FOREST),
                        new Option(OPT_GO_ALLEY,     "Піти в провулок",        ID_ALLEY),
                        new Option(OPT_GO_ROOF,      "Піднятися на дах будинку", ID_ROOF),
                        new Option(OPT_GO_GARDEN,    "Зайти в сусідський сад", ID_GARDEN)
                )
        ));

        // ліс
        questions.put(ID_FOREST, new Question(
                ID_FOREST,
                "У лісі пахне стиглими ягодами й грибами. Що обираєте?",
                Arrays.asList(
                        new Option(OPT_EAT_BERRIES,    "Зірвати й обережно спробувати ягоди", ID_FOREST_BERRIES),
                        new Option(OPT_PICK_MUSHROOMS, "Зібрати гриби біля пенька",           ID_FOREST_MUSHROOMS),
                        new Option(OPT_CATCH_FISH,     "Спробувати впіймати рибку в струмку",   ID_FOREST_FISH)
                )
        ));

        // ліс — результати
        questions.put(ID_FOREST_BERRIES, new Question(
                ID_FOREST_BERRIES,
                "Ягоди виявилися гіркими й схожими на отруйні. Ваш стан погіршується...",
                Collections.emptyList()
        ));
        questions.put(ID_FOREST_MUSHROOMS, new Question(
                ID_FOREST_MUSHROOMS,
                "Гриби можуть бути їстівні або отруйні. Що обираєте?",
                Arrays.asList(
                        new Option(OPT_EAT_SAFE,  "Ті, що ростуть біля кореня дуба", ID_MUSHROOM_SAFE),
                        new Option(OPT_EAT_RISKY, "З'їсти всі зібрані гриби",       ID_MUSHROOM_RISKY)
                )
        ));
        questions.put(ID_MUSHROOM_SAFE, new Question(
                ID_MUSHROOM_SAFE,
                "Гриби виявилися їстівними! Ви повні сил. Що далі?",
                Arrays.asList(
                        new Option(OPT_GO_FOREST,      "Піти глибше до лісу",    ID_DEEP_FOREST),
                        new Option(OPT_GO_GARDEN,      "Повернутися додому",   ID_VICTORY_HOME)
                )
        ));
        questions.put(ID_MUSHROOM_RISKY, new Question(
                ID_MUSHROOM_RISKY,
                "Отруєння: з’явилися болі в животі. Ви втрачаєте сили...",
                Collections.emptyList()
        ));
        questions.put(ID_FOREST_FISH, new Question(
                ID_FOREST_FISH,
                "Ви спритно зловили рибу й сито пообідали!",
                Collections.emptyList()
        ));
        questions.put(ID_DEEP_FOREST, new Question(
                ID_DEEP_FOREST,
                "У глуші лісу ви чуєте рик вовка. Спробуєте втекти чи сховатися?",
                Arrays.asList(
                        new Option(OPT_RUN_AWAY, "Утікати назад до узлісся", ID_FOREST),
                        new Option(OPT_HIDE,     "Сховатися за великим пнем", ID_WOLF_ENCOUNTER)
                )
        ));
        questions.put(ID_WOLF_ENCOUNTER, new Question(
                ID_WOLF_ENCOUNTER,
                "Вовк вас помітив і піднісся на лапи. Утікти не вдасться.",
                Collections.emptyList()
        ));

        // провулок
        questions.put(ID_ALLEY, new Question(
                ID_ALLEY,
                "У провулку поруч сміттєвий бак і щур. Що робите?",
                Arrays.asList(
                        new Option(OPT_SNEAK,      "Повзком підійти до щура", ID_CATCH_SUCCESS),
                        new Option(OPT_POUNCE,     "Різко стрибнути й злякати",   ID_CATCH_FAIL),
                        new Option(OPT_STEAL_FISH, "Вкрасти рибу зі смітника",    ID_STEAL_FISH)
                )
        ));
        questions.put(ID_CATCH_SUCCESS, new Question(
                ID_CATCH_SUCCESS,
                "Щур налякано втік — але ви знайшли рибу в баку! Сито пообідали.",
                Collections.emptyList()
        ));
        questions.put(ID_CATCH_FAIL, new Question(
                ID_CATCH_FAIL,
                "Ви промахнулися — щур вкусив за лапу.",
                Collections.emptyList()
        ));
        questions.put(ID_STEAL_FISH, new Question(
                ID_STEAL_FISH,
                "Свіжа риби в сміттєвому баку. Двірник біля під’їзду: нявкнути чи втекти?",
                Arrays.asList(
                        new Option(OPT_DISTRACT, "Гучно нявкнути",  ID_HOME_MEOW),
                        new Option(OPT_FLEE,     "Сховатись й утекти", ID_ALLEY_ESCAPE)
                )
        ));
        questions.put(ID_HOME_MEOW, new Question(
                ID_HOME_MEOW,
                "Ваше нявкання привернуло двірника — вас пригостили рибою.",
                Collections.emptyList()
        ));
        questions.put(ID_ALLEY_ESCAPE, new Question(
                ID_ALLEY_ESCAPE,
                "Ви втекли з пустими лапками й повернулися додому голодними.",
                Collections.emptyList()
        ));

        // дах
        questions.put(ID_ROOF, new Question(
                ID_ROOF,
                "На даху зграя голубів і дірка в покрівлі. Куди крокуєте?",
                Arrays.asList(
                        new Option(OPT_CHASE_PIGEONS, "Погнатися за голубами", ID_PIGEON_CHASE),
                        new Option(OPT_ENTER_WINDOW,  "Залізти через дірку",    ID_ATTIC)
                )
        ));
        questions.put(ID_PIGEON_CHASE, new Question(
                ID_PIGEON_CHASE,
                "Голуби здіймаються в небо й зникають. Куди тепер?",
                Arrays.asList(
                        new Option(OPT_RUN_AWAY,  "Спуститися додому й нявкати", ID_HOME_MEOW),
                        new Option(OPT_CATCH_FISH,"Спробувати з даху дістатися лісу", ID_FOREST)
                )
        ));
        questions.put(ID_ATTIC, new Question(
                ID_ATTIC,
                "На горищі пил та старі речі. Знайшли котячий корм?",
                Arrays.asList(
                        new Option(OPT_EAT_CAT_FOOD, "Поласувати кормом", ID_ATTIC_EAT),
                        new Option(OPT_SEARCH_EXIT,  "Шукати вихід",      ID_ATTIC_EXIT)
                )
        ));
        questions.put(ID_ATTIC_EAT, new Question(
                ID_ATTIC_EAT,
                "Корм свіжий і смачний! Ви ситі.",
                Collections.emptyList()
        ));
        questions.put(ID_ATTIC_EXIT, new Question(
                ID_ATTIC_EXIT,
                "Ви заблукали серед речей і впали у вентиляцію.",
                Collections.emptyList()
        ));

        // сад
        questions.put(ID_GARDEN, new Question(
                ID_GARDEN,
                "У саду багато трав і квітів. Пляма молока на лавці чи трави?",
                Arrays.asList(
                        new Option(OPT_DRINK_MILK, "Прийнюхатися й пити молоко", ID_GARDEN_MILK),
                        new Option(OPT_EAT_HERBS,   "Покуштувати ароматні трави", ID_GARDEN_HERBS)
                )
        ));
        questions.put(ID_GARDEN_MILK, new Question(
                ID_GARDEN_MILK,
                "Молоко свіже та смачне! Ви повертаєтеся додому ситі.",
                Collections.emptyList()
        ));
        questions.put(ID_GARDEN_HERBS, new Question(
                ID_GARDEN_HERBS,
                "Трави викликали чхання й сльозотечу. Ви втекли.",
                Collections.emptyList()
        ));

        // перемога додому
        questions.put(ID_VICTORY_HOME, new Question(
                ID_VICTORY_HOME,
                "Ви повернулися додому ситі та щасливі!",
                Collections.emptyList()
        ));
    }

    public Question getQuestionById(String id) {
        return questions.get(id);
    }

    public String processChoice(SessionState state, String choiceId) {
        Question q = questions.get(state.getCurrentQuestionId());
        Option selected = null;
        for (Option opt : q.getOptions()) {
            if (opt.getId().equals(choiceId)) {
                selected = opt;
                break;
            }
        }
        if (selected == null) {
            throw new IllegalArgumentException("Невідомий вибір: " + choiceId);
        }
        state.setCurrentQuestionId(selected.getNextID());
        return selected.getNextID();
    }

    public boolean isEndState(String questionId) {
        Question q = getQuestionById(questionId);
        return q.getOptions().isEmpty();
    }
}