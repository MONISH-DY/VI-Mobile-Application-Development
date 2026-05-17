package com.app.habittracker.presentation.achievements;

import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.repository.AchievementRepository;
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
public final class AchievementsViewModel_Factory implements Factory<AchievementsViewModel> {
  private final Provider<AchievementRepository> achievementRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  public AchievementsViewModel_Factory(
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider) {
    this.achievementRepositoryProvider = achievementRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
  }

  @Override
  public AchievementsViewModel get() {
    return newInstance(achievementRepositoryProvider.get(), gamificationEngineProvider.get());
  }

  public static AchievementsViewModel_Factory create(
      Provider<AchievementRepository> achievementRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider) {
    return new AchievementsViewModel_Factory(achievementRepositoryProvider, gamificationEngineProvider);
  }

  public static AchievementsViewModel newInstance(AchievementRepository achievementRepository,
      GamificationEngine gamificationEngine) {
    return new AchievementsViewModel(achievementRepository, gamificationEngine);
  }
}
