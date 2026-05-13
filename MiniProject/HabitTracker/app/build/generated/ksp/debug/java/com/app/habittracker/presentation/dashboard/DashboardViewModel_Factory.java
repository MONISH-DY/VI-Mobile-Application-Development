package com.app.habittracker.presentation.dashboard;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.repository.BossBattleRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<BossBattleRepository> bossBattleRepositoryProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public DashboardViewModel_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.bossBattleRepositoryProvider = bossBattleRepositoryProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(userRepositoryProvider.get(), habitRepositoryProvider.get(), gamificationEngineProvider.get(), questRepositoryProvider.get(), bossBattleRepositoryProvider.get(), preferencesManagerProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<UserRepository> userRepositoryProvider,
      Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new DashboardViewModel_Factory(userRepositoryProvider, habitRepositoryProvider, gamificationEngineProvider, questRepositoryProvider, bossBattleRepositoryProvider, preferencesManagerProvider);
  }

  public static DashboardViewModel newInstance(UserRepository userRepository,
      HabitRepository habitRepository, GamificationEngine gamificationEngine,
      QuestRepository questRepository, BossBattleRepository bossBattleRepository,
      PreferencesManager preferencesManager) {
    return new DashboardViewModel(userRepository, habitRepository, gamificationEngine, questRepository, bossBattleRepository, preferencesManager);
  }
}
