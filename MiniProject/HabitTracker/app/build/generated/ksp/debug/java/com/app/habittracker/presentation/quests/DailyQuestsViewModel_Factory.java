package com.app.habittracker.presentation.quests;

import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.engines.SmartQuestEngine;
import com.app.habittracker.domain.repository.HabitRepository;
import com.app.habittracker.domain.repository.QuestRepository;
import com.app.habittracker.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DailyQuestsViewModel_Factory implements Factory<DailyQuestsViewModel> {
  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  private final Provider<SmartQuestEngine> smartQuestEngineProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public DailyQuestsViewModel_Factory(Provider<QuestRepository> questRepositoryProvider,
      Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<SmartQuestEngine> smartQuestEngineProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.questRepositoryProvider = questRepositoryProvider;
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
    this.smartQuestEngineProvider = smartQuestEngineProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public DailyQuestsViewModel get() {
    DailyQuestsViewModel instance = newInstance(questRepositoryProvider.get(), habitRepositoryProvider.get(), gamificationEngineProvider.get());
    DailyQuestsViewModel_MembersInjector.injectSmartQuestEngine(instance, smartQuestEngineProvider.get());
    DailyQuestsViewModel_MembersInjector.injectUserRepository(instance, userRepositoryProvider.get());
    return instance;
  }

  public static DailyQuestsViewModel_Factory create(
      Provider<QuestRepository> questRepositoryProvider,
      Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<SmartQuestEngine> smartQuestEngineProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new DailyQuestsViewModel_Factory(questRepositoryProvider, habitRepositoryProvider, gamificationEngineProvider, smartQuestEngineProvider, userRepositoryProvider);
  }

  public static DailyQuestsViewModel newInstance(QuestRepository questRepository,
      HabitRepository habitRepository, GamificationEngine gamificationEngine) {
    return new DailyQuestsViewModel(questRepository, habitRepository, gamificationEngine);
  }
}
