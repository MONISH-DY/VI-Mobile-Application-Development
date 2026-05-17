package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.AchievementDao;
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
public final class AchievementRepository_Factory implements Factory<AchievementRepository> {
  private final Provider<AchievementDao> achievementDaoProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public AchievementRepository_Factory(Provider<AchievementDao> achievementDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.achievementDaoProvider = achievementDaoProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public AchievementRepository get() {
    return newInstance(achievementDaoProvider.get(), preferencesManagerProvider.get());
  }

  public static AchievementRepository_Factory create(
      Provider<AchievementDao> achievementDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new AchievementRepository_Factory(achievementDaoProvider, preferencesManagerProvider);
  }

  public static AchievementRepository newInstance(AchievementDao achievementDao,
      PreferencesManager preferencesManager) {
    return new AchievementRepository(achievementDao, preferencesManager);
  }
}
