package com.app.habittracker.domain.engines;

import com.app.habittracker.domain.repository.AchievementRepository;
import com.app.habittracker.domain.repository.BossBattleRepository;
import com.app.habittracker.domain.repository.HabitRepository;
import com.app.habittracker.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class GamificationEngine_Factory implements Factory<GamificationEngine> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AchievementRepository> achievementRepositoryProvider;

  private final Provider<BossBattleRepository> bossBattleRepositoryProvider;

  private final Provider<HabitRepository> habitRepositoryProvider;

  public GamificationEngine_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<HabitRepository> habitRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.achievementRepositoryProvider = achievementRepositoryProvider;
    this.bossBattleRepositoryProvider = bossBattleRepositoryProvider;
    this.habitRepositoryProvider = habitRepositoryProvider;
  }

  @Override
  public GamificationEngine get() {
    return newInstance(userRepositoryProvider.get(), achievementRepositoryProvider.get(), bossBattleRepositoryProvider.get(), habitRepositoryProvider.get());
  }

  public static GamificationEngine_Factory create(Provider<UserRepository> userRepositoryProvider,
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<HabitRepository> habitRepositoryProvider) {
    return new GamificationEngine_Factory(userRepositoryProvider, achievementRepositoryProvider, bossBattleRepositoryProvider, habitRepositoryProvider);
  }

  public static GamificationEngine newInstance(UserRepository userRepository,
      AchievementRepository achievementRepository, BossBattleRepository bossBattleRepository,
      HabitRepository habitRepository) {
    return new GamificationEngine(userRepository, achievementRepository, bossBattleRepository, habitRepository);
  }
}
