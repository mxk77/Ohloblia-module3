package com.quest.service;

import com.quest.service.model.Option;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameService {
    private final Map<String, Question> questions = new HashMap<>();

    public GameService(){
        initQuestions();
    }

    private void initQuestions() {
        // Приклад для стартового питання
        List<Option> startOptions = new ArrayList<>();
        startOptions.add(new Option("goForest", "У ліс за ягодами", "forest"));
        startOptions.add(new Option("goAlley",  "У провулок за мишами",  "alley"));
        startOptions.add(new Option("stayHome", "Залишитися вдома й м’якнути", "meowLoudly"));
        questions.put("start", new Question("start",
                "Ви – маленький котик, прокинулися голодними. Куди йти шукати їжу?",
                startOptions));

        // Ліс
        List<Option> forestOptions = new ArrayList<>();
        forestOptions.add(new Option("eatBerries", "Зірвати й з’їсти ягоди", "forestBerries"));
        forestOptions.add(new Option("catchFish",  "Спробувати зловити рибку",  "forestFish"));
        questions.put("forest", new Question("forest",
                "У лісі ви бачите кущ із ягодами та річку з рибою. Що обираєте?",
                forestOptions));

        // Результати (кінець гри – порожній список)
        questions.put("forestBerries", new Question(
                "forestBerries",
                "Ягоди виявилися отруйними. Ви відчули слабкість. Поразка.",
                new ArrayList<>()
        ));
        questions.put("forestFish", new Question(
                "forestFish",
                "Вдалось спіймати рибку – сито пообідали! Перемога.",
                new ArrayList<>()
        ));

        // Провулок
        List<Option> alleyOptions = new ArrayList<>();
        alleyOptions.add(new Option("sneak",  "Повзком, мов тінь",        "catchSuccess"));
        alleyOptions.add(new Option("pounce", "Різко стрибнути й здивувати", "catchFail"));
        questions.put("alley", new Question("alley",
                "У провулку ви помітили мишку за сміттєвим баком. Як підкрадетеся?",
                alleyOptions));

        questions.put("catchSuccess", new Question(
                "catchSuccess",
                "Мишка не втекла – ситий обід. Перемога!",
                new ArrayList<>()
        ));
        questions.put("catchFail", new Question(
                "catchFail",
                "Мишка злякалась і втекла. Поразка.",
                new ArrayList<>()
        ));

        // М’якання вдома
        List<Option> homeOptions = new ArrayList<>();
        homeOptions.add(new Option("feedYou",   "Господар дає корм",    "homeFeed"));
        homeOptions.add(new Option("ignoreYou", "Господар ігнорує",     "homeIgnore"));
        questions.put("meowLoudly", new Question("meowLoudly",
                "Ви м’якнули дуже голосно. Що зробить господар?",
                homeOptions));

        questions.put("homeFeed", new Question(
                "homeFeed",
                "Корм вчасно! Ви сито поїли. Перемога.",
                new ArrayList<>()
        ));
        questions.put("homeIgnore", new Question(
                "homeIgnore",
                "Господар злякався й не прийшов. Поразка.",
                new ArrayList<>()
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
