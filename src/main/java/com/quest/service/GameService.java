package com.quest.service;

import com.quest.service.model.Option;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;

import java.util.*;

public class GameService {
    private final Map<String, Question> questions = new HashMap<>();

    public GameService() {
        initQuestions();
    }

    private void initQuestions() {
        // Початкове питання
        questions.put("start", new Question(
                "start",
                "Ви — маленький котик, прокинулися вранці голодні. Куди вирушити шукати їжу?",
                List.of(
                        new Option("goForest", "Вирушити до лісу", "forest"),
                        new Option("goAlley", "Піти в провулок", "alley"),
                        new Option("goRoof", "Піднятися на дах будинку", "roof"),
                        new Option("goGarden", "Зайти в сусідський сад", "garden")
                )
        ));

        // Ліс
        questions.put("forest", new Question(
                "forest",
                "У лісі пахне стиглими ягодами й грибами. Кущ із червоними ягодами або невеликі білих грибів?",
                List.of(
                        new Option("eatBerries", "Зірвати й обережно спробувати ягоди", "forestBerries"),
                        new Option("pickMushrooms", "Зібрати гриби, що ростуть біля пенька", "forestMushrooms"),
                        new Option("catchFish", "Спробувати впіймати рибку в струмку", "forestFish")
                )
        ));
        // Ліс — ягоди
        questions.put("forestBerries", new Question(
                "forestBerries",
                "Ягоди виявилися гіркими й схожими на отруйні. Ваш стан погіршується...",
                Collections.emptyList()
        ));
        // Ліс — гриби
        questions.put("forestMushrooms", new Question(
                "forestMushrooms",
                "Гриби можуть бути їстівні або отруйні. Що вибираєте?",
                List.of(
                        new Option("eatSafe", "Ті, що ростуть біля кореня дуба", "mushroomSafe"),
                        new Option("eatRisky", "Снідати всі зібрані гриби", "mushroomRisky")
                )
        ));
        questions.put("mushroomSafe", new Question(
                "mushroomSafe",
                "Гриби виявилися їстівними! Ви повні сил. Далі шукаємо шлях додому чи в інші пригоди?",
                List.of(
                        new Option("goHome", "Повернутися додому", "victoryHome"),
                        new Option("exploreDeeper", "Піти глибше в ліс", "deepForest")
                )
        ));
        questions.put("mushroomRisky", new Question(
                "mushroomRisky",
                "Отруєння: з’явилися болі в животі. Ви втрачаєте сили...",
                Collections.emptyList()
        ));
        questions.put("forestFish", new Question(
                "forestFish",
                "Ви спритно зловили дрібну рибу й сито пообідали!",
                Collections.emptyList()
        ));
        questions.put("deepForest", new Question(
                "deepForest",
                "У глибині лісу ви чуєте рик вовка. Спроба втекти чи заховатися?",
                List.of(
                        new Option("runAway", "Утікати назад до узлісся", "forest"),
                        new Option("hide", "Сховатися за великим пнем", "wolfEncounter")
                )
        ));
        questions.put("wolfEncounter", new Question(
                "wolfEncounter",
                "Вовк вас помітив і піднісся на лапи. Утікти не вдасться.",
                Collections.emptyList()
        ));

        // Провулок
        questions.put("alley", new Question(
                "alley",
                "У провулку стоїть сміттєвий бак, а поруч біжить щур. Що робите?",
                List.of(
                        new Option("sneak", "Повзком підійти до щура", "catchSuccess"),
                        new Option("pounce", "Різко стрибнути та лякнути", "catchFail"),
                        new Option("stealFish", "Вкрасти рибу зі смітника", "stealFish")
                )
        ));
        questions.put("catchSuccess", new Question(
                "catchSuccess",
                "Щур налякано втік — але ви знайшли залишки риби в баку! Сито пообідали.",
                Collections.emptyList()
        ));
        questions.put("catchFail", new Question(
                "catchFail",
                "Ви промахнулися — щур вкусив вас за лапу. Ви покалічені.",
                Collections.emptyList()
        ));
        questions.put("stealFish", new Question(
                "stealFish",
                "У баку було кілька свіжих риб. Але раптом під’їхав двірник. Спроба відволікти його чи втекти?",
                List.of(
                        new Option("distract", "М’яукнути гучніше", "homeMeow"),
                        new Option("flee", "Ухилитися й побігти геть", "alleyEscape")
                )
        ));
        questions.put("homeMeow", new Question(
                "homeMeow",
                "Ваше нявкання привернуло увагу господаря двора — вас пригостили залишками риби.",
                Collections.emptyList()
        ));
        questions.put("alleyEscape", new Question(
                "alleyEscape",
                "Ви втекли, але риби не встигли вхопити. Ви зголоднілі й повернулися додому.",
                Collections.emptyList()
        ));

        // Дах
        questions.put("roof", new Question(
                "roof",
                "На даху ви бачите зграю голубів і відкритий віконний отвір. Куди крокуєте?",
                List.of(
                        new Option("chasePigeons", "Погнатися за голубами", "pigeonChase"),
                        new Option("enterWindow", "Залізти через вікно", "attic")
                )
        ));
        questions.put("pigeonChase", new Question(
                "pigeonChase",
                "Голуби здіймаються у небо й зникають. Ви самі змушені шукати їжу. Куди тепер?",
                List.of(
                        new Option("goHomeRoof", "Спуститися додому і нявкати", "homeMeow"),
                        new Option("goForestRoof", "Спробувати зі стріхи дістатися до лісу", "forest")
                )
        ));
        questions.put("attic", new Question(
                "attic",
                "Ви опинилися на горищі. Пил, старі речі й запилені стіни. Знайшли розкриту коробку з кормом?",
                List.of(
                        new Option("eatCatFood", "Відкрити пакунок корму й поласувати", "atticEat"),
                        new Option("searchExit", "Шукати шлях з горища", "atticExit")
                )
        ));
        questions.put("atticEat", new Question(
                "atticEat",
                "Корм свіжий і смачний! Ви ситі.",
                Collections.emptyList()
        ));
        questions.put("atticExit", new Question(
                "atticExit",
                "Ви заблукали серед коробок і впали через вентиляційний отвір.",
                Collections.emptyList()
        ));

        // Сад
        questions.put("garden", new Question(
                "garden",
                "В сусідському саду росте багато трав і квітів. Пляма з молоком на лавці чи збирати квіти?",
                List.of(
                        new Option("drinkMilk", "Прийнюхатися та влаштуватися пити молоко", "gardenMilk"),
                        new Option("eatHerbs", "Покуштувати ароматні трави", "gardenHerbs")
                )
        ));
        questions.put("gardenMilk", new Question(
                "gardenMilk",
                "Молоко свіже та смачне! Ви повертаєтеся додому задоволені.",
                Collections.emptyList()
        ));
        questions.put("gardenHerbs", new Question(
                "gardenHerbs",
                "Трави викликали чхання й сльозотечу. Ви злякалися і втекли.",
                Collections.emptyList()
        ));

        // Повернення додому
        questions.put("victoryHome", new Question(
                "victoryHome",
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
        for (Option o : q.getOptions()) {
            if (o.getId().equals(choiceId)) {
                selected = o;
                break;
            }
        }
        if (selected == null) {
            throw new IllegalArgumentException("Невідомий вибір: " + choiceId);
        }
        String nextId = selected.getNextID();
        state.setCurrentQuestionId(nextId);
        return nextId;
    }

    public boolean isEndState(String questionId) {
        Question q = getQuestionById(questionId);
        return q.getOptions().isEmpty();
    }
}