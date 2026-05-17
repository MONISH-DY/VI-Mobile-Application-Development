package com.app.habittracker.di;

import com.app.habittracker.data.local.HabitDatabase;
import com.app.habittracker.data.local.dao.BossBattleDao;
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
public final class AppModule_ProvideBossBattleDaoFactory implements Factory<BossBattleDao> {
  private final Provider<HabitDatabase> dbProvider;

  public AppModule_ProvideBossBattleDaoFactory(Provider<HabitDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BossBattleDao get() {
    return provideBossBattleDao(dbProvider.get());
  }

  public static AppModule_ProvideBossBattleDaoFactory create(Provider<HabitDatabase> dbProvider) {
    return new AppModule_ProvideBossBattleDaoFactory(dbProvider);
  }

  public static BossBattleDao provideBossBattleDao(HabitDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBossBattleDao(db));
  }
}
