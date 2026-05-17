package com.app.habittracker.di;

import com.app.habittracker.data.local.HabitDatabase;
import com.app.habittracker.data.local.dao.AchievementDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideAchievementDaoFactory implements Factory<AchievementDao> {
  private final Provider<HabitDatabase> dbProvider;

  public AppModule_ProvideAchievementDaoFactory(Provider<HabitDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AchievementDao get() {
    return provideAchievementDao(dbProvider.get());
  }

  public static AppModule_ProvideAchievementDaoFactory create(Provider<HabitDatabase> dbProvider) {
    return new AppModule_ProvideAchievementDaoFactory(dbProvider);
  }

  public static AchievementDao provideAchievementDao(HabitDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAchievementDao(db));
  }
}
